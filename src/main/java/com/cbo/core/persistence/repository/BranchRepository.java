package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
