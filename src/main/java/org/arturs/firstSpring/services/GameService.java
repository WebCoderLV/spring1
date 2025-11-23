package org.arturs.firstSpring.services;

import java.util.List;
import java.util.Optional;

import org.arturs.firstSpring.interfaces.GameServiceInterface;
import org.arturs.firstSpring.models.GameDTO;
import org.arturs.firstSpring.models.GameModel;
import org.arturs.firstSpring.models.UserModel;
import org.arturs.firstSpring.repositories.GameRepository;
import org.arturs.firstSpring.repositories.UserRepository;
import org.arturs.firstSpring.utilities.GameUtility;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GameService implements GameServiceInterface {

    private final GameRepository gameRepository;

    private final UserRepository userRepository;

    public Long createGame(Long userId) {
        Optional<UserModel> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            user.setPlayedGames(user.getPlayedGames() + 1);
            userRepository.save(user);
            gameRepository.deleteByUserId(userId);
            List<Integer> numbersList = GameUtility.generateRandomNumber();
            GameModel game = new GameModel();
            game.setUser(userOpt.get());
            game.setGuessNumber1(numbersList.get(0));
            game.setGuessNumber2(numbersList.get(1));
            game.setGuessNumber3(numbersList.get(2));
            game.setGuessNumber4(numbersList.get(3));
            Long gameId = gameRepository.save(game).getGameId();
            return gameId;
        } else {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }

    public GameDTO compareNumbers(GameModel gameModel) {
        Optional<GameModel> gameOpt = gameRepository.findById(gameModel.getGameId());
        if (gameOpt.isPresent()) {
            List<Integer> numberFromDb = List.of(gameOpt.get().getGuessNumber1(), gameOpt.get().getGuessNumber2(),
                    gameOpt.get().getGuessNumber3(), gameOpt.get().getGuessNumber4());
            List<Integer> guessNumbers = List.of(gameModel.getGuessNumber1(), gameModel.getGuessNumber2(),
                    gameModel.getGuessNumber3(),
                    gameModel.getGuessNumber4());
            int p = 0;
            int a = 0;
            for (int i = 0; i < 4; i++) {
                // Compare guessNumbers and numberFromDb to calculate p and a
                if (guessNumbers.get(i).equals(numberFromDb.get(i))) {
                    // exact match
                    p++;
                } else if (numberFromDb.contains(guessNumbers.get(i))) {
                    for (int j = 0; j < 4; j++) {
                        if (i != j && guessNumbers.get(i).equals(numberFromDb.get(j))) {
                            a++;
                            break;
                        }
                    }
                }
            }
            if (p == 4) {
                UserModel user = gameOpt.get().getUser();
                if (user != null) {
                    user.setWonGames(user.getWonGames() + 1);
                    userRepository.save(user);
                    return new GameDTO(gameModel.getGameId(), "4", "0", true);
                } else {
                    throw new IllegalStateException("Game exists but has no associated user");
                }
            } else {
                return new GameDTO(gameModel.getGameId(), String.valueOf(p), String.valueOf(a), false);
            }
        } else {
            throw new IllegalArgumentException("Game not found with id: " + gameModel.getGameId());
        }
    }

    public GameModel giveUp(Long gameId) {
        if (gameId == null) {
            return new GameModel();
        }
        Optional<GameModel> gameOpt = gameRepository.findById(gameId);
        if (gameOpt.isPresent()) {
            return gameOpt.get();
        } else {
            return new GameModel();
        }
    }
}
