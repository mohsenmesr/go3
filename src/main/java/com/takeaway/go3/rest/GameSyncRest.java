package com.takeaway.go3.rest;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import com.takeaway.go3.service.SyncGameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@ConditionalOnProperty("game.settings.sync.playEndpoint")
public class GameSyncRest {

    private final SyncGameService syncGameService;

    @ApiOperation(value = "start-game-in-sync-mode"
            , notes = "Start a game in sync mode and other user should be up to handle requests")
    @PostMapping(value = "/go3/api/v1/start-sync")
    public ResponseEntity<String> startSync(
            @ApiParam(required = true, value = "Details of game request")
            @RequestBody GameStart gameRequest) {
        return ResponseEntity.ok(syncGameService.startGame(gameRequest));
    }

    @GetMapping(value = "${game.settings.sync.healthEndpoint}")
    public ResponseEntity<Void> healthCheckSync() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "${game.settings.sync.playEndpoint}")
    public ResponseEntity<String> playSync(@RequestBody Game game) {
        return ResponseEntity.ok(syncGameService.play(game));
    }
}
