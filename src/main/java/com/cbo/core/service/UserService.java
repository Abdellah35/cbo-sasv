package com.cbo.core.service;

import com.cbo.core.exception.NoSuchUserExistsException;
import com.cbo.core.exception.UserAlreadyExistsException;
import com.cbo.core.model.ERole;
import com.cbo.core.model.Employee;
import com.cbo.core.model.Role;
import com.cbo.core.model.User;
import com.cbo.core.repo.RoleRepository;
import com.cbo.core.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final EmployeeService employeeService;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService, EmployeeService employeeService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.employeeService = employeeService;
    }


    public void addAdmin(){
        User userExist =userRepository.findByUserName("admin@cbo");
        if(userExist == null){

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode("a!5R@cbo");
            Role adminR = roleRepository.findByName(ERole.ROLE_ADMIN);
            User user = new User();
            user.setActive(true);
            user.setUsername("admin@cbo");
            user.setEmail("admin@cbo.com.et");
            user.setEmployee(null);
            Set<Role> roles = new HashSet<>();
            Role _role = roleRepository.findByName(ERole.ROLE_ADMIN);
            roles.add(_role);
            user.setRoles(roles);
            user.setPassword(encodedPassword);


            userRepository.save(user);
        }

    }


    public User addUser(User user, Long role){
        User existingUser =null;

        existingUser = userRepository.findByUserName(user.getUsername());

        System.out.println("here User role " + role);
        if (existingUser == null) {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            user.setPassword(encodedPassword);
            if (user.getEmployee() != null){
                Employee employee = employeeService.findEmployeeById(user.getEmployee().getId().longValue());
                user.setEmployee(employee);
            }


            Set<Role> roles = new HashSet<>();
            Role _role = roleRepository.findById(role).orElse(null);
            roles.add(_role);
            user.setRoles(roles);
            user.setActive(true);

            User saveduser=userRepository.save(user);
            return saveduser;
        }
        else
            throw new UserAlreadyExistsException(
                    "User already exists!!");

    }


    public List<User> findAllUser(){
        return userRepository.findAll();
    }


    public User updateUser(User user, boolean state, Long role){
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null)
            throw new NoSuchUserExistsException("No such user exists!");
        else {
            existingUser.setEmail(user.getEmail());
            existingUser.setUsername(user.getUsername());
            existingUser.setActive(state);
            if (user.getEmployee() != null){
                Employee employee = employeeService.findEmployeeById(user.getEmployee().getId().longValue());
                existingUser.setEmployee(employee);
            }
            if(user.getPassword() != "encoded_password"){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(user.getPassword());

                existingUser.setPassword(encodedPassword);
            }

            Set<Role> roles = new HashSet<>();
            if(role != null){
                Role _role = roleRepository.findById(role).orElse(null);
                roles.add(_role);
                user.setRoles(roles);
                existingUser.setRoles(roles);
            }


            User updatedUser = userRepository.save(existingUser);

            return updatedUser;
        }

    }


    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchUserExistsException("NO user exists with id = " + id));
    }


    public List<User> findUsersByRoleId(Long id){
        return userRepository.findUsersByRolesId(id);
    }


    public String deleteUser(Long id){

        User existingUser = userRepository.findById(id).orElse(null);
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName(ERole.ROLE_ADMIN);
        roles.add(role);
        if (existingUser == null || existingUser.getRoles() == roles)
            throw new NoSuchUserExistsException("No such user exists!");
        else {
            userRepository.deleteById(id);

            return "Record deleted successfully!";
        }
    }


}
