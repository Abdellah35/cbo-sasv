package com.cbo.core.repo;

import com.cbo.core.model.User;
import com.cbo.core.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    @Query("SELECT u FROM Visitor u WHERE u.loggedTime = ?1")
    public List<Visitor> findByMonth(String loggedTime);
    @Query("SELECT u FROM Visitor u WHERE u.loggedTime = ?1 AND u.ip = ?2 ORDER BY id")
    public List<Visitor> findByIps(String loggedTime, String ip);
    @Query("SELECT u FROM Visitor u WHERE u.loggedTime = ?1 AND u.page = ?2 ORDER BY id")
    public List<Visitor> findByVisitors(String loggedTime, String page);
}
