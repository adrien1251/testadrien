package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findById(Long id);
    Optional<Admin> findByEmail(String email);
}