package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.User;
import com.ysu.xrandnet.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/demo")
public class TestController {
    private final UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public @ResponseBody
    String addNewUser(@RequestParam String username
            , @RequestParam String password) {

        User n = new User();
        n.setUsername(username);
        n.setPassword(password);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody
    String deleteAllUsers() {
        userRepository.deleteAll();
        return "Deleted";
    }
}