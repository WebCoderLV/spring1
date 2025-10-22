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
            List<Integer> numbersList = GameUtility.generateRandomNumber();
            GameModel game = new GameModel();
            game.setUser(userOpt.get());
            game.setNumber1(numbersList.get(0));
            game.setNumber2(numbersList.get(1));
            game.setNumber3(numbersList.get(2));
            game.setNumber4(numbersList.get(3));
            game.setWin(false);
            Long gameId = gameRepository.save(game).getId();
            return gameId;
        } else {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }

    public GameDTO compareNumbers(GameModel gameModel) {
        Optional<GameModel> gameOpt = gameRepository.findById(gameModel.getId());
        if (gameOpt.isPresent()) {
            List<Integer> numberFromDb = List.of(gameOpt.get().getNumber1(), gameOpt.get().getNumber2(),
                    gameOpt.get().getNumber3(), gameOpt.get().getNumber4());
            List<Integer> guessNumbers = List.of(gameModel.getNumber1(), gameModel.getNumber2(), gameModel.getNumber3(),
                    gameModel.getNumber4());
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
                gameModel.setWin(true);
                gameRepository.save(gameModel);
                return new GameDTO(gameModel.getId(), "4", "0", true);
            } else {
                return new GameDTO(gameModel.getId(), String.valueOf(p), String.valueOf(a), false);
            }

        } else {
            throw new IllegalArgumentException("Game not found with id: " + gameModel.getId());
        }
    }
}
