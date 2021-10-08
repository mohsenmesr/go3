package com.takeaway.go3.service;

import com.takeaway.go3.model.Game;

public interface GameService {
    String startGame(Game game);
    String play(Game game);
}
