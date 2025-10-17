package org.arturs.firstSpring.services;

import java.util.List;

import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.repositories.RepositoryInterface;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServices {

    private final RepositoryInterface repository;

    public List<UserModel> getAllUsers() {
        return repository.findAll();
    }

    public int addUser(UserModel user) {
        repository.save(user);
        return Math.toIntExact(user.getId());
    }

}
