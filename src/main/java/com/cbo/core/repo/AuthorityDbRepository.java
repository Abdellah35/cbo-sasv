package com.cbo.core.repo;

import com.cbo.core.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cbo.core.model.AuthorityDB;

import java.util.List;
import java.util.Optional;

public interface AuthorityDbRepository extends JpaRepository<AuthorityDB, Long> {

    Optional<AuthorityDB> findAuthorityById(Long id);
    Optional<AuthorityDB> findAuthorityDBByDivisionAndIsActive(Division division, boolean isActive);

    List<AuthorityDB> findAllByIsActive(boolean isActive);


    void deleteAuthorityById(Long id);
    long count();

}
