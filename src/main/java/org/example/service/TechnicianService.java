package org.example.service;

import org.example.dao.TechnicianRepol;
import org.example.models.Office;
import org.example.dto.TechnicianDTO;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TechnicianService {

    TechnicianRepol technicianRepol;

    @Autowired
    public TechnicianService(TechnicianRepol technicianRepol) {
        this.technicianRepol = technicianRepol;
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Technician> getAllTechnicians() throws Exception {
        List<Technician> technicians = technicianRepol.findAll();

        if(technicians.isEmpty()) {
            log.debug("Empty list of Customers!!");
            throw new Exception("Empty List!");
        }

        return technicians;
    }

    public List<Technician> deviceTechniciansInOffice(Office office, String selectedDevice) {

        List<Technician> deviceTechniciansInOffice = new ArrayList<>();
        Set<Technician> techniciansInOfficeSet = office.getTechnicians();

        for (Technician technician : techniciansInOfficeSet) {
            if(technician.getDevices().contains(selectedDevice)) {
                System.out.println("Technician: " + technician + " contains: " + selectedDevice);
                deviceTechniciansInOffice.add(technician);
            }
        }

        return deviceTechniciansInOffice;
    }

    public List<Technician> filterTechniciansWithDevice(List<Technician> allTechnicians, String selectedDevice){

        List<Technician> deviceTechnicians = new ArrayList<>();

        for(int i = 0; i < allTechnicians.toArray().length; i++) {

            if ( allTechnicians.get(i).getDevices().contains(selectedDevice) ) {

                deviceTechnicians.add(allTechnicians.get(i));

            }
        }

        return deviceTechnicians;
    }

    public List<TechnicianDTO> getTechnicianEssentialInfo() {

        return technicianRepol.findAll().stream().map((oneTechnician) -> {
            return new TechnicianDTO(oneTechnician.getId(), oneTechnician.getName());
        }).collect(Collectors.toList());
    }
}
