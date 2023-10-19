package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
