package org.arturs.firstSpring.services;

import java.util.Optional;

import org.arturs.firstSpring.interfaces.UserServiceInterface;
import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.repositories.GameRepository;
import org.arturs.firstSpring.repositories.UserRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServices implements UserServiceInterface {

    private final UserRepository repository;
    private final GameRepository gameRepository;

    public Long findOrSaveUser(UserModel user) {
        Optional<UserModel> existingUser = repository.findByNameAndPassword(user.getName(), user.getPassword());
        if (existingUser.isPresent()) {
            return existingUser.get().getId();
        } else {
            UserModel savedUser = repository.save(user);
            return savedUser.getId();
        }
    }

    public void deleteUser(Long userId) {
        // First delete all games associated with this user
        gameRepository.deleteByUserId(userId);
        // Then delete the user
        repository.deleteById(userId);
    }
}
