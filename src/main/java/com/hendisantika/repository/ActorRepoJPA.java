package com.hendisantika.repository;

import com.hendisantika.model.Actor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
