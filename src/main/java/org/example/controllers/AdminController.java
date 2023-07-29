package org.example.controllers;

import org.example.dao.CustomerRepoI;
import org.example.dao.OfficeRepoI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.example.service.CustomerService;
import org.example.service.OfficeService;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    CustomerRepoI customerRepoI;
    OfficeRepoI officeRepoI;
    CustomerService customerService;
    OfficeService officeService;

    @Autowired
    public AdminController(CustomerRepoI customerRepoI, OfficeRepoI officeRepoI, CustomerService customerService, OfficeService officeService) {
        this.customerRepoI = customerRepoI;
        this.officeRepoI = officeRepoI;
        this.customerService = customerService;
        this.officeService = officeService;
    }



}
