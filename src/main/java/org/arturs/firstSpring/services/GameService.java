package org.arturs.firstSpring.services;

import org.arturs.firstSpring.repositories.GameRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

}
