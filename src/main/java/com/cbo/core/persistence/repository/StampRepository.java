package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    @Query("SELECT ST FROM Stamp ST WHERE ST.process.id = :processId " +
            "AND ST.isActive = :isActive")
    Stamp findByProcessIdAndIsActive(@Param("processId") Long processId,@Param("isActive") boolean isActive);

    @Query("SELECT ST FROM Stamp ST WHERE ST.subProcess.id = :subProcessId " +
            "AND ST.isActive = :isActive")
    Stamp findBySubProcessIdAndIsActive(@Param("subProcessId") Long subProcessId,@Param("isActive") boolean isActive);

    @Query("SELECT ST FROM Stamp ST WHERE ST.organizationalUnit.id = :organizationUnitId " +
            "AND ST.isActive = :isActive")
    Stamp findByOrganizationUnitIdIdAndIsActive(@Param("organizationUnitId") Long organizationUnitId,@Param("isActive") boolean isActive);

    @Query("SELECT ST FROM Stamp ST WHERE ST.branch.id = :branchId " +
            "AND ST.isActive = :isActive")
    Stamp findByBranchIdAndIsActive(@Param("branchId") Long processId,@Param("isActive") boolean isActive);


    @Query("SELECT ST FROM Stamp ST WHERE ST.district.id = :districtId " +
            "AND ST.isActive = :isActive")
    Stamp findByDistrictIdAndIsActive(@Param("districtId") Long processId,@Param("isActive") boolean isActive);


    @Query("SELECT ST FROM Stamp ST WHERE" +
            " ST.isActive = :isActive")
    List<Stamp> findAllActiveStamps(@Param("isActive") boolean isActive);
}
