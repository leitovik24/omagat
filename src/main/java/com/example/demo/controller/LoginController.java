package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String loginGet(Model model, String error, String logout) {
        if(error != null) {
            model.addAttribute("error", "Username or password gavno");
        }
        if(logout != null){
            model.addAttribute("message", "Logged out ok");
        }

        return "login2";
    }

    @GetMapping("error")
    public String Hello(Model model){
        return "login2";
    }

    @PostMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

}
