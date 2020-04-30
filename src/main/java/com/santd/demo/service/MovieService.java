package com.santd.demo.service;

import com.santd.demo.models.MovieEntity;
import com.santd.demo.models.MovieModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MovieService {
    List<MovieEntity> getMovies(Optional<String> string);
    String addMovie(MovieEntity movieModel);
    boolean deleteMovie(String id);
}
