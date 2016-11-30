package com.checkrise.instateam.dao;


import com.checkrise.instateam.model.Role;

import java.util.List;

public interface RoleDao {
  List<Role> findAll();
  Role findById(Long id);
  void save(Role role);
}
