package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

////Login Controller posts, gets, and redirects to other pages
@Controller
@Slf4j
public class LoginController {

    @GetMapping(value = "/login")
    public String loginPage() {
        log.warn("I am in the loginPage controller method");

        return "login";
    }

    @PostMapping("/post-login")
    public String loginProcess() {
        log.warn("I am in the post-login controller method");

        return "redirect:index";
    }
}
