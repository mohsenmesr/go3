package com.takeaway.go3.impl;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStartRequest;
import com.takeaway.go3.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameServiceImpl implements GameService {
    @Value("spring.application.name")
    private String playerName;

    @Override
    public String startGame(GameStartRequest gameRequest) {
        return null;
    }

    @Override
    public String play(Game game) {
        return null;
    }
}
