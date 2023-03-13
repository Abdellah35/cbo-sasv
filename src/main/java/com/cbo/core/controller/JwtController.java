package com.cbo.core.controller;

import com.cbo.core.model.JwtRequest;
import com.cbo.core.model.User;
import com.cbo.core.model.Visitor;
import com.cbo.core.response.JwtResponse;
import com.cbo.core.service.JwtService;
import com.cbo.core.service.UserService;
import com.cbo.core.service.VisitorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "localhost", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private VisitorService visitorService;

    //This method used to generate a token for a user with valid username and password
    @ApiOperation(value = "Login ",
            notes = "Insert userName and userPassword to generate temporary token.",
            response = User.class)
    @PostMapping("/login")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        return jwtService.createJwtToken(jwtRequest);
    }

    @GetMapping("/visitors")
    public List<Visitor> visitorslog(@RequestBody JwtRequest jwtRequest) throws Exception {

        return visitorService.allVisitors();
    }
}
