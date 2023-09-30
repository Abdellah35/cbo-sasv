package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
}
