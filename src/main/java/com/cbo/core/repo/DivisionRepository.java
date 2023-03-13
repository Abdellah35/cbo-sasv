package com.cbo.core.repo;

import com.cbo.core.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DivisionRepository extends JpaRepository<Division, Long> {
    Optional<Division> findDivisionById(Long id);

    List<Division> findDivisionByName(String name);

    List<Division> findDivisionByParent(Division parent);
}
