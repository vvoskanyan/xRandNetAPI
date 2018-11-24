package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.User;
import com.ysu.xrandnet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/account")
public class AccountsController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountsController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/register", consumes = {"application/json"})
    public @ResponseBody
    User addNewUser(@RequestBody User user) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        this.userService.saveUser(user);
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
        userService.deleteByUsername(username);
        return "Deleted";
    }
}