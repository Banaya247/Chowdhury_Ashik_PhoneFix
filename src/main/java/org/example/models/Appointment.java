package org.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Appointments")
@Slf4j
@NoArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please enter appointment Date")
    Date appointmentDate;  // validate so appointment is at/after current date.

    @NotNull(message = "Please enter an Appointment Time")
    String appointmentTime;

    String appointmentDevice;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    @NonNull
    Technician technician;

    @ManyToOne
    @JoinColumn(name = "office_id")
    @NonNull
    Office office;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NonNull
    Customer customer;

    public Appointment(@DateTimeFormat(pattern = "yyyy-MM-dd") Date appointmentDate, @NotNull(message = "Please enter an Appointment Time") String appointmentTime, String appointmentDevice, @NonNull Technician technician, @NonNull Office office, @NonNull Customer customer) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDevice = appointmentDevice;
        this.technician = technician;
        this.office = office;
        this.customer = customer;
    }

    public Appointment(@NonNull int id, @DateTimeFormat(pattern = "yyyy-MM-dd") Date appointmentDate, @NotNull(message = "Please enter an Appointment Time") String appointmentTime, String appointmentDevice, @NonNull Technician technician, @NonNull Office office, @NonNull Customer customer) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDevice = appointmentDevice;
        this.technician = technician;
        this.office = office;
        this.customer = customer;
    }
}
