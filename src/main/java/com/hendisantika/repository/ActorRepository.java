package com.hendisantika.repository;

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
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public interface ActorRepository {
    List<Actor> findActorsByMovie(Movie movie);

    List<Actor> findActors();

    Actor findActorById(Long id);

    void create(Actor actor);

    Actor update(Actor actor);

    void delete(Long actorId);

    void registerUser(User user);
}
