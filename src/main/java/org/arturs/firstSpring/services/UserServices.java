package org.arturs.firstSpring.services;

import java.util.Optional;

import org.arturs.firstSpring.interfaces.UserServiceInterface;
import org.arturs.firstSpring.models.UserDTO;
import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.repositories.GameRepository;
import org.arturs.firstSpring.repositories.UserRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServices implements UserServiceInterface {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public UserDTO findOrSaveUser(UserModel user) {
        Optional<UserModel> existingUser = userRepository.findByNameAndPassword(user.getName(), user.getPassword());
        if (existingUser.isPresent()) {
            return new UserDTO(existingUser.get().getId(), existingUser.get().getPlayedGames(),
                    existingUser.get().getWonGames());
        } else {
            UserModel savedUser = userRepository.save(user);
            return new UserDTO(savedUser.getId(), 0, 0);
        }
    }

    public void deleteUser(Long userId) {
        Optional<UserModel> user = userRepository.findById(userId);
        if (user.isPresent()) {
            gameRepository.deleteByUserId(userId);
            userRepository.deleteById(userId);
        }
    }
}
