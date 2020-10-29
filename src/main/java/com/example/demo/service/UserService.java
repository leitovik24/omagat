package com.example.demo.service;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import java.util.List;
import java.util.Set;

public interface UserService {
    Role getRoleById(int id);
    List<User> allUsers();
    void add(User user);
    void delete(User user);
    void edit(User user);
    User getUserById(int id);
    List<Role> getRolesByName(Set<Integer> ids);
}
