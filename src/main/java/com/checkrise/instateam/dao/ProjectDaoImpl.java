package com.checkrise.instateam.dao;

import com.checkrise.instateam.model.Project;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Override
    public void delete(Project project) {
        Session session = sessionFactory.openSession();
        // begin transaction
        session.beginTransaction();
        // detach project from project_role link table,
        session.createSQLQuery(
                "DELETE project_role " +
                        "WHERE PROJECT_ID = " + project.getId())
                .executeUpdate();
        // detach project from project_collaborator link table
        session.createSQLQuery(
                "DELETE project_collaborator " +
                        "WHERE PROJECT_ID = " + project.getId())
                .executeUpdate();

        // delete project from his table: delete, commit
        session.delete(project);
        session.getTransaction().commit();
        session.close();
    }

}
