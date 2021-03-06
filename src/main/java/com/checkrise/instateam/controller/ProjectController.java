package com.checkrise.instateam.controller;

import com.checkrise.instateam.model.Collaborator;
import com.checkrise.instateam.model.Project;
import com.checkrise.instateam.model.Role;
import com.checkrise.instateam.model.Status;
import com.checkrise.instateam.service.CollaboratorService;
import com.checkrise.instateam.service.ProjectService;
import com.checkrise.instateam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CollaboratorService collaboratorService;

    // Home page - index all projects
    @RequestMapping("/")
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();
        // Sort project list to display projects in order of creating date
        projects = projects.stream()
                .sorted(Comparator.comparing(Project::getCreationDate))
                .collect(Collectors.toList());

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

    // Add project to database or update existing project
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addEditProject(@Valid Project project, BindingResult result) {
        // Get rolesNeeded from project to validate if there are any roles selected
        List<Role> rolesNeeded = project.getRolesNeeded().stream()
                .filter(role -> role != null)
                .collect(Collectors.toList());

        if (rolesNeeded.size() == 0) {
            result.rejectValue("rolesNeeded",
                    "error.project",
                    "Please select at least one role");
        }

        // If project does not have creation date add it to project
        if (project.getCreationDate() == null){
            project.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        }

        List<Collaborator> newCollaborators;
        // If there are collaborators in project
        if (project.getCollaborators() != null) {
            // Get old collaborators from project and cross check
            // with any changes to rolesNeeded in project
            List<Collaborator> oldCollaborators = project.getCollaborators();
            newCollaborators = new ArrayList<>();

            // check if collaborator from old list has role
            // that exist in potentially modified rolesNeeded list
            // if yes - add that collaborator to newCollaborators
            for (Collaborator collaborator : oldCollaborators) {
                if (rolesNeeded.contains(collaborator.getRole())) {
                    newCollaborators.add(collaborator);
                }
            }
            // Assign new collaborators to project
            project.setCollaborators(newCollaborators);
        }

        // if user entered invalid input do not persist entry
        if (result.hasFieldErrors("name")
                || result.hasFieldErrors("description")
                || result.hasFieldErrors("status")
                || result.hasFieldErrors("rolesNeeded")){
            return "redirect:/";
        }

        // add proper neededRoles to project
        project.setRolesNeeded(rolesNeeded);

        // if no errors - persist entry
        projectService.save(project);
        return "redirect:/projects/" + project.getId();
    }

    // View project details
    @RequestMapping("/projects/{projectId}")
    public String projectDetails(@PathVariable Long projectId, Model model) {
        // Get project whose id is projectId
        Project project = projectService.findById(projectId);

        // here we synchronize collaborators with roles needed, so that user
        // can see assigned collaborators for his roles.
        List<Collaborator> collaboratorsSynchronized =
                generateSynchronizedWithRolesNeededCollaboratorsList(project);
        project.setCollaborators(collaboratorsSynchronized);

        model.addAttribute("project", project);
        return "project/details";
    }

    // Edit existing project
    @RequestMapping("/projects/{projectId}/edit")
    public String editProjectForm(@PathVariable Long projectId, Model model) {
        // Add all roles from database to model
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);

        // Add possible statuses to model
        List<Status> statuses = Arrays.asList(Status.values());
        model.addAttribute("statuses", statuses);

        // Get project whose id is projectId
        Project project = projectService.findById(projectId);

        // Problem:
        //   project roles that were selected by user will not be
        //   shown properly if rolesNeeded of project and allRoles from database
        //   are not synchronized: that because of the way we interact with
        //   thymeleaf: cycle in cycle.
        // Solution:
        //   We have to make projects rolesNeeded same size with all roles,
        //   putting on the missing position nulls. This way our arrays being
        //   synchronized by size and index will be correctly displayed in
        //   thymeleaf template
        project.setRolesNeeded(
                generateSynchronizedWithAllRolesRolesNeededList(
                        roles, project.getRolesNeeded()
                ));

        model.addAttribute("project", project);

        return "project/add_edit_project";
    }

    // generate roles needed list synchronized with all roles list, by
    // index and size, because initially we can have:
    // all roles: [Role(1), Role(2)]
    // project.rolesNeeded: [Role(2)]
    // After synchronization will be
    // [null, Role(2)]
    // used in method above `editProject`
    private List<Role> generateSynchronizedWithAllRolesRolesNeededList(
            List<Role> allRoles, List<Role> rolesNeeded
    ) {
        // initialize synchronized array
        List<Role> synchronizedRolesNeededListWithNulls =
                new ArrayList<>();
        // cycle through all roles
        for (Role role : allRoles) {
            // if rolesNeeded has role, we put this role in new list
            // at the right index. Else we put null
            if (rolesNeeded.contains(role)) {
                synchronizedRolesNeededListWithNulls.add(role);
            } else {
                synchronizedRolesNeededListWithNulls.add(null);
            }
        }
        return synchronizedRolesNeededListWithNulls;
    }


    // Edit project collaborators
    @RequestMapping("/projects/{projectId}/collaborators")
    public String editProjectCollaboratorsForm(@PathVariable Long projectId, Model model) {
        // Get all Collaborators from database
        List<Collaborator> collaborators = collaboratorService.findAll();
        model.addAttribute("collaborators", collaborators);

        // Get project whose id is projectId
        Project project = projectService.findById(projectId);

        // here we synchronize collaborators with roles needed, so that user
        // can see assigned collaborators for his roles.
        List<Collaborator> collaboratorsSynchronized =
                generateSynchronizedWithRolesNeededCollaboratorsList(project);
        project.setCollaborators(collaboratorsSynchronized);

        // add project to model
        model.addAttribute("project", project);

        return "project/editCollaborators";
    }

    private List<Collaborator> generateSynchronizedWithRolesNeededCollaboratorsList(Project project) {
        // list of collaborators synchronized with roles needed list by
        // index and size: so that
        // Role(1) -> null
        // Role(2) --> Collaborator(withRole(2))
        // for case where rolesNeeded are: [Role(1), Role(2)]
        // and project.collaborators are: [Collaborator(withRole(2))]
        // This list in opposite to project.collaborators will look like
        // [null, Collaborator(withRole(2))]
        List<Collaborator> projectCollaboratorsWithNullsForUnAssigned =
                new ArrayList<>();

        // cycle through roles needed
        for (Role roleNeeded : project.getRolesNeeded()) {
            boolean collaboratorIsAssigned = false;

            // cycle through projectCollaborators
            for (Collaborator projectCollaborator : project.getCollaborators()) {

                // if collaborator is assigned to this role: we check by
                // unique ids
                if (projectCollaborator.getRole().getId() ==
                        roleNeeded.getId()) {

                    // we assign collaborator
                    collaboratorIsAssigned = true;

                    // add this collaborated synchronized with role
                    projectCollaboratorsWithNullsForUnAssigned
                            .add(projectCollaborator);

                    // break the cycle
                    break;
                }
            }

            // if after cycling through all collaborators we found that
            // no collaborator was assigned for this roles, we add
            // null at this index in our new array
            if (!collaboratorIsAssigned) {
                projectCollaboratorsWithNullsForUnAssigned.add(null);
            }
        }
        return projectCollaboratorsWithNullsForUnAssigned;
    }

    // Save edited collaborators for project with passed id to database
    @RequestMapping(value = "/projects/save-collaborators", method = RequestMethod.POST)
    public String saveCollaboratorsForProject(@Valid Project projectWithIdAndCollaborators, BindingResult result) {
        // Get project object from database defined by passed id from form
        Project project = projectService.findById(projectWithIdAndCollaborators.getId());

        // Create empty collaborators list
        List<Collaborator> collaboratorsToAdd = new ArrayList<>();

        // Populate list by using list of collaborators id's selected by user
        for (Collaborator collaborator : projectWithIdAndCollaborators.getCollaborators()) {
            collaboratorsToAdd.add(collaboratorService.findById(collaborator.getId()));
        }

        // Set new collaborators in project
        project.setCollaborators(collaboratorsToAdd);

        // Persist project
        projectService.save(project);

        return "redirect:/projects/" + project.getId();
    }

    // Delete project
    @RequestMapping("/projects/{projectId}/delete")
    public String deleteProject(@PathVariable Long projectId, Model model) {
        // Get project whose id is projectId
        Project project = projectService.findById(projectId);

        // Delete project from database
        projectService.delete(project);

        return "redirect:/";
    }
}
