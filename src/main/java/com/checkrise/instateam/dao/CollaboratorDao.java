package com.checkrise.instateam.dao;

import com.checkrise.instateam.model.Collaborator;

import java.util.List;

// Generic dao covers all basic methods (save, findById, findAll)
public interface CollaboratorDao extends GenericDao<Collaborator> {

    void delete(Collaborator collaborator);
}
