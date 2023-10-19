package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.SubProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubProcessRepository extends JpaRepository<SubProcess, Long> {

//    @Query(" SELECT SP FROM SubProcess SP WHERE SP.process.id = :processId ")
//    List<SubProcess> findByProcessId(@Param("processId") Long processId);
}
