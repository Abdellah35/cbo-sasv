package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRepository extends JpaRepository<Signature, Long> {

    Signature findByEmployeeIdAndIsActive(Long employeeId, boolean isActive);
}
