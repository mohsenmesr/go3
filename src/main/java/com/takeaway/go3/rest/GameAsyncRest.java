package com.takeaway.go3.rest;

import com.takeaway.go3.model.GameStart;
import com.takeaway.go3.service.GameService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@ConditionalOnProperty("game.settings.async.topic")
public class GameAsyncRest {

    @Qualifier("async")
    private final GameService syncGameService;

    @PostMapping(value = "/go3/api/v1/start-async")
    public ResponseEntity<String> startAsync(@RequestBody GameStart gameRequest) {
        return ResponseEntity.ok(syncGameService.startGame(gameRequest));
    }
}
