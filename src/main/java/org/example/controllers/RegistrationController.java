package org.example.controllers;

import org.example.dao.CustomerRepoI;
import org.example.models.Customer;
import org.example.models.Office;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.service.CustomerService;

//Registration Controller uses registration form for customer
@Controller
@Slf4j
public class RegistrationController {

   
    private String selectedDevice = null;
    private Office selectedOffice = new Office();
    private Technician selectedTechnician = new Technician();


    private final CustomerRepoI customerRepoI;
    private final CustomerService customerService;

    @Autowired
    public RegistrationController(CustomerRepoI customerRepoI, CustomerService customerService) {
        this.customerRepoI = customerRepoI;
        this.customerService = customerService;
    }

    @GetMapping(value = "customer-registration")
    public String customerRegistrationPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        log.warn("I am in the customer-registration controller method");
        selectedDevice = (String) session.getAttribute("selectedDevice");

        System.out.println("selectedDevice: " + selectedDevice);
        System.out.println("selectedTechnician: " + selectedTechnician);

        if (selectedDevice == null) {
            log.warn("Device is empty! Returning to index");
            redirectAttributes.addFlashAttribute("insertedDanger", "Please select a device!");
            return "redirect:index";
        }
        if (selectedTechnician == null) {
            log.warn("Technician is Empty! Returning to select-store");
            redirectAttributes.addFlashAttribute("insertedDangerStore", "Please select a Store and Technician!");
            return "redirect:select-store";
        }

        model.addAttribute("customer", new Customer());

        return "customer-registration";
    }


    @PostMapping("/post-customer-registration")
    public String customerProcess(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, HttpSession session, Model model) {

        log.warn("I am in the customerProcess controller method");

        if (bindingResult.hasErrors()) {
            log.debug(bindingResult.getAllErrors().toString());
            return "customer-registration";
        }

        customerRepoI.saveAndFlush(customer);
        System.out.println(customer.toString());

        session.setAttribute("registeredCustomer", customer);

        return "redirect:book-appointment";
    }
}
