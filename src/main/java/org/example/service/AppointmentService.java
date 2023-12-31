package org.example.service;

import org.example.dao.AppointmentRepoI;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppointmentService {

    AppointmentRepoI appointmentRepoI;

    @Autowired
    public AppointmentService(AppointmentRepoI appointmentRepoI) {
        this.appointmentRepoI = appointmentRepoI;
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Appointment> getAllAppointments() throws Exception {
        List<Appointment> appointments = appointmentRepoI.findAll();

        if(appointments.isEmpty()) {
            log.debug("Empty list of Appointments!!");
            throw new Exception("Empty List!");
        }

        return appointments;
    }
}
