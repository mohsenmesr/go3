package com.takeaway.go3.rest;

import com.takeaway.go3.model.GameStartRequest;
import com.takeaway.go3.service.GameService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
public class GameStartRest {

    private final GameService gameService;

    @ApiOperation(value = "start-game-in-sync-mode"
            , notes = "Start a game in sync mode and other user should be up to handle requests")
    @PostMapping(value = "/go3/api/v1/start-sync")
    public ResponseEntity<String> startSync(
            @ApiParam(required = true, value = "Details of game request")
            @RequestBody GameStartRequest gameRequest) {
        return ResponseEntity.ok(gameService.startGame(gameRequest));
    }

    @PostMapping(value = "/go3/api/v1/start-async")
    public ResponseEntity<String> startAsync(@RequestBody GameStartRequest gameRequest) {
        return ResponseEntity.ok(gameService.startGame(gameRequest));
    }
}
