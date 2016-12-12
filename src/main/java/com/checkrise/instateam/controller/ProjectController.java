package com.checkrise.instateam.controller;

import com.checkrise.instateam.model.Project;
import com.checkrise.instateam.model.Role;
import com.checkrise.instateam.model.Status;
import com.checkrise.instateam.service.ProjectService;
import com.checkrise.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    // Home page - index all projects
    @RequestMapping("/")
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects);
        return "project/index";
    }

    // New Project page - form to add new project to database
    @RequestMapping("/add")
    public String addProjectForm(Model model) {
        // Add all roles from database to model
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);

        // Add possible statuses to model
        List<Status> statuses = Arrays.asList(Status.values());
        model.addAttribute("statuses", statuses);

        // Add empty new project object to model
        model.addAttribute("project", new Project());
        return "project/add_edit_project";
    }

    // Add project to database
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addCollaborator(@Valid Project project, BindingResult result) {
        // Get rolesNeeded from created project to validate if there are any roles selected
        List<Role> rolesNeeded = project.getRolesNeeded().stream()
                .filter(role -> role != null)
                .collect(Collectors.toList());

        if (rolesNeeded.size() == 0){
            result.rejectValue("rolesNeeded",
                    "error.project",
                    "Please select at least one role");
        }

        // if user entered invalid input do not persist entry
        if (result.hasFieldErrors("name")
                || result.hasFieldErrors("description")
                || result.hasFieldErrors("status")
                || result.hasFieldErrors("rolesNeeded")) {
            return "redirect:/";
        }

        // add right neededRoles to project
        project.setRolesNeeded(rolesNeeded);

        // if no errors - persist new entry
        projectService.save(project);
        return "redirect:/";

    }
}
