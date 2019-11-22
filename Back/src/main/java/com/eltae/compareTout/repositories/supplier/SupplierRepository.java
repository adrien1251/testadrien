package com.eltae.compareTout.repositories.supplier;

import com.eltae.compareTout.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT p FROM User p where p.class=:discriminatorValue")
    List<Supplier> findByDiscriminatorValue(@Param("discriminatorValue") String discriminatorValue);
    @Query("SELECT p FROM User p where p.id=:id")
    Optional<Supplier> findById(Long id);
    List<Supplier> findByValidationDateIsNull();
    @Query("SELECT p FROM User p where p.id=:id and  p.class=:discriminatorValue")
    Optional<Supplier> findByIdAndDiscriminatorValue(Long id,@Param("discriminatorValue") String discriminatorValue);


}
