package com.checkrise.instateam.controller;

import com.checkrise.instateam.model.Project;
import com.checkrise.instateam.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // Home page - index all projects
    @RequestMapping("/")
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects);
        return "project/index";
    }
}
