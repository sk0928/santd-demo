package com.santd.demo;

import com.santd.demo.models.MovieEntity;
import com.santd.demo.repo.MovieRepository;
import com.santd.demo.service.MovieService;
import com.santd.demo.service.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieService movieService;
    @BeforeEach
    void setup(){
        movieService = new MovieServiceImpl(movieRepository);
    }


    @Test
    void getAllMovies(){
        List<MovieEntity> movieEntities = new ArrayList<>();
        movieEntities.add(MovieEntity.builder().id("id1").name("movie1").releaseYear(2020).build());
        movieEntities.add(MovieEntity.builder().id("id2").name("movie2").releaseYear(2020).build());
        when(movieRepository.findAll()).thenReturn(movieEntities);
        assertThat(movieService.getMovies(Optional.empty()).size(), is(2));
    }

    @Test
    void getOneMovie(){
        when(movieRepository.findById("id1")).thenReturn(Optional.of(MovieEntity.builder().id("id1").name("movie1").releaseYear(2020).build()));
        assertThat(movieService.getMovies(Optional.of("id1")).size(), is(1));
    }

    @Test
    void getNoMovie(){
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(movieService.getMovies(Optional.empty()).size(), is(0));
        when(movieRepository.findById("invalid")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->movieService.getMovies(Optional.of("invalid")));
    }

    @Test
    void saveAMovie(){
        MovieEntity movieEntity = MovieEntity.builder().id("new_movie").name("movie_name").releaseYear(2020).build();
        when(movieRepository.save(any())).thenReturn(movieEntity);
        assertThat(movieService.addMovie(movieEntity), is("new_movie"));
    }

    @Test
    void deleteAMovieShouldReturnTrue(){
        when(movieRepository.existsById("id1")).thenReturn(true);
        doNothing().when(movieRepository).deleteById("id1");
        assertThat(movieService.deleteMovie("id1"),is(true));
    }

    @Test
    void throwNotFoundWithInvalidIdForDelete(){
        when(movieRepository.existsById("id1")).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () ->movieService.deleteMovie("id1"));
    }
}
