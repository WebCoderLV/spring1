package org.arturs.firstSpring.interfaces;

import org.arturs.firstSpring.models.GameDTO;
import org.arturs.firstSpring.models.GameModel;

public interface GameServiceInterface {

    Long createGame(Long userId);

    GameDTO compareNumbers(GameModel gameModel);

}
