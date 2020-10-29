package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.allUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name= "id") int id){
        User user = userService.getUserById(id);
        return user != null ? new ResponseEntity<>(user,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/add")
    public void createUser(String name, String password, String roles){
        User user = new User(name, password);
        if(roles.equalsIgnoreCase("ADMIN")) {
            user.setRoles(Collections.singletonList(userService.getRoleById(1)));
        }
        else if(roles.equalsIgnoreCase("USER")) {
            user.setRoles(Collections.singletonList(userService.getRoleById(2)));
        }
        userService.add(user);
    }

    @PutMapping(value = "/edit")
    public void updateUser(String name, String password, String roles, int id){
        User user = userService.getUserById(id);
        user.setName(name);
        user.setPassword(password);

        if(roles.equalsIgnoreCase("ADMIN")) {
            user.setRoles(Collections.singletonList(userService.getRoleById(1)));
        }
        else if(roles.equalsIgnoreCase("USER")) {
            user.setRoles(Collections.singletonList(userService.getRoleById(2)));
        }
        userService.edit(user);
    }

    @DeleteMapping(value = "/delete")
    public void  deleteUser(int id){
        User user = userService.getUserById(id);
        userService.delete(user);
    }


}
