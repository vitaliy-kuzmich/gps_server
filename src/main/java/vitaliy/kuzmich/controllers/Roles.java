package vitaliy.kuzmich.controllers;

import vitaliy.kuzmich.model.Role;
import vitaliy.kuzmich.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Roles {
    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/all-roles")
    public List<Role> getAll() {
        return roleService.getAll();

    }

    @RequestMapping(value = "/add-role")
    public void add(Role role) throws Exception {
        roleService.addOrUpdateUser(role);
    }


}
