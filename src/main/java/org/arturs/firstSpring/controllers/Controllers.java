package org.arturs.firstSpring.controllers;

import java.util.List;

import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.services.UserServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class Controllers {

    // CRUD operations:
    // Create, Read, Update, Delete
    // POST, GET, PUT, DELETE

    private final UserServices userService;

    // Iegūt visus mūsu jūzerus
    @GetMapping("/api/v1/users")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/api/v1/users")
    public String addUser(@RequestBody UserModel user) {
        System.out.println("user = " + user);
        int userId = userService.addUser(user);
        return String.valueOf(userId);
    }

}
