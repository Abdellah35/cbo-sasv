package com.cbo.core.controller;

import com.cbo.core.exception.NoSuchUserExistsException;
import com.cbo.core.exception.ResourceNotFoundException;
import com.cbo.core.model.Division;
import com.cbo.core.model.Role;
import com.cbo.core.model.User;
import com.cbo.core.repo.RoleRepository;
import com.cbo.core.repo.UserRepository;
import com.cbo.core.service.RoleService;
import com.cbo.core.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final UserService userService;

    private final RoleService roleService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(UserService userService, RoleService roleService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @GetMapping("/all")
    @ApiOperation(value = "List all Role",
            notes = "List of all available Roles",
            response = Division.class)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> allRoles(){
        List<Role> roles = roleService.findAllRole();

        return ResponseEntity.ok(roles);
    }


    @GetMapping("find/{id}")
    @ApiOperation(value = "Finds Role by id",
            notes = "Provide an id to look up specific role from the Role table",
            response = Division.class)
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id){
        Role role=  roleService.findRoleById(id);

        return ResponseEntity.ok(role);
    }


    @PostMapping("/add")
    @ApiOperation(value = "Add new Role",
            notes = "When you create a role u need to provide Role name",
            response = Role.class)
    public ResponseEntity<Role> createRole(@RequestBody Role role){

        Role newRole = roleService.addRole(role);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newRole.getId()).toUri();

        return ResponseEntity.created(location).body(newRole);
    }


    @PutMapping("update")
    @ApiOperation(value = "Update Existing Role",
            notes = "All fields can be updated except Role Id",
            response = User.class)
    public ResponseEntity<?> updateRole(@RequestBody Role role){

        roleService.updateRole(role);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete existing Role",
            notes = "Delete Role by Id",
            response = User.class)
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long id){

        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/users/{userId}")
    public ResponseEntity<List<User>> addRole(@PathVariable(value = "userId") Long userId, @RequestBody Role roleRequest) {

        Role role = userRepository.findById(userId).map(user -> {
            long roleId = roleRequest.getId();
            Set<Role> roles = new HashSet<>();
            if (roleId != 0L) {
                Role _role = roleRepository.findRoleById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Role with id = " + roleId));

                roles.add(_role);
                user.setRoles(roles);
                userRepository.save(user);
                return _role;
            }

            return null;
        }).orElseThrow(() -> new NoSuchUserExistsException("Not found User with id = " + userId));

        List<User> user2 = userService.findUsersByRoleId(role.getId());

        return new ResponseEntity<>(user2, HttpStatus.CREATED);
    }

}
