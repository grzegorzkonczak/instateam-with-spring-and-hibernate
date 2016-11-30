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

@Repository
public class RoleDaoImpl implements RoleDao{
  @Autowired
  private SessionFactory sessionFactory;

  @Override
  @SuppressWarnings("unchecked")
  public List<Role> findAll() {
    // Open session
    Session session = sessionFactory.openSession();

    //Get Criteria Builder
    CriteriaBuilder builder = session.getCriteriaBuilder();

    //Create Criteria
    CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
    Root<Role> contactRoot = criteria.from(Role.class);
    criteria.select(contactRoot);

    //Use criteria to query with session to fetch all Roles
    List<Role> roles = session.createQuery(criteria).getResultList();

    // Close session
    session.close();
    return roles;
  }

  @Override
  public Role findById(Long id) {
    // Open session
    Session session = sessionFactory.openSession();

    // Get role
    Role role = session.get(Role.class, id);

    // Close session
    session.close();
    return role;
  }

  @Override
  public void save(Role role) {
    // Open session
    Session session = sessionFactory.openSession();

    // Begin transaction
    session.beginTransaction();

    // Save/Update Role
    session.saveOrUpdate(role);

    // Commit transaction
    session.getTransaction().commit();

    // Close session
    session.close();
  }
}
