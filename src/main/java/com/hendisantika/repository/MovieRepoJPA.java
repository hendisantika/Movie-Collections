package com.hendisantika.repository;

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
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
@Repository("movieRepository")
public class MovieRepoJPA implements MovieRepository {

    @PersistenceContext
    private EntityManager entityManager;
}
