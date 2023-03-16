package com.cbo.core.service;

import com.cbo.core.exception.NoSuchUserExistsException;
import com.cbo.core.exception.UserAlreadyExistsException;
import com.cbo.core.model.Role;
import com.cbo.core.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role addRole(Role role){
        Role existingRole = roleRepository.findByName(role.getName());

        if (existingRole == null) {

            Role newrole = roleRepository.save(role);
            return newrole;
        }
        else
            throw new UserAlreadyExistsException(
                    "Role already exists!!");
    }


    public List<Role> findAllRole(){
        return roleRepository.findAll();
    }


    public Role updateRole(Role role){
        Role existingRole = roleRepository.findById(role.getId()).orElse(null);

        if (existingRole == null)
            throw new NoSuchUserExistsException("No Such Role exists!!");
        else {
            existingRole.setName(role.getName());
            Role updatedRole = roleRepository.save(existingRole);

            return updatedRole;
        }
    }


    public Role findRoleById(Long id){
        return roleRepository.findById(id).orElseThrow(() -> new NoSuchUserExistsException("Role By id "+ id + " was not found"));
    }


    public String deleteRole(Long id){
        Role existingRole = roleRepository.findById(id).orElse(null);

        if (existingRole == null)
            throw new NoSuchUserExistsException("No Such Role exists!!");
        else {
            roleRepository.deleteById(id);

            return "Record deleted Successfully";
        }
    }


}
