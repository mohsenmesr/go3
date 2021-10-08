package com.takeaway.go3.rest;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.service.GameService;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@ConditionalOnProperty("game.settings.sync.playEndpoint")
public class GamePlaySyncRest {

    private final GameService gameService;

    @GetMapping(value = "${game.settings.sync.healthEndpoint}")
    public ResponseEntity<Void> healthCheckSync() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "${game.settings.sync.playEndpoint}")
    public ResponseEntity<String> playSync(@RequestBody Game game) {
        return ResponseEntity.ok(gameService.play(game));
    }
}
