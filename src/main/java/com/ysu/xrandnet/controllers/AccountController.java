package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.User;
import com.ysu.xrandnet.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/account")
public class AccountController {
    private final UserRepository userRepository;

    @Autowired
    public AccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public @ResponseBody
    User addNewUser(@RequestParam String username
            , @RequestParam String password) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);
        return newUser;
    }

    @GetMapping(path = "/login")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping(path = "/getByUsername")
    public @ResponseBody
    User getAllUsers(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody
    String deleteAllUsers() {
        userRepository.deleteAll();
        return "Deleted";
    }
}