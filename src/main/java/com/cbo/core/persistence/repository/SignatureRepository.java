package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Signature;
import com.cbo.core.persistence.model.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SignatureRepository extends JpaRepository<Signature, Long> {

    Signature findByEmployeeIdAndIsActive(Long employeeId, boolean isActive);


    @Query("SELECT SG FROM Signature SG WHERE" +
            " SG.isActive = :isActive")
    List<Signature> findAllActiveStamps(@Param("isActive") boolean isActive);

}
