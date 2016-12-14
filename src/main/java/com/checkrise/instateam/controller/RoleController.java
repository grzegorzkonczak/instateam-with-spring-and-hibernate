package com.checkrise.instateam.controller;

import com.checkrise.instateam.model.Role;
import com.checkrise.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    // Roles Page - index all roles, add role button and field
    @RequestMapping("/roles")
    public String listRoles(Model model) {
        // Add to model all existing roles from database
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);

        // Add to model empty role to create new role by user
        model.addAttribute("role", new Role());
        return "role/roles";
    }

    // Add role to database
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult result) {
        // if user entered invalid input do not persist entry
        if (result.hasErrors()){
            return "redirect:/roles";
        }
        // if no errors - persist new entry
        roleService.save(role);
        return "redirect:/roles";

    }

    // Display details/edit page for specific role
    @RequestMapping("/roles/{roleId}/details")
    public String editRoleForm (@PathVariable Long roleId, Model model){
        Role role = roleService.findById(roleId);
        model.addAttribute("role", role);
        return "role/details";
    }
}
