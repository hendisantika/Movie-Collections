package com.hendisantika.repository;

import com.hendisantika.model.Actor;
import com.hendisantika.model.Movie;
import com.hendisantika.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/17/22
 * Time: 18:10
 * To change this template use File | Settings | File Templates.
 */
@Repository("actorRepository")
public class ActorRepoJPA implements ActorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Actor actor) {
        entityManager.persist(actor);
    }

    @Override
    public Actor update(Actor actor) {
        return entityManager.merge(actor);
    }

    @Override
    public void delete(Long actor_id) {
        entityManager.remove(entityManager.getReference(Actor.class, actor_id));
    }

    @Override
    public List<Actor> findActors() {
        return entityManager.createQuery("from Actor", Actor.class).getResultList();
    }

    @Override
    public Actor findActorById(Long id) {
        return entityManager.find(Actor.class, id);
    }

    @Override
    public List<Actor> findActorsByMovie(Movie movie) {
        String q = "SELECT * FROM Movie_Actors WHERE movie_id = " + movie.getId();
        return entityManager.createNativeQuery(q).getResultList();
    }

    // This is irrelevant to the actor model however it was easier to implement this
    // method inside an existing repository class rather than creating a new one
    // just for one method.
    @Override
    public void registerUser(User user) {
        String q = "INSERT INTO users(username, password, enabled) VALUES('" + user.getUsername() + "', '{noop}" + user.getPassword() + "', " + user.isEnabled() + ");";
        String q2 = "INSERT INTO authorities(username, authority) VALUES('" + user.getUsername() + "', '" + user.getRole() + "');";
        entityManager.createNativeQuery(q).executeUpdate();
        entityManager.createNativeQuery(q2).executeUpdate();
    }
}
