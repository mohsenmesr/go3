package com.takeaway.go3.impl;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import com.takeaway.go3.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("asyncP1")
class AbstractGameServiceImplTest {

    @Autowired()
    @Qualifier("sync")
    GameService syncGameService;

    @Autowired
    @Qualifier("async")
    GameService asyncGameService;

    @MockBean
    RestTemplate restTemplate;

    @MockBean
    JmsTemplate jmsTemplate;

    @BeforeEach
    void setUp() {
        Mockito.when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenReturn(true);

        Mockito.doNothing()
                .when(jmsTemplate).convertAndSend(Mockito.anyString(), Mockito.any(Game.class));
    }

    @Test
    void startGameSyncTest() {
        GameStart gameStart = GameStart.of();
        assertDoesNotThrow(() -> syncGameService.startGame(gameStart));
    }
}