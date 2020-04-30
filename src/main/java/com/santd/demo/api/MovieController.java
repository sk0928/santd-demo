package com.santd.demo.api;

import com.santd.demo.models.MovieEntity;
import com.santd.demo.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "movie", produces = "application/json", consumes = "application/json")
public class MovieController {

	private MovieService movieService;
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("{id}")
	public ResponseEntity getMovies(@PathVariable(required = false) String id) {
		List movies = movieService.getMovies(Optional.of(id));
		if (movies.size() > 0){
			return ResponseEntity.ok(movies);
		} else return ResponseEntity.notFound().build();

	}

	@GetMapping("list")
	public ResponseEntity getAllMovies() {
		List movies = movieService.getMovies(Optional.empty());
		if (movies.size() > 0){
			return ResponseEntity.ok(movies);
		} else return ResponseEntity.notFound().build();

	}
	
	@PutMapping
	public ResponseEntity addMovie(@Valid @RequestBody MovieEntity movie) {
		movieService.addMovie(movie);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deleteMovie(@PathVariable String id) {
		movieService.deleteMovie(id);
		return ResponseEntity.noContent().build();
	}
}
