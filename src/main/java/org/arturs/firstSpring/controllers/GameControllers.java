package org.arturs.firstSpring.controllers;

import org.arturs.firstSpring.models.GameDTO;
import org.arturs.firstSpring.models.GameModel;
import org.arturs.firstSpring.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class GameControllers {

    private final GameService gameService;

    @PostMapping("/game/start/{userId}")
    public ResponseEntity<Long> createGame(@PathVariable Long userId) {
        Long gameId = gameService.createGame(userId);
        return new ResponseEntity<>(gameId, HttpStatus.CREATED);
    }

    @PostMapping("/game/check")
    public ResponseEntity<GameDTO> compareNumbers(@Valid @RequestBody GameModel gameModel) {
        log.debug("Received GameModel: {}", gameModel);
        GameDTO result = gameService.compareNumbers(gameModel);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
