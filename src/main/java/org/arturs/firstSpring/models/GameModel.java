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
    private String guessNumber1;
    private String guessNumber2;
    private String guessNumber3;
    private String guessNumber4;
}
