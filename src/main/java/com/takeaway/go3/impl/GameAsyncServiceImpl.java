package com.takeaway.go3.impl;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Data
@Slf4j
@Service
@Qualifier("async")
@EqualsAndHashCode(callSuper = false)
@ConditionalOnProperty("game.settings.async.topic")
public class GameAsyncServiceImpl extends AbstractGameServiceImpl {

    private final JmsTemplate jmsTemplate;

    @Value("${game.settings.async.topic}")
    private String topic;

    @Override
    protected String initialGame(Game game, GameStart gameRequest) {
        jmsTemplate.convertAndSend(getOpponentAddress(gameRequest), game);
        return "The game has been started!";
    }

    private String getOpponentAddress(GameStart gameRequest) {
        return StringUtils.isEmpty(gameRequest.getOtherPlayerAddress())
                ? defaultOtherPlayerAddress : gameRequest.getOtherPlayerAddress();
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

    protected String sendPlayRequest(Game game) {
        final String playAddress = game.getRespondAddress();

        game.setRespondAddress(getRespondAddress());

        log.info("Request sent {} ", game.toString());

        jmsTemplate.convertAndSend(playAddress, game);

        return "Request sent " + game.toString();
    }

}

