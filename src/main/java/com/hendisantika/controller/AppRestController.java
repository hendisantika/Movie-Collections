package com.hendisantika.controller;

import com.hendisantika.exception.MovieNotFoundException;
import com.hendisantika.model.Movie;
import com.hendisantika.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/18/22
 * Time: 05:43
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class AppRestController {

    private final AppService appService;

    @GetMapping(value = "/movies")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = appService.findMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/movie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id) {
        try {
            Movie movie = appService.findMovie(id);
            return ResponseEntity.ok(movie);
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
