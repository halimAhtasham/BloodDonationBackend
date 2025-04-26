package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register.html";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/login";  // after registration, go to login page
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login.html";
    }
}
