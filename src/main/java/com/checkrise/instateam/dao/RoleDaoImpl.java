package com.checkrise.instateam.dao;


import com.checkrise.instateam.model.Role;
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
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void delete(Role role) {
        Session session = sessionFactory.openSession();
        // begin transaction
        session.beginTransaction();

        // Delete entries from project_collaborator before deleting collaborators
        // that no longer has roles assigned
        session.createSQLQuery("DELETE project_collaborator " +
                "WHERE collaborators_id IN (SELECT id FROM collaborator WHERE " +
                "role_id = " + role.getId() + ")").executeUpdate();

        // Delete entries from collaborators table (because this application does not
        // allow for collaborators without role assigned)
        session.createSQLQuery("DELETE collaborator " +
                            "WHERE role_id = " + role.getId()).executeUpdate();

        // Delete entries from project_role link table
        session.createSQLQuery(
                "DELETE project_role " +
                        "WHERE rolesneeded_id = " + role.getId())
                .executeUpdate();

        // delete role from its table: delete, commit
        session.delete(role);
        session.getTransaction().commit();
        session.close();
    }
}
