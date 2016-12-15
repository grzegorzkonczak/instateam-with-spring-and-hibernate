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

// Generic dao covers all basic methods (save, findById, findAll)
@Repository
public class CollaboratorDaoImpl extends GenericDaoImpl<Collaborator> implements CollaboratorDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void delete(Collaborator collaborator) {
        Session session = sessionFactory.openSession();
        // begin transaction
        session.beginTransaction();
        // detach collaborator from project_collaborator link table
        session.createSQLQuery(
                "DELETE project_collaborator " +
                        "WHERE COLLABORATORS_ID = " + collaborator.getId())
                .executeUpdate();

        // delete collaborator from his table: delete, commit
        session.delete(collaborator);
        session.getTransaction().commit();
        session.close();
    }
}
