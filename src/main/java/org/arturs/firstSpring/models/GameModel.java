package org.arturs.firstSpring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game")
@AllArgsConstructor
@NoArgsConstructor
public class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserModel user;
    private int guessNumber1;
    private int guessNumber2;
    private int guessNumber3;
    private int guessNumber4;
}
