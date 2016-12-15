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

// For now generic dao covers all needed methods (save, findById, findAll)
@Repository
public class CollaboratorDaoImpl extends GenericDaoImpl<Collaborator> implements CollaboratorDao{

}
