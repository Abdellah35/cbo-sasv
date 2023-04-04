package com.cbo.core.controller;

import com.cbo.core.exception.UserAlreadyExistsException;
import com.cbo.core.model.ErrorResponse;
import com.cbo.core.model.Role;
import com.cbo.core.model.User;
import com.cbo.core.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController( UserService userService) {
        this.userService = userService;
    }

    //This method used to return all existing users
    @GetMapping("/all")
    @ApiOperation(value = "List all Users",
            notes = "List of all Users",
            response = User.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.findAllUser();
        return ResponseEntity.ok(users);
    }


    //The method bellow used to get a specific user with an Id
    @GetMapping("/find/{id}")
    @ApiOperation(value = "Finds Users by id",
        notes = "Provide an id to look up specific user from the User table",
        response = User.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@ApiParam(value =  "Id value for the User you need to retrieve", required = true) @PathVariable("id") Long id){

        User usr = userService.findUserById(id);
        usr.setPassword("encoded_password");
        return ResponseEntity.ok(usr);
    }


    //This method used for adding a new user with the given info
    @PostMapping("/add")
    @ApiOperation(value = "Add new User",
            notes = "When you create a user u need to provide Employee",
            response = User.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody User user){

        User newUser = userService.addUser(user,user.getRole());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(location).body(newUser);
    }


    //This method used to update an existing user
    @PutMapping("/update")
    @ApiOperation(value = "Update Existing User",
            notes = "All fields can be updated except User Id",
            response = User.class)
/*    @PreAuthorize("hasRole('ADMIN')")*/
    public ResponseEntity<?> updateUser(@RequestBody User user){
        System.out.println(user.isActive());
        userService.updateUser(user, user.isActive(), user.getRole());

        return ResponseEntity.noContent().build();
    }


    //using the following method admins can delete an existing user
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete Existing User",
            notes = "Delete User by Id",
            response = User.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}
