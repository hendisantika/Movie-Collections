package com.hendisantika.repository;

import com.hendisantika.model.Movie;
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
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
@Repository("movieRepository")
public class MovieRepoJPA implements MovieRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Movie> findAll() {
        return entityManager.createQuery("from Movie", Movie.class).getResultList();
    }

    @Override
    public Movie findById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    @Override
    public List<Movie> findByName(String name) {
        String queryString = "from Movie WHERE upper(name) LIKE :name";
        return entityManager.createQuery(queryString, Movie.class)
                .setParameter("name", "%" + name.toUpperCase() + "%")
                .getResultList();
    }

    @Override
    public void create(Movie movie) {
        entityManager.persist(movie);
    }

    @Override
    public Movie update(Movie movie) {
        return entityManager.merge(movie);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(Movie.class, id));
    }

    @Override
    public List<Movie> findByCategory(String category) {
        return entityManager.createQuery("from Movie WHERE upper(category) = :category", Movie.class)
                .setParameter("category", category.toUpperCase()).getResultList();
    }
}
