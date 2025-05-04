package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
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
    @PostMapping("/login")
    public void loginUser(@RequestParam String userName,
                          @RequestParam int password,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        User user = userRepository.findByUserNameAndPassword(userName, password);

        if (user != null) {
            request.getSession().setAttribute("loggedInUser", user);
            response.sendRedirect("/welcome");
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Invalid username or password.'); window.location.href = '/login.html';</script>");
        }
    }

    @GetMapping("/welcome")
    public void showWelcome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("loggedInUser");

        if (user == null) {
            response.sendRedirect("/login.html");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Welcome</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; padding: 40px; background: #f4f4f4; }");
        out.println(".card { background: white; padding: 20px; border-radius: 6px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); width: 400px; margin: auto; }");
        out.println("h2 { color: #333; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='card'>");
        out.println("<h2>Welcome, " + user.getUserName() + "!</h2>");
        out.println("<p><strong>Phone:</strong> " + user.getPhone() + "</p>");
        out.println("<p><strong>Blood Group:</strong> " + user.getBloodGroup() + "</p>");
        out.println("<p><strong>City:</strong> " + user.getCity() + "</p>");
        out.println("<p><strong>Age:</strong> " + user.getAge() + "</p>");
        out.println("</div></body></html>");
    }



}
