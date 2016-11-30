package com.checkrise.instateam.dao;

import com.checkrise.instateam.model.Collaborator;

import java.util.List;

public interface CollaboratorDao {
    List<Collaborator> findAll();
    Collaborator findById(Long id);
    void save(Collaborator collaborator);
}
