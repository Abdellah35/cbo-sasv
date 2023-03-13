package com.cbo.core.service;

import com.cbo.core.model.Visitor;
import com.cbo.core.repo.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository repository;

    public Visitor saveVisitorInfo(Visitor visitor) {
        return repository.save(visitor);
    }

    public List<Visitor> allVisitors(){
        return repository.findAll();
    }

}