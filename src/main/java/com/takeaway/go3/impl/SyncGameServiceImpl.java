package com.takeaway.go3.impl;

import com.takeaway.go3.GameException;
import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import com.takeaway.go3.model.Move;
import com.takeaway.go3.service.SyncGameService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;

@Slf4j
@Data
@Service
public class SyncGameServiceImpl implements SyncGameService {

    private final RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String defaultPlayerName;

    @Value("${game.settings.maxRandom}")
    private int maxRandomRange;

    @Value("${game.settings.sync.default2ndPlayerAddress}")
    private String defaultOtherPlayerAddress;

    @Value("${game.settings.sync.healthEndpoint}")
    private String healthEndpoint;

    @Value("${game.settings.sync.playEndpoint}")
    private String playEndpoint;

    @Override
    public String startGame(GameStart gameRequest) {
        final String opponentAddress = getOpponentAddress(gameRequest);
        if (checkOtherPlayer(opponentAddress)) {
            log.info("The game has been started!");
            final Game game = getStarterGameModel(gameRequest);
            log.info("Request sent {}", game);
            restTemplate.postForObject(opponentAddress + playEndpoint
                    , game
                    , String.class);
            return "The game has been started!";
        } else {
            return "The other player is not up!";
        }
    }

    private Game getStarterGameModel(GameStart gameRequest) {
        final int initialNumber = gameRequest.isInitialRandomly() ?
                new SecureRandom().nextInt(maxRandomRange)
                : gameRequest.getInitialValue();
        final String playerName = StringUtils.isEmpty(gameRequest.getStarterPlayerName()) ?
                defaultPlayerName : gameRequest.getStarterPlayerName();
        return Game.of(initialNumber, playerName)
                .setCurrentNumber(initialNumber)
                .setRespondAddress(getUriString());
    }

    private String getUriString() {
        final UriComponents builder = ServletUriComponentsBuilder.fromCurrentContextPath().build();
        return UriComponentsBuilder.newInstance()
                .scheme(builder.getScheme())
                .host(builder.getHost())
                .port(builder.getPort())
                .encode().toUriString();
    }

    private String getOpponentAddress(GameStart gameRequest) {
        return (StringUtils.isEmpty(gameRequest.getOtherPlayerAddress()) ?
                defaultOtherPlayerAddress : gameRequest.getOtherPlayerAddress());
    }

    private boolean checkOtherPlayer(String opponentAddress) {
        try {
            restTemplate.getForObject(opponentAddress + healthEndpoint
                    , String.class);
            log.info("Second player is active!");
            return true;
        } catch (Exception ex) {
            log.error("There is no second player active!");
            return false;
        }
    }

    @Override
    public String play(Game game) {

        log.info("Received request {} ", game.toString());

        final Move nextMove = getNextMove(game.getCurrentNumber());

        game.setCurrentNumber(nextMove.getFinalValue())
                .getMoves().add(nextMove);

        if (game.getCurrentNumber() == 1) {
            log.info("You are the winner!!");
            return "You are the winner!!";
        }

        if (!checkOtherPlayer(game.getRespondAddress())) {
            log.error("second player is not active anymore! The game has been ended!!");
            return "second player is not active anymore!";
        }

        sendPlayRequest(game);

        return "Request sent " + game.toString();

    }

    private void sendPlayRequest(Game game) {
        final String playAddress = game.getRespondAddress() + playEndpoint;

        game.setRespondAddress(getUriString());

        log.info("Request sent {} ", game.toString());

        restTemplate.postForObject(playAddress
                , game
                , String.class);
    }

    private Move getNextMove(int currentNumber) {

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

