package com.cbo.core.repo;

import com.cbo.core.model.ERole;
import com.cbo.core.model.Role;
import com.cbo.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleById(Long id);

    @Query("SELECT u FROM Role u WHERE u.name = ?1")
    public Role findByName(ERole name);
}
