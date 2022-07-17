package com.hendisantika.repository;

import com.hendisantika.model.Movie;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/17/22
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
public interface MovieRepository {
    List<Movie> findAll();

    Movie findById(Long id);

    List<Movie> findByName(String name);

    List<Movie> findByCategory(String category);

    List<Movie> findByActor(String actor);

    void create(Movie movie);

    Movie update(Movie movie);

    void delete(Long id);
}
