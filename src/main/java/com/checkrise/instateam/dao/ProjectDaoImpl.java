package com.checkrise.instateam.dao;

import com.checkrise.instateam.model.Project;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

// Generic dao covers methods save and findAll, only difference is in findById (collection initialization)
@Repository
public class ProjectDaoImpl extends GenericDaoImpl<Project> implements ProjectDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Project findById(Long id) {
        // Open session
        Session session = sessionFactory.openSession();

        // Get project
        Project project = session.get(Project.class, id);

        // Initialize collections
        Hibernate.initialize(project.getCollaborators());
        Hibernate.initialize(project.getRolesNeeded());

        // Close session
        session.close();
        return project;
    }

}
