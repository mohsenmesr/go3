package com.takeaway.go3.impl;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@Slf4j
@Service
@Qualifier("sync")
@EqualsAndHashCode(callSuper = false)
public class GameServiceImpl extends AbstractGameServiceImpl {

    private final RestTemplate restTemplate;

    @Value("${game.settings.sync.healthEndpoint}")
    private String healthEndpoint;

    @Value("${game.settings.sync.playEndpoint}")
    private String playEndpoint;


    @Override
    protected String initialGame(Game game, GameStart gameRequest) {
        final String opponentAddress = getOpponentAddress(gameRequest);
        if (checkOtherPlayer(opponentAddress)) {
            log.info("The game has been started!");
            log.info("Request sent {}", game);
            restTemplate.postForObject(opponentAddress + playEndpoint
                    , game
                    , String.class);
            return "The game has been started!";
        } else {
            return "The other player is not up!";
        }
    }

    @Override
    protected String getRespondAddress() {
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

    @Override
    protected boolean checkOtherPlayer(String opponentAddress) {
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

    protected String sendPlayRequest(Game game) {
        final String playAddress = game.getRespondAddress() + playEndpoint;

        game.setRespondAddress(getRespondAddress());

        log.info("Request sent {} ", game.toString());

        restTemplate.postForObject(playAddress
                , game
                , String.class);
        return "Request sent " + game.toString();
    }

}

