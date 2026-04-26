package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // ✅ REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user){

        System.out.println("Name: " + user.getName());

        userRepo.save(user);
        return "User Registered";
    }

   
    @PostMapping("/login")
    public String login(@RequestBody User user){

        User u = userRepo.findByEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        );

        if(u != null){
            return "Login Success";
        } else {
            return "Invalid Credentials";
        }
    }
}