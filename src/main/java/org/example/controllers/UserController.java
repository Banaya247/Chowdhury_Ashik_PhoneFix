package org.example.controllers;

import org.example.dao.CustomerRepoI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.example.service.CustomerService;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final CustomerRepoI customerRepoI;
    private final CustomerService customerService;

    @Autowired
    public UserController(CustomerRepoI customerRepoI, CustomerService customerService) {
        this.customerRepoI = customerRepoI;
        this.customerService = customerService;
    }



}
