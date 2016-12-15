package com.checkrise.instateam.dao;


import com.checkrise.instateam.model.Role;

import java.util.List;

// Generic dao covers all basic methods (save, findById, findAll)
public interface RoleDao extends GenericDao<Role> {
    void delete(Role role);
}
