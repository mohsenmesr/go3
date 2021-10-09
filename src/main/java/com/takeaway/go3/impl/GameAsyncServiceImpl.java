package com.takeaway.go3.impl;

import com.takeaway.go3.model.Game;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service("async")
@EqualsAndHashCode(callSuper = false)
@ConditionalOnProperty("game.settings.async.topic")
public class GameAsyncServiceImpl extends AbstractGameServiceImpl {

    private final JmsTemplate jmsTemplate;

    @Value("${game.settings.async.topic}")
    private String topic;

    @Override
    protected String startGameInternally(Game game) {
        jmsTemplate.convertAndSend(opponentAddress, game);
        return "The game has been started!";
    }

    @Override
    protected String getRespondAddress() {
        return topic;
    }

    @Async
    @JmsListener(destination = "${game.settings.async.topic}")
    public String play(Game game) {
        return super.play(game);
    }

    protected String playInternally(Game game) {
        final String playAddress = game.getRespondAddress();

        game.setRespondAddress(getRespondAddress());

        log.info("Request sent {} ", game);

        jmsTemplate.convertAndSend(playAddress, game);

        return "Request sent " + game;
    }

}

