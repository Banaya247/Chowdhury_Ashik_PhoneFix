package org.example.controllers;

import org.example.models.Customer;
import org.example.models.Office;
import org.example.dao.AppointmentRepoI;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Appointment;
import org.example.models.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.service.AppointmentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class AppointmentController {

    private String selectedDevice = "";
    private Office selectedOffice = new Office();
    private Technician selectedTechnician = new Technician();
    private Customer registeredCustomer = new Customer();
    private Date selectedDate = new Date();
    private String selectedTime = null;
    private Appointment buildingAppointment = new Appointment();


    private final AppointmentRepoI appointmentRepoI;
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentRepoI appointmentRepoI, AppointmentService appointmentService) {
        this.appointmentRepoI = appointmentRepoI;
        this.appointmentService = appointmentService;
    }


    @GetMapping(value = "book-appointment")
    public String bookAppointmentPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {

        log.warn("I am in the book-appointment controller method");

        log.info("Retrieving selectedDevice from HttpSession. Casting returned value from Object to String");
        selectedDevice = (String) session.getAttribute("selectedDevice");
        System.out.println(selectedDevice);

        log.info("Retrieving selectedOffice from HttpSession. Casting returned value from Object to Office");
        selectedOffice = (Office) session.getAttribute("selectedOffice");

        log.info("Retrieving selectedTechnician from HttpSession. Casting returned value from Object to Technician");
        selectedTechnician = (Technician) session.getAttribute("selectedTechnician");

        log.info("Retrieving registeredCustomer from HttpSession. Casting returned value from Object to Customer");
        registeredCustomer = (Customer) session.getAttribute("registeredCustomer");

          if (selectedDevice == null) {
            log.warn("Device is empty! Returning to index");
            redirectAttributes.addFlashAttribute("insertedDanger", "Please select a device!");
            return "redirect:index";
        }

        if (selectedTechnician == null) {
            log.warn("Technician is Empty! Returning to select-store");
            redirectAttributes.addFlashAttribute("insertedDangerStore", "Please select a Store and Technician!");
            return "redirect:select-Store";
        }

        if (registeredCustomer == null) {
            log.warn("Customer Not Registered!");
            redirectAttributes.addFlashAttribute("insertedDangerCustomer", "Please Complete Registration!");
            return "redirect:customer-registration";
        }

        String time = null;
        Date date = null;
        buildingAppointment = new Appointment(0, date, time, selectedDevice, selectedTechnician, selectedOffice, registeredCustomer);

        model.addAttribute("appointment", buildingAppointment);

        return "book-appointment";
    }

    @PostMapping(value = "/post-book-appointment")
    public String bookAppointmentProcess(@Valid @ModelAttribute("appointment") Appointment builtAppointment, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.warn("I am in the bookAppointmentProcess controller method");

        if (bindingResult.hasErrors()) {
            log.debug(bindingResult.getAllErrors().toString());
            redirectAttributes.addFlashAttribute("appointment", buildingAppointment);
            redirectAttributes.addFlashAttribute("insertedDangerDateTime", "Please choose appointment Date & Time!");
            return "redirect:book-appointment";
        }

        Date d1 = builtAppointment.getAppointmentDate();
        Date d2 = new Date();

        if (d1.compareTo(d2) < 0) {
            log.debug("Selected date is before today's date!");
            redirectAttributes.addFlashAttribute("appointment", buildingAppointment);
            redirectAttributes.addFlashAttribute("insertedDangerWrongDate", "Your appointment cannot be scheduled on or before today's date!");
            return "redirect:book-appointment";
        }

        buildingAppointment = builtAppointment;
        selectedDate = builtAppointment.getAppointmentDate();
        selectedTime = builtAppointment.getAppointmentTime();

        return "redirect:appointment-confirmation";
    }


    @GetMapping(value = "appointment-confirmation")
    public String appointmentConfirmationPage(Model model, RedirectAttributes redirectAttributes) throws Exception {

        log.warn("I am in the appointment-confirmation controller method");

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

        if (registeredCustomer == null) {
            log.warn("Customer Not Registered!");
            redirectAttributes.addFlashAttribute("insertedDangerCustomer", "Please Complete Registration!");
            return "redirect:customer-registration";
        }

        if (buildingAppointment.getAppointmentDate() == null) {
            log.warn("Appointment Date & Time not set!");
            redirectAttributes.addFlashAttribute("insertedDangerDateTime", "Please choose appointment Date & Time!");
            return "redirect:book-appointment";
        }

        if (buildingAppointment.getAppointmentTime().isBlank()) {
            log.warn("Appointment Date & Time not set!");
            redirectAttributes.addFlashAttribute("insertedDangerDateTime", "Please choose appointment Date & Time!");
            return "redirect:book-appointment";
        }

        Appointment finalAppointment = new Appointment(selectedDate, selectedTime, selectedDevice, selectedTechnician, selectedOffice, registeredCustomer);
        appointmentRepoI.saveAndFlush(finalAppointment);

        model.addAttribute("appointment", finalAppointment);

        return "appointment-confirmation";
    }


    @PostMapping(value = "/post-appointment-confirmation")
    public String appointmentConfirmationProcess() {

        log.warn("I am in the appointmentConfirmationProcess controller method");

        return "/post-appointment-confirmation";
    }

    @GetMapping(value = "appointment-lookup")
    public String appointmentLookupPage(Model model) {

        log.warn("I am in the appointmentLookupPage controller method");

        Appointment tempAppointment = new Appointment();
        System.out.println(tempAppointment);

        Integer id = 0;

        model.addAttribute("id", id);

        return "appointment-lookup";
    }

    private Integer cancelID;

    @PostMapping(value = "/post-appointment-lookup")
    public String appointmentLookupProcess(@RequestParam(name = "12345", required = false) Integer id, RedirectAttributes redirectAttributes) throws Exception {

        log.warn("I am in the appointmentLookupProcess controller method");

        if(id == null) {
            log.warn("User did not enter any value!");
            redirectAttributes.addFlashAttribute("noValueDanger", "Please enter a value before searching!");
            return "redirect:appointment-lookup";
        }

        System.out.println(id);

        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<Integer> validIDs = new ArrayList<>();

        for (Appointment appointment : appointments) {
            validIDs.add(appointment.getId());
        }

        if (!validIDs.contains(id)) {
            log.warn("No Such Appointment Exists!");
            redirectAttributes.addFlashAttribute("noAppointmentDanger", "Sorry, no Appointment exists by this ID.");
            return "redirect:appointment-lookup";
        }

        Appointment flashAppointment = appointmentRepoI.findById(id).get();
        redirectAttributes.addFlashAttribute("appointment", flashAppointment);

        cancelID = id;

        return "redirect:appointment-lookup";
    }

}
