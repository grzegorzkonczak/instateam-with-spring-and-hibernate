package com.checkrise.instateam.dao;

import com.checkrise.instateam.model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Project> findAll() {
        // Open session
        Session session = sessionFactory.openSession();

        //Get Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        //Create Criteria
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
        Root<Project> contactRoot = criteria.from(Project.class);
        criteria.select(contactRoot);

        //Use criteria to query with session to fetch all Projects
        List<Project> projects = session.createQuery(criteria).getResultList();

        // Close session
        session.close();
        return projects;
    }

    @Override
    public Project findById(Long id) {
        // Open session
        Session session = sessionFactory.openSession();

        // Get project
        Project project = session.get(Project.class, id);

        // Close session
        session.close();
        return project;
    }

    @Override
    public void save(Project project) {
        // Open session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save/Update Project
        session.saveOrUpdate(project);

        // Commit transaction
        session.getTransaction().commit();

        // Close session
        session.close();
    }
}
