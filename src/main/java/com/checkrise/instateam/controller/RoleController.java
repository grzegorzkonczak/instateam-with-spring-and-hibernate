package com.checkrise.instateam.controller;

import com.checkrise.instateam.model.Role;
import com.checkrise.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    // Roles Page - index all roles, add role button and field
    @RequestMapping("/roles")
    public String listRoles(Model model) {
        List<Role> roles = roleService.findAll();

        model.addAttribute("roles", roles);
        return "role/roles";
    }
}
