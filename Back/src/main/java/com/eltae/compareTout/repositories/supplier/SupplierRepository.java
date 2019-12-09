package com.eltae.compareTout.repositories.supplier;

import com.eltae.compareTout.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT p FROM User p where p.class=?1")
    List<Supplier> findByDiscriminatorValue(String discriminatorValue);

    Optional<Supplier> findById(Long id);

    List<Supplier> findAllByValidationDateIsNull();

    @Query("SELECT p FROM User p where p.id=?1 and  p.class=?2")
    Optional<Supplier> findByIdAndDiscriminatorValue(Long id, String discriminatorValue);

    @Query("SELECT p FROM User p where p.email=?1 and  p.class=?2")
    Optional<Supplier> findByEmailAndDiscriminatorValue(String id, String discriminatorValue);


}
