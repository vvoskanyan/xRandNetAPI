package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.User;
import com.ysu.xrandnet.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/account")
public class AccountsController {
    private final UserRepository userRepository;

    @Autowired
    public AccountsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/register", consumes = {"application/json"})
    public @ResponseBody
    User addNewUser(@RequestBody User user) {
//        this.userRepository.save(user);
        return user;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/login")
    public @ResponseBody
    User getAllUsers() {
        return null;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @DeleteMapping(path = "/delete/{username}")
    public @ResponseBody
    String deleteUser(@PathVariable String username) {
        userRepository.deleteByUsername(username);
        return "Deleted";
    }
}