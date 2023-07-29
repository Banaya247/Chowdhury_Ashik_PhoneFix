package org.example.controllers;

import org.example.dao.TechnicianRepol;
import org.example.models.Office;
import org.example.dao.OfficeRepoI;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.service.OfficeService;
import org.example.service.TechnicianService;

import java.util.List;

//Home Controller posts, gets, and redirects to other pages
@Controller
@Slf4j
public class  HomeController {

    private final TechnicianRepol technicianRepol;
    private final OfficeRepoI officeRepoI;

    private final TechnicianService technicianService;
    private final OfficeService officeService;

    private String selectedDevice = "";

    @Autowired
    public HomeController(TechnicianRepol technicianRepol, OfficeRepoI officeRepoI, TechnicianService technicianService, OfficeService officeService) {
        this.technicianRepol = technicianRepol;
        this.officeRepoI = officeRepoI;
        this.technicianService = technicianService;
        this.officeService = officeService;
    }

    @GetMapping(value = {"/", "/index"})
    public String homePage(Model model) {

        log.warn("I am in the index controller method");

        model.addAttribute("device", "");



        return "index";
    }


    @PostMapping("/post-index")
    public String indexProcess(@ModelAttribute("device") String device, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        log.warn("I am in the indexProcess controller method");
        // Debug
        System.out.println("device: " + device);
        System.out.println("selectedDevice: " + selectedDevice);

        session.setAttribute("selectedDevice", device);
        selectedDevice = device;


        if (selectedDevice.isEmpty()) {
            log.warn("Device is empty! (No device selected) Returning to index");
            redirectAttributes.addFlashAttribute("insertedDanger", "Please select a device!");
            return "redirect:index";
        }

        else {
            return "redirect:select-store";
        }
    }


    @GetMapping(value = "/select-store")
    public String selectStorePage(HttpSession session, Model model, RedirectAttributes redirectAttributes) throws Exception {

        log.warn("I am in the select-store controller method");
        // Debug
        System.out.println("device: " + session.getAttribute("device"));
        System.out.println("selectedDevice: " + selectedDevice);

        if (selectedDevice.isEmpty()) {
            log.warn("Device is empty! Returning to index");
            redirectAttributes.addFlashAttribute("insertedDanger", "Please select a Device!");
            return "redirect:index";
        }

        List<Office> allOffices = officeService.getAllOffices();

        List<Technician> deviceTechniciansQueens = technicianService.deviceTechniciansInOffice(allOffices.get(0), selectedDevice);
        List<Technician> deviceTechniciansManhattan = technicianService.deviceTechniciansInOffice(allOffices.get(1), selectedDevice);
        List<Technician> deviceTechniciansBrooklyn = technicianService.deviceTechniciansInOffice(allOffices.get(2), selectedDevice);
        List<Technician> deviceTechniciansStatenIsland = technicianService.deviceTechniciansInOffice(allOffices.get(3), selectedDevice);

        model.addAttribute("infoSelectStore", selectedDevice);
        model.addAttribute("deviceTechniciansQueens", deviceTechniciansQueens);
        model.addAttribute("deviceTechniciansManhattan", deviceTechniciansManhattan);
        model.addAttribute("deviceTechniciansBrooklyn", deviceTechniciansBrooklyn);
        model.addAttribute("deviceTechniciansStatenIsland", deviceTechniciansStatenIsland);

        return "select-store";
    }

    @PostMapping("/post-select-store")
    public String selectStoreProcess(@RequestParam(name = "storeChoice") String store, @RequestParam(name = "technicianChoice") String technicianName, HttpSession session) {

        log.warn("I am in the selectStoreProcess controller method");

        log.info("Setting selectedOffice in HttpSession");
        // Getting the entire Office object by searching it by name
        session.setAttribute("selectedOffice", officeRepoI.findByName(store).get());

        log.info("Setting selectedTechnician in HttpSession");
        // Getting the entire Technician Object by searching it by name
        session.setAttribute("selectedTechnician", technicianRepol.findByName(technicianName).get());

        return "redirect:customer-registration";
    }
}