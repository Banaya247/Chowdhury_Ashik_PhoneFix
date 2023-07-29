package org.example;

import org.example.dao.*;
import org.example.models.AuthGroup;
import org.example.models.Customer;
import org.example.models.Office;
import org.example.models.Technician;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.example.service.TechnicianService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyCommandLineRunner implements CommandLineRunner {

    TechnicianRepol technicianRepoI;
    OfficeRepoI officeRepoI;
    CustomerRepoI customerRepoI;
    AppointmentRepoI appointmentRepoI;

    TechnicianService technicianService;

    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public MyCommandLineRunner(TechnicianRepol technicianRepoI, OfficeRepoI officeRepoI, CustomerRepoI customerRepoI, AppointmentRepoI appointmentRepoI, TechnicianService technicianService, AuthGroupRepoI authGroupRepoI) {
        this.technicianRepoI = technicianRepoI;
        this.officeRepoI = officeRepoI;
        this.customerRepoI = customerRepoI;
        this.appointmentRepoI = appointmentRepoI;
        this.technicianService = technicianService;
        this.authGroupRepoI = authGroupRepoI;
    }

    @PostConstruct
    void created() {
        log.warn("==================== org.example Got Created ====================");
    }

    @Override
    public void run(String... args) throws Exception {

        Customer user = new Customer("User", "User@gmail.com", "password");
        customerRepoI.saveAndFlush(user);

        authGroupRepoI.saveAndFlush(new AuthGroup("User@gmail.com", "ROLE_USER"));



        List<String> devicesD1 = new ArrayList<>(Arrays.asList("Android"));
        Technician d1 = new Technician(1, "Wendy Abbott", "Wendy@gmail.com", devicesD1);
        technicianRepoI.saveAndFlush(d1);

        List<String> devicesD2 = new ArrayList<>(Arrays.asList("Apple"));
        Technician d2 = new Technician(2, "Monica Phillips", "Monica@gmail.com", devicesD2);
        technicianRepoI.saveAndFlush(d2);

        List<String> devicesD3 = new ArrayList<>(Arrays.asList("Android"));
        Technician d3 = new Technician(3, "Savannah Anthony", "Savannah@gmail.com", devicesD3);
        technicianRepoI.saveAndFlush(d3);

        List<String> devicesD4 = new ArrayList<>(Arrays.asList("Apple"));
        Technician d4 = new Technician(4, "Gina Navarro", "Gina@gmail.com", devicesD4);
        technicianRepoI.saveAndFlush(d4);

        List<String> devicesD5 = new ArrayList<>(Arrays.asList("Android"));
        Technician d5 = new Technician(5, "Isra Mathis", "Isra@gmail.com", devicesD5);
        technicianRepoI.saveAndFlush(d5);

        List<String> devicesD6 = new ArrayList<>(Arrays.asList("Apple"));
        Technician d6 = new Technician(6, "Robin Calderon", "Robin@gmail.com", devicesD6);
        technicianRepoI.saveAndFlush(d6);

        List<String> devicesD7 = new ArrayList<>(Arrays.asList("Android"));
        Technician d7 = new Technician(7, "Taya Steele", "Taya@gmail.com", devicesD7);
        technicianRepoI.saveAndFlush(d7);
        technicianService.getTechnicianEssentialInfo();


        Office o1 = new Office(1, "Queens");
        o1.addTechnician(d1);
        o1.addTechnician(d2);
        officeRepoI.saveAndFlush(o1);

        Office o2 = new Office(2, "Manhattan");
        o2.addTechnician(d3);
        o2.addTechnician(d4);
        o2.addTechnician(d5);
        officeRepoI.saveAndFlush(o2);

        Office o3 = new Office(3, "Brooklyn");
        o3.addTechnician(d6);
        o3.addTechnician(d7);
        officeRepoI.saveAndFlush(o3);

        Office o4 = new Office(4, "StatenIsland");
        o4.addTechnician(d1);
        officeRepoI.saveAndFlush(o4);

    }
}
