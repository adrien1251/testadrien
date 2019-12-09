package com.eltae.compareTout.repositories;

import com.eltae.compareTout.entities.InsertionErrors;
import com.eltae.compareTout.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsertionErrorsRepository extends JpaRepository<InsertionErrors, Long> {
    List<InsertionErrors> findAll();
    InsertionErrors findInsertionErrorsById(long insertionErrors);
    List<InsertionErrors> findAllBySupplierId(long idSupplier);

}