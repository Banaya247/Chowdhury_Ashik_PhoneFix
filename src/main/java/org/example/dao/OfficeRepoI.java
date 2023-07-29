package org.example.dao;

import org.example.models.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeRepoI extends JpaRepository<Office, Integer> {
    Optional<Office> findByName(String name);
}
