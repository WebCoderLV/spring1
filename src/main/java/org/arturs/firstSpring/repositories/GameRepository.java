package org.arturs.firstSpring.repositories;

import org.arturs.firstSpring.models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface GameRepository extends JpaRepository<GameModel, Long> {
    @Transactional
    void deleteByUserId(Long userId);
}
