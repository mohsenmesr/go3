package com.takeaway.go3.service;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface GameService {

    String startGame(@Valid GameStart gameRequest);

    String play(@Valid Game game);
}
