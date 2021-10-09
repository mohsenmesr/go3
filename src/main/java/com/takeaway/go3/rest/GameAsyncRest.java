package com.takeaway.go3.rest;

import com.takeaway.go3.model.GameStart;
import com.takeaway.go3.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty("game.settings.async.topic")
public class GameAsyncRest {

    private final GameService syncGameService;

    @Autowired
    public GameAsyncRest(@Qualifier("async") GameService syncGameService) {
        this.syncGameService = syncGameService;
    }

    @PostMapping(value = "/go3/api/v1/start-async")
    public ResponseEntity<String> startAsync(@RequestBody GameStart gameRequest) {
        return ResponseEntity.ok(syncGameService.startGame(gameRequest));
    }
}
