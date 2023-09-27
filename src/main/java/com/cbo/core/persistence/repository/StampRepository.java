package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    @Query("SELECT ST FROM Stamp ST WHERE ST.processId = :processId " +
            "AND ST.isActive = :isActive")
    Stamp findByProcessIdAndIsActive(@Param("processId") Long processId,@Param("isActive") boolean isActive);

    @Query("SELECT ST FROM Stamp ST WHERE ST.subProcessId = :subProcessId " +
            "AND ST.isActive = :isActive")
    Stamp findBySubProcessIdAndIsActive(@Param("subProcessId") Long subProcessId,@Param("isActive") boolean isActive);

    @Query("SELECT ST FROM Stamp ST WHERE ST.organizationUnitId = :organizationUnitId " +
            "AND ST.isActive = :isActive")
    Stamp findByOrganizationUnitIdIdAndIsActive(@Param("organizationUnitId") Long organizationUnitId,@Param("isActive") boolean isActive);
}
