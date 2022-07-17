package com.hendisantika.controller;

import com.hendisantika.model.User;
import com.hendisantika.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/18/22
 * Time: 05:37
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AppService appService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@ModelAttribute User user, Model model) {
        if (user.getUsername().length() <= 0 || user.getPassword().length() <= 0) {
            return "redirect:/register";
        } else {
            user.setEnabled(true);
            appService.registerUser(user);
            return "login";
        }

    }
}
