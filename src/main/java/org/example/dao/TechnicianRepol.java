package org.example.dao;

import org.example.models.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnicianRepol extends JpaRepository<Technician, Integer> {
    Optional<Technician> findByName(String name);
}