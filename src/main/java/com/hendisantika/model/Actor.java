package com.hendisantika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonIgnore
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cast")
    private Set<Movie> playedIn = new HashSet<>();

    public void addMovie(Movie movie) {
        this.playedIn.add(movie);
    }

}
