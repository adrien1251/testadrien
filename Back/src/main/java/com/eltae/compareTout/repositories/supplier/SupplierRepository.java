package com.eltae.compareTout.repositories.supplier;

import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT p FROM User p where p.class=:discriminatorValue")
    List<Supplier> findByDiscriminatorValue(@Param("discriminatorValue") String discriminatorValue);
    @Query("SELECT p FROM User p where p.id=:idval")
    Supplier findById(long idval);
    List<Supplier> findByValidationDateIsNull();

}
