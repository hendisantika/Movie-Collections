package com.hendisantika.repository;

import com.hendisantika.model.Actor;
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
public interface ActorRepoJPA implements ActorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    void create(Actor actor) {
        entityManager.persist(actor);
    }

    @Override
    Actor update(Actor actor) {
        return entityManager.merge(actor);
    }

    @Override
    void delete(Long actor_id) {
        entityManager.remove(entityManager.getReference(Actor.class, actor_id));
    }

    @Override
    List<Actor> findActors() {
        return entityManager.createQuery("from Actor", Actor.class).getResultList();
    }

    @Override
    Actor findActorById(Long id) {
        return entityManager.find(Actor.class, id);
    }
}
