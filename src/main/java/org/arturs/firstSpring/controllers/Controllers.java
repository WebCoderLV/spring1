package org.arturs.firstSpring.controllers;

import java.util.List;

import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class Controllers {

    // CRUD operations:
    // Create, Read, Update, Delete
    // POST, GET, PUT, DELETE

    private final UserServices userService;

    @GetMapping("/user/{name}")
    public ResponseEntity<Boolean> getUserByName(@PathVariable String name) {
        Boolean exists = userService.getUserByName(name);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Boolean> getMethodName(@RequestParam String param) {
        return new ResponseEntity<>(userService.getUserByName(param), HttpStatus.OK);
    }

    // Iegūt visus mūsu jūzerus
    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Integer> addUser(@RequestBody UserModel user) {
        int userId = userService.addUser(user);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}") // http://localhost:8080/api/v1/users/23
    public ResponseEntity<UserModel> getUserById(@PathVariable long id) {
        UserModel user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody UserModel user) {
        user.setId(id);
        userService.updateUser(user);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
