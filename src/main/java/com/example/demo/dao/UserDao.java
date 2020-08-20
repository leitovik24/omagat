package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;

public interface UserDao{

    List<User> allUsers();
    void add(User user);
    void delete(User user);
    void edit(User user);
    User getUserById(int id);
    User getUserByName(String name);
}