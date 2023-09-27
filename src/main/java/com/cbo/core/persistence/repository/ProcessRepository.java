package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Long> {
}
