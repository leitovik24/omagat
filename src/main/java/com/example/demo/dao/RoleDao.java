package com.example.demo.dao;

import com.example.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Role getRoleById(int id);
    List<Role> getRolesByName(Set<Integer> ids);
    List<Role> getAllRoles();
}