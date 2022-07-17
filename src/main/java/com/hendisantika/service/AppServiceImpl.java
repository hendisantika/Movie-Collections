package com.hendisantika.service;

import com.hendisantika.exception.MovieNotFoundException;
import com.hendisantika.model.Actor;
import com.hendisantika.model.Movie;
import com.hendisantika.repository.ActorRepository;
import com.hendisantika.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/17/22
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {

    private MovieRepository movieRepository;
    private ActorRepository actorRepository;

    @Override
    public List<Movie> findMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> findMoviesByName(String name) {
        return movieRepository.findByName(name);
    }

    @Override
    public Movie findMovie(Long id) throws MovieNotFoundException {
        Movie movie = movieRepository.findById(id);
        if (movie == null) throw new MovieNotFoundException("Owner not found with id: " + id);
        return movie;
    }

    @Override
    public void createMovie(Movie movie) {
        movieRepository.create(movie);
    }

    @Override
    public void createActor(Actor actor) {
        actorRepository.create(actor);
    }

    @Override
    public void update(Movie movie) {
        movieRepository.update(movie);
    }

    @Override
    public void update(Actor actor) {
        actorRepository.update(actor);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.delete(id);
    }

    @Override
    public void deleteActor(Long actorId) {
        actorRepository.delete(actorId);
    }

    @Override
    public List<Movie> findMoviesByCategory(String category) {
        return movieRepository.findByCategory(category);
    }

    @Override
    public List<Movie> findMoviesByActor(String actor) {
        return movieRepository.findByActor(actor);
    }

    @Override
    public List<Actor> findActors() {
        return actorRepository.findActors();
    }

    @Override
    public Actor findActor(Long id) {
        return actorRepository.findActorById(id);
    }

    @Override
    public List<Actor> findActorsByMovie(Movie movie) {
        return actorRepository.findActorsByMovie(movie);
    }

}
