package com.cbo.core.controller;

import com.cbo.core.persistence.model.Visitor;
import com.cbo.core.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "localhost", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class visitorController {

    @Autowired
    private VisitorService visitorService;


    @GetMapping("/visitors")
    public List<Visitor> visitorslog() throws Exception {

        return visitorService.allVisitors();
    }
}