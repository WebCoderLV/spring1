package org.arturs.firstSpring.services;

import java.util.List;
import java.util.Optional;

import org.arturs.firstSpring.interfaces.UserServiceInterface;
import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.repositories.RepositoryInterface;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServices implements UserServiceInterface {

    private final RepositoryInterface repository;

    public Boolean getUserByName(String name) {
        Optional<UserModel> user = repository.findByName(name);
        return user.isPresent();
    }

    public List<UserModel> getAllUsers() {
        return repository.findAll();
    }

    public int addUser(UserModel user) {
        UserModel savedUser = repository.save(user);
        return Math.toIntExact(savedUser.getId());
    }

    public UserModel getUserById(long id) {
        Optional<UserModel> user = repository.findById(id);
        return user.orElse(null);
    }

    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    public void updateUser(UserModel user) {
        repository.save(user);
    }

}
