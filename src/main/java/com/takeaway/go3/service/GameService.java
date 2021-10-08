package com.takeaway.go3.service;

import com.sun.istack.internal.NotNull;
import com.takeaway.go3.model.Game;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface GameService {
    String startGame(int initialValue);
    String play(@Valid Game game);
}
