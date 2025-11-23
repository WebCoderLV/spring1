package org.arturs.firstSpring.services;

import java.util.List;

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
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        user.setPlayedGames(user.getPlayedGames() + 1);
        userRepository.save(user);

        gameRepository.deleteByUserId(userId);

        List<String> numbersList = GameUtility.generateRandomNumber();
        GameModel game = new GameModel();
        game.setUser(user);
        game.setGuessNumber1(numbersList.get(0));
        game.setGuessNumber2(numbersList.get(1));
        game.setGuessNumber3(numbersList.get(2));
        game.setGuessNumber4(numbersList.get(3));

        return gameRepository.save(game).getGameId();
    }

    public GameDTO compareNumbers(GameModel gameModel) {
        Long gameId = gameModel.getGameId();
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }

        GameModel game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with id: " + gameId));

        List<String> hashedNumbersFromDb = List.of(
                game.getGuessNumber1(),
                game.getGuessNumber2(),
                game.getGuessNumber3(),
                game.getGuessNumber4());

        List<String> hashedGuessNumbers = List.of(
                GameUtility.hashNumber(Integer.parseInt(gameModel.getGuessNumber1())),
                GameUtility.hashNumber(Integer.parseInt(gameModel.getGuessNumber2())),
                GameUtility.hashNumber(Integer.parseInt(gameModel.getGuessNumber3())),
                GameUtility.hashNumber(Integer.parseInt(gameModel.getGuessNumber4())));

        int p = 0;
        int a = 0;

        for (int i = 0; i < 4; i++) {
            if (hashedGuessNumbers.get(i).equals(hashedNumbersFromDb.get(i))) {
                p++;
            } else if (hashedNumbersFromDb.contains(hashedGuessNumbers.get(i))) {
                for (int j = 0; j < 4; j++) {
                    if (i != j && hashedGuessNumbers.get(i).equals(hashedNumbersFromDb.get(j))) {
                        a++;
                        break;
                    }
                }
            }
        }

        if (p == 4) {
            UserModel user = game.getUser();
            if (user == null) {
                throw new IllegalStateException("Game exists but has no associated user");
            }
            user.setWonGames(user.getWonGames() + 1);
            userRepository.save(user);
            return new GameDTO(gameId, "4", "0", true);
        }

        return new GameDTO(gameId, String.valueOf(p), String.valueOf(a), false);
    }

}
