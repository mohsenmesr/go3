package com.takeaway.go3.impl;

import com.takeaway.go3.exceptions.GameException;
import com.takeaway.go3.model.Game;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@Slf4j
@Service("sync")
@EqualsAndHashCode(callSuper = false)
@ConditionalOnProperty("game.settings.sync.playEndpoint")
public class GameSyncServiceImpl extends AbstractGameServiceImpl {

    private final RestTemplate restTemplate;

    @Value("${game.settings.sync.playEndpoint}")
    private String playEndpoint;

    @Override
    protected String startGameInternally(Game game) {
        log.info("Sending Request {}", game);
        if (postRestCall(opponentAddress + playEndpoint, game)) {
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

    protected String playInternally(Game game) {

        final String playAddress = game.getRespondAddress() + playEndpoint;

        game.setRespondAddress(getRespondAddress());

        log.info("Sending Request {} ", game);

        postRestCall(playAddress, game);

        return "Request sent " + game;
    }

    protected boolean postRestCall(String url, Game game) {
        try {
            restTemplate.postForObject(url
                    , game
                    , String.class);
            return true;
        } catch (Exception e) {
            log.error("The other player is not up!", e);
            throw new GameException("Other player is not accessible! on url = " + url);
        }
    }
}

