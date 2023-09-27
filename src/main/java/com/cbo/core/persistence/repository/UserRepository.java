package com.cbo.core.persistence.repository;


import com.cbo.core.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);

    List<User> findUsersByRolesId(Long roleId);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public User findByUserName(String username);

    @Query("SELECT u FROM User u WHERE u.createdAt = ?1")
    public List<User> findByMonth(String createdAt);

    Optional<Object> findByUsername(String username);
}
