package org.example.dao;

import org.example.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepoI extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findById(int id);
}
