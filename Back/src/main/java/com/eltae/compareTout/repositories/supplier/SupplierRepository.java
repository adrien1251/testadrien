package com.eltae.compareTout.repositories.supplier;

import com.eltae.compareTout.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findAll();
    Supplier findById(long id);
    List<Supplier> findByValidationDateIsNull();

}
