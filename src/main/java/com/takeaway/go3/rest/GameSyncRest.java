package com.takeaway.go3.rest;

import com.takeaway.go3.model.Game;
import com.takeaway.go3.model.GameStart;
import com.takeaway.go3.service.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty("game.settings.sync.playEndpoint")
public class GameSyncRest {

    private final GameService gameService;

    @Autowired
    public GameSyncRest(@Qualifier("sync") GameService gameService) {
        this.gameService = gameService;
    }

    @ApiOperation(value = "start-game-in-sync-mode"
            , notes = "Start a game in sync mode and other user should be up to handle requests")
    @PostMapping(value = "/go3/api/v1/start-sync")
    public ResponseEntity<String> startSync(
            @ApiParam(required = true, value = "Details of game request")
            @RequestBody GameStart gameRequest) {
        return ResponseEntity.ok(gameService.startGame(gameRequest));
    }

    @ApiOperation(value = "play-game-in-sync-mode"
            , notes = "Play initialed game B2B")
    @PostMapping(value = "${game.settings.sync.playEndpoint}")
    public ResponseEntity<String> playSync(@RequestBody
                                           @ApiParam(required = true, value = "The game to play")
                                                   Game game) {
        return ResponseEntity.ok(gameService.play(game));
    }
}
