package com.cbo.core.persistence.repository;


import com.cbo.core.persistence.model.OrganizationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {
    Optional<OrganizationalUnit> findOrganizationalUnitById(Long id);

    @Query("SELECT b FROM OrganizationalUnit b WHERE b.subProcess.id = :subProcessId")
    List<OrganizationalUnit> findAllBySubProcessId(Long subProcessId);

    Optional<OrganizationalUnit> findOrganizationalUnitByName(String name);
}
