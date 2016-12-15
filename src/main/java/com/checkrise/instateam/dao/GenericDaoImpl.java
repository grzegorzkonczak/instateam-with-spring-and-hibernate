package com.checkrise.instateam.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class GenericDaoImpl<T> implements GenericDao<T>{
    // session factory: will be used in all concrete Dao implementations
    @Autowired
    private SessionFactory sessionFactory;

    // This member is needed to determine class type, because in `findAll`
    // method and `findById` we need to get class instance. Using generic T
    // we can know type of object, and in order to get the class instance we
    // we create this member, that is created in constructor.
    private Class<T> typeParameterClass;

    // unchecked warning added because of casting at the last line
    // Main point of this constructor is to get instance of the class
    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        typeParameterClass = (Class) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() {
        // Open session
        Session session = sessionFactory.openSession();

        //Get Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        //Create Criteria
        CriteriaQuery<T> criteria = builder.createQuery(typeParameterClass);
        Root<T> contactRoot = criteria.from(typeParameterClass);
        criteria.select(contactRoot);

        //Use criteria to query with session to fetch all t elements
        List<T> t = session.createQuery(criteria).getResultList();

        // Close session
        session.close();
        return t;
    }

    @Override
    public T findById(Long id) {
        // Open session
        Session session = sessionFactory.openSession();

        // Get element
        T t = session.get(typeParameterClass, id);

        // Close session
        session.close();
        return t;
    }

    @Override
    public void save(T t) {
        // Open session
        Session session = sessionFactory.openSession();

        // Begin transaction
        session.beginTransaction();

        // Save/Update element
        session.saveOrUpdate(t);

        // Commit transaction
        session.getTransaction().commit();

        // Close session
        session.close();
    }
}
