package com.checkrise.instateam.controller;

import com.checkrise.instateam.model.Collaborator;
import com.checkrise.instateam.model.Role;
import com.checkrise.instateam.service.CollaboratorService;
import com.checkrise.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CollaboratorController {
    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private RoleService roleService;

    // Collaborators Page - index all collaborators, add collaborator button and fields
    @RequestMapping("/collaborators")
    public String listCollaborators(Model model) {
        // Add to model all existing collaborators from database
        List<Collaborator> collaborators = collaboratorService.findAll();
        model.addAttribute("collaborators", collaborators);

        // Add to model all existing roles from database
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);

        // Add to model empty collaborator to create new collaborator by user
        model.addAttribute("collaborator", new Collaborator());
        return "collaborator/collaborators";
    }
}
