package com.hendisantika.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/17/22
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_actors")
@JsonIgnoreProperties(value = {"playedIn"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;

    @Column(name = "actor_name")
    private String actorName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cast")
    private Set<Movie> playedIn = new HashSet<>();

    public void addMovie(Movie movie) {
        this.playedIn.add(movie);
    }

}
