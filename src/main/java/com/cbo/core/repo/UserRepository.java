package com.cbo.core.repo;

import com.cbo.core.model.Role;
import com.cbo.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);
    List<User> findUsersByRolesId(Long roleId);

//    User findByUserName(String username);
    
    

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public User findByUserName(String username);

    Optional<Object> findByUsername(String username);
}
