package com.santd.demo.service;


import com.santd.demo.models.MovieEntity;
import com.santd.demo.repo.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public List<MovieEntity> getMovies(Optional<String> id) {
        if (id.isPresent()){
            Optional<MovieEntity> movie = movieRepository.findById(id.get());
            movie.orElseThrow(() -> new EntityNotFoundException());
            return Collections.singletonList(movie.get());
        }else{
            return movieRepository.findAll();
        }
    }

    @Override
    public String addMovie(MovieEntity movieModel) {
        return movieRepository.save(movieModel).getId();
    }

    @Override
    public boolean deleteMovie(String id) {
        if(movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException();
    }
}
