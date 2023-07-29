package org.example.service;

import org.example.dao.OfficeRepoI;
import org.example.dto.OfficeDTO;
import org.example.models.Office;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OfficeService {

    OfficeRepoI officeRepoI;

    @Autowired
    public OfficeService(OfficeRepoI officeRepoI) {
        this.officeRepoI = officeRepoI;
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Office> getAllOffices() throws Exception {
        List<Office> offices = officeRepoI.findAll();

        if(offices.isEmpty()) {
            log.debug("Empty list of Office!!");
            throw new Exception("Empty List!");
        }

        return offices;
    }


    public List<Office> filterOfficesWithDevice(List<Office> allOffices, List<Technician> deviceTechnicians, String selectedDevice){

        List<Office> deviceOffices = new ArrayList<>();

        for(int i = 0; i < allOffices.toArray().length; i++) {
            System.out.println("Office: " + allOffices.get(i).getId());
            System.out.println("Technicians in this office: " + allOffices.get(i).getTechnicians());
            System.out.println("User Selected Device: " + selectedDevice);
            System.out.println("deviceTechnicians: " + deviceTechnicians);
            System.out.println("Similar: " + allOffices.get(i).getTechnicians().stream().filter(deviceTechnicians::contains).collect(Collectors.toList()));

            if (allOffices.get(i).getTechnicians().stream().filter(deviceTechnicians::contains).collect(Collectors.toList()).isEmpty()) {
                System.out.println("false, lists are not similar");
            }
            else {
                System.out.println("true, lists are similar");
                deviceOffices.add(allOffices.get(i));
            }
            System.out.println();
        }

        return deviceOffices;
    }

    public List<OfficeDTO> getOfficeEssentialInfo() {

        return officeRepoI.findAll().stream().map((oneOffice) -> {
            return new OfficeDTO(oneOffice.getId(), oneOffice.getName());
        }).collect(Collectors.toList());

    }


}
