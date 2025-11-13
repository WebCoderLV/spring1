package org.arturs.firstSpring.controllers;

import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UserControllers {

    // CRUD operations:
    // Create, Read, Update, Delete
    // POST, GET, PUT, DELETE

    private final UserServices userService;

    @PostMapping("/user")
    public ResponseEntity<Long> addUser(@Valid @RequestBody UserModel user) {
        Long userId = userService.findOrSaveUser(user);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
