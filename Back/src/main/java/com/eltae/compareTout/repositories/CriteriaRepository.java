package com.eltae.compareTout.repositories;
import com.eltae.compareTout.entities.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

    @Repository
    public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
        List<Criteria> findAll();
        Criteria findById(long id);
        Optional<Criteria> findByName(String name);

    }

