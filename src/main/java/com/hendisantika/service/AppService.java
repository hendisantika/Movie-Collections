package com.hendisantika.service;

import com.hendisantika.exception.MovieNotFoundException;
import com.hendisantika.model.Actor;
import com.hendisantika.model.Movie;
import com.hendisantika.model.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/17/22
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public interface AppService {
    List<Actor> findActors();

    List<Actor> findActorsByMovie(Movie movie);

    List<Movie> findMovies();

    List<Movie> findMoviesByName(String name);

    List<Movie> findMoviesByCategory(String category);

    List<Movie> findMoviesByActor(String actor);

    Movie findMovie(Long id) throws MovieNotFoundException;

    Actor findActor(Long id);

    void createMovie(Movie movie);

    void createActor(Actor actor);

    void update(Movie movie);

    void update(Actor actor);

    void deleteMovie(Long id);

    void deleteActor(Long actorId);

    void registerUser(User user);
}
