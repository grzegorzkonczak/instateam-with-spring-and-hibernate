package com.checkrise.instateam.dao;


import com.checkrise.instateam.model.Collaborator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CollaboratorDaoImpl implements CollaboratorDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Collaborator> findAll() {
        // Open session
        Session session = sessionFactory.openSession();

        //Get Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        //Create Criteria
        CriteriaQuery<Collaborator> criteria = builder.createQuery(Collaborator.class);
        Root<Collaborator> contactRoot = criteria.from(Collaborator.class);
        criteria.select(contactRoot);

        //Use criteria to query with session to fetch all Collaborators
        List<Collaborator> collaborators = session.createQuery(criteria).getResultList();

        // Close session
        session.close();
        return collaborators;
    }

    @Override
    public Collaborator findById(Long id) {
        // Open session
        Session session = sessionFactory.openSession();

        // Get collaborator
        Collaborator collaborator = session.get(Collaborator.class, id);

        // Close session
        session.close();
        return collaborator;
    }

    @Override
    public void save(Collaborator collaborator) {
        // Open session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save/Update Collaborator
        session.saveOrUpdate(collaborator);

        // Commit transaction
        session.getTransaction().commit();

        // Close session
        session.close();
    }
}
