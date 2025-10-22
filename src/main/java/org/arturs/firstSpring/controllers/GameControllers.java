package org.arturs.firstSpring.controllers;

import org.arturs.firstSpring.models.GameDTO;
import org.arturs.firstSpring.models.GameModel;
import org.arturs.firstSpring.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class GameControllers {

    private final GameService gameService;

    @PostMapping("/game")
    public ResponseEntity<Long> startGame(@Valid @RequestParam Long userId) {
        Long gameId = gameService.createGame(userId);
        return new ResponseEntity<>(gameId, HttpStatus.CREATED);
    }

    @PostMapping("/game/compare")
    public ResponseEntity<GameDTO> compareNumbers(@Valid @RequestBody GameModel gameModel) {
        GameDTO result = gameService.compareNumbers(gameModel);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
