package com.takeaway.go3.impl;

import com.takeaway.go3.exceptions.GameException;
import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import com.takeaway.go3.model.Move;
import com.takeaway.go3.service.GameService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import java.security.SecureRandom;

@Slf4j
@Data
public abstract class AbstractGameServiceImpl implements GameService {

    @Value("${spring.application.name}")
    protected String playerName;

    @Value("${game.settings.maxRandom}")
    protected int maxRandomRange;

    @Value("${game.settings.opponentAddress}")
    protected String opponentAddress;

    @Override
    public String startGame(GameStart gameRequest) {
        log.info("Processing game start request! {}", gameRequest);
        final Game game = getStarterGameModel(gameRequest);
        log.info("The game has been initialized! {}", game);
        return startGameInternally(game);
    }

    protected abstract String startGameInternally(Game game);

    protected Game getStarterGameModel(GameStart gameRequest) {
        final int initialNumber = getInitialNumber(gameRequest.isInitialRandomly(), gameRequest.getInitialValue());
        final String playerName = this.playerName;
        return Game.of(initialNumber, playerName)
                .setCurrentNumber(initialNumber)
                .setRespondAddress(getRespondAddress());
    }

    protected int getInitialNumber(boolean doRandomly, Integer initialValue) {
        return doRandomly || ObjectUtils.isEmpty(initialValue) ?
                new SecureRandom().nextInt(maxRandomRange)
                : initialValue;
    }

    protected abstract String getRespondAddress();

    @Override
    public String play(Game game) {

        log.info("Received request {} ", game.toString());

        final Move nextMove = getNextMove(game.getCurrentNumber());

        game.setCurrentNumber(nextMove.getFinalValue())
                .getMoves().add(nextMove.setMovedBy(playerName));

        if (game.getCurrentNumber() == 1) {
            log.info("You are the winner!!");
            return "You are the winner!!";
        }

        return playInternally(game);

    }

    protected abstract String playInternally(Game game);

    protected Move getNextMove(int currentNumber) {

        if (currentNumber <= 0) {
            log.error("Error, Received number is below-zero!");
            throw new GameException("Error, Received number is below-zero!");
        }

        if (currentNumber % 3 == 0) {
            log.info("Value added: 0");
            return Move.of()
                    .setDetermination(0)
                    .setStartValue(currentNumber)
                    .setFinalValue(currentNumber / 3);
        }

        if ((currentNumber + 1) % 3 == 0) {
            log.info("Value added: 1");
            return Move.of()
                    .setDetermination(1)
                    .setStartValue(currentNumber)
                    .setFinalValue((currentNumber + 1) / 3);
        }

        log.info("Value added: -1");
        return Move.of()
                .setDetermination(-1)
                .setStartValue(currentNumber)
                .setFinalValue((currentNumber - 1) / 3);
    }
}

