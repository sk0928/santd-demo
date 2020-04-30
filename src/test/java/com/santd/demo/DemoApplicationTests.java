package com.santd.demo;

import com.santd.demo.models.MovieEntity;
import com.santd.demo.service.MovieService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@BeforeEach
	public void setup(){
		RestAssured.port = port;
	}


	@MockBean
	MovieService movieService;

	@Test
	public void shouldThrowExceptionForInvalidContentType(){
		given().contentType("application/xml").when().get("/movie/12").then().assertThat().statusCode(415);
		given().contentType("application/xml").when().put("/movie").then().assertThat().statusCode(415);
		given().contentType("application/xml").when().delete("/movie/12").then().assertThat().statusCode(415);
	}

	@Test
	public void shouldReturnOneMovieWhenSentValidId(){
		List<MovieEntity> movieEntities = new ArrayList<>();
		movieEntities.add(MovieEntity.builder().id("id1").name("movie1").releaseYear(2020).build());
		movieEntities.add(MovieEntity.builder().id("id2").name("movie2").releaseYear(2020).build());
		when(movieService.getMovies(Optional.empty())).thenReturn(movieEntities);
		Response response = given().contentType("application/json").when().get("/movie/list");
		System.out.println(response.body().prettyPrint());
		response.then().statusCode(200).body("name", containsInAnyOrder("movie1","movie2"));

	}

	@Test
	public void shouldReturn404WhenNoMoviesArePresent(){
		when(movieService.getMovies(Optional.empty())).thenReturn(Collections.emptyList());
		given().contentType("application/json").when().get("/movie/list").then().statusCode(404);
	}

	@Test
	public void shouldReturn404WhenSentInvalidId(){
		when(movieService.getMovies(Optional.empty())).thenThrow(new EntityNotFoundException());
		given().contentType("application/json").when().get("/movie/id2").then().statusCode(404);
	}

	@Test
	public void shouldReturnAllMoviesWithGet(){
		given().contentType("application/json").when().get("/movie/valid").then().statusCode(404);
	}

	@Test
	void shouldSaveSuccessfullyWithValidPayload() throws IOException, URISyntaxException {
		when(movieService.addMovie(ArgumentMatchers.any())).thenReturn("movieId");
		Response response = given().contentType("application/json")
				.body(readBytes("valid_req.json"))
				.when().put("/movie");
		System.out.println(response.body().prettyPrint());
		response.then().statusCode(200);
	}

	@Test
	void shouldThrowExceptionForInvalidPayload() throws IOException, URISyntaxException {
		when(movieService.addMovie(ArgumentMatchers.any())).thenReturn("movieId");
		given().contentType("application/json")
				.body(readBytes("invalid_req.json"))
				.when().put("/movie").then().statusCode(400);
	}

	@Test
	void shouldReturnTrueForDeleteValidOrInvalidId() {
		when(movieService.deleteMovie("id1")).thenReturn(true);
		given().contentType("application/json")
				.when().delete("/movie/id1").then().statusCode(204);
	}


	private byte[] readBytes(String file) throws URISyntaxException, IOException {
		return Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("invalid_req.json").toURI()));
	}

}
