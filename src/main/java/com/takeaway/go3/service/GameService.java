package com.takeaway.go3.service;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface GameService {

    /**
     * @param game The game that is running
     * @return Final state of the player move as a reporting String
     */
    String play(@Valid Game game);

    /**
     * @param gameRequest Request detail for starting a game
     * @return Final state of the started game as a String
     */
    String startGame(@Valid GameStart gameRequest);
}
