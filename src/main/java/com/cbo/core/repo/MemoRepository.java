package com.cbo.core.repo;

import com.cbo.core.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByRefnomContaining(Long refnom);
    Optional<Memo> findByRefnom(Long refnom);
    void deleteByRefnom(Long refnom);
}
