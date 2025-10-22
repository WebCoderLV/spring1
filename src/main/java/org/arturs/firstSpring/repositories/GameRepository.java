package org.arturs.firstSpring.repositories;

import org.arturs.firstSpring.models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameModel, Long> {

}
