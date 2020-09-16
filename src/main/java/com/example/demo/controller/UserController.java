package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userPage(ModelMap model, Authentication authentication) {
        User user1 = (User) authentication.getPrincipal();
        String name = user1.getUsername();
        String pass = user1.getPassword();
        int id = user1.getId();
        List<Role> roles = (List<Role>) authentication.getAuthorities();
        model.addAttribute("name", name);
        model.addAttribute("pass", pass);
        model.addAttribute("id",id);
        model.addAttribute("roles", roles);
        model.addAttribute("user", authentication.getPrincipal());//Юзер который ща в сессии
        return "user";
    }

}