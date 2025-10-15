package org.arturs.firstSpring.controllers;

import java.util.List;

import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.services.UserServices;
import org.springframework.web.bind.annotation.GetMapping;
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

}
