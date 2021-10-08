package com.takeaway.go3.service;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStartRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface GameService {
    String startGame(@Valid GameStartRequest gameRequest);

    String play(@Valid Game game);
}
