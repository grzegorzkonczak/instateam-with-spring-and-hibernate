package com.checkrise.instateam.controller;

import com.checkrise.instateam.model.Collaborator;
import com.checkrise.instateam.model.Project;
import com.checkrise.instateam.model.Role;
import com.checkrise.instateam.service.CollaboratorService;
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
public class CollaboratorController {
    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private RoleService roleService;

    // Collaborators Page - index all collaborators, add collaborator button and fields
    @RequestMapping("/collaborators")
    public String listCollaborators(Model model) {
        // Create list of all existing collaborators from database
        List<Collaborator> collaborators = collaboratorService.findAll();

        // Create list of all existing roles from database and add it to model
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);

        // Add to model empty collaborator to create new collaborator by user
        model.addAttribute("collaborator", new Collaborator());

        // Add to model project with all collaborators and all roles
        Project project = new Project();
        project.setCollaborators(collaborators);
        project.setRolesNeeded(roles);
        model.addAttribute("project", project);

        return "collaborator/collaborators";
    }

    // Add collaborator to database
    @RequestMapping(value = "/collaborators/add", method = RequestMethod.POST)
    public String addCollaborator(@Valid Collaborator collaborator, BindingResult result) {
        // if user entered invalid input do not persist entry
        if (result.hasErrors()){
            return "redirect:/collaborators";
        }
        // if no errors - persist new entry
        collaboratorService.save(collaborator);
        return "redirect:/collaborators";

    }

    // Save collaborators with roles to database
    @RequestMapping(value = "/collaborators/save-all", method = RequestMethod.POST)
    public String saveCollaborators(@Valid Project project, BindingResult result){
        // Extract Collaborators from project object
        List<Collaborator> collaborators = project.getCollaborators();

        // Update collaborators in database
        for (Collaborator collaborator : collaborators){
            collaboratorService.save(collaborator);
        }

        return "redirect:/collaborators";
    }

    // Display details/edit page for specific collaborator
    @RequestMapping("/collaborators/{collaboratorId}/details")
    public String editCollaboratorForm (@PathVariable Long collaboratorId, Model model){
        // Add all roles to model
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);

        // Add collaborator to model
        Collaborator collaborator = collaboratorService.findById(collaboratorId);
        model.addAttribute("collaborator", collaborator);
        return "collaborator/details";
    }

}
