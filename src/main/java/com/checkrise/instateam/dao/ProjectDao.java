package com.checkrise.instateam.dao;


import com.checkrise.instateam.model.Project;

import java.util.List;

public interface ProjectDao {
    List<Project> findAll();
    Project findById(Long id);
    void save(Project project);
}
