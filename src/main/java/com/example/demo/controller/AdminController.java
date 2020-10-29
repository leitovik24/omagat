package com.example.demo.controller;

import com.example.demo.dao.RoleDao;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin/**")
public class AdminController {

    private final RoleDao roleDao;
    private final UserService service;
    @Autowired
    public AdminController(RoleDao roleDao, UserService service) {
        this.roleDao = roleDao;
        this.service = service;
    }

    @GetMapping
    public String adminPage(ModelMap model, Authentication authentication) {
        List<User> users = service.allUsers();
        User user1 = (User) authentication.getPrincipal();
        String lol = user1.getUsername();
        String lol2 = user1.getName();
        String lol3 = user1.getPassword();
        int lol4 = user1.getId();
        List<Role> roles1 = roleDao.getAllRoles();
        model.addAttribute("roles", roles1);
        List<Role> roles = (List<Role>) authentication.getAuthorities();
        model.addAttribute("name1", lol);
        model.addAttribute("roles1", roles);
        model.addAttribute("users", users);
        model.addAttribute("lol2", lol2);
        model.addAttribute("lol3",lol3);
        model.addAttribute("lol4",lol4);
        return "admin2";
    }


    @GetMapping(value = "/add")
    public String addUserPage(Model model) {
        List<Role> roles1 = roleDao.getAllRoles();
        model.addAttribute("roles", roles1);
        model.addAttribute("addUser");
        return "add";
    }

    @PostMapping(value = "/add")
    public String addUser(@RequestParam String name,
                          @RequestParam String password,
                          @RequestParam Set<Integer> roles) {
        User user = new User(name, password);
        user.setRoles(service.getRolesByName(roles));
        service.add(user);
        return "redirect:/admin";
    }


    @PostMapping(value = "/edit")
    public String editUser(@RequestParam("id") Integer id,
                           @RequestParam String name,
                           @RequestParam String password,
                           @RequestParam Set<Integer> roles){
        User user = new User(id,name, password);
        user.setRoles(service.getRolesByName(roles));
        service.edit(user);
        return "redirect:/admin";

    }


    @PostMapping("delete")
    public String deleteUser(@RequestParam("id") Integer id){
        User user = service.getUserById(id);
        service.delete(user);
        return "redirect:/admin";
    }
}
