package org.arturs.firstSpring.services;

import java.util.Optional;

import org.arturs.firstSpring.interfaces.UserServiceInterface;
import org.arturs.firstSpring.models.UserDTO;
import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.repositories.GameRepository;
import org.arturs.firstSpring.repositories.UserRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class UserServices implements UserServiceInterface {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public UserDTO findOrSaveUser(UserModel user) {
        try {
            Optional<UserModel> existingUser = userRepository.findByNameAndPassword(user.getName(), user.getPassword());
            if (existingUser.isPresent()) {
                return new UserDTO(existingUser.get().getId(), existingUser.get().getPlayedGames(),
                        existingUser.get().getWonGames());
            } else {
                UserModel savedUser = userRepository.save(user);
                if (savedUser != null && savedUser.getId() != null) {
                    return new UserDTO(savedUser.getId(), 0, 0);
                } else {
                    log.error("Failed to save user - savedUser is null or has no ID");
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("Error in findOrSaveUser for user: {} - {}", user.getName(), e.getMessage(), e);
            return null;
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
