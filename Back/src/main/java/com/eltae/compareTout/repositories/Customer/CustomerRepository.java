package com.eltae.compareTout.repositories.Customer;

import com.eltae.compareTout.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT p FROM User p where p.class=:discriminatorValue")
    List<Customer> findByDiscriminatorValue(String discriminatorValue);
    @Query("SELECT p FROM User p where p.id=:id")
    Optional<Customer> findById(Long id);
    @Query("SELECT p FROM User p where p.id=:id and  p.class=:discriminatorValue")
    Optional<Customer> findByIdAndDiscriminatorValue(Long id, String discriminatorValue);

    Optional<Customer> findByEmail(String email);
}
