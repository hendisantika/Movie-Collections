package com.hendisantika.controller;

import com.hendisantika.model.Actor;
import com.hendisantika.model.Movie;
import com.hendisantika.service.AppService;
import com.hendisantika.util.TextForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/18/22
 * Time: 05:38
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    @GetMapping("")
    public String redirectToMain() {
        return "redirect:/movies/list";
    }

    @GetMapping(value = "/movies/update/{id}")
    public String updateMovie(@PathVariable("id") Long id, Model model) {
        Movie movie = appService.findMovie(id);
        model.addAttribute("movie", movie);
        return "edit";
    }

    @PostMapping("/movies//edit/cast/{id}")
    public String movieUpdating(@PathVariable("id") Long id, @ModelAttribute Movie movie, Model model) {
        appService.update(movie);
        model.addAttribute("movie_id", movie.getId());
        model.addAttribute("actor", new Actor());
        model.addAttribute("actors", appService.findActors());
        model.addAttribute("textForm", new TextForm());
        return "updateCast";
    }
}
