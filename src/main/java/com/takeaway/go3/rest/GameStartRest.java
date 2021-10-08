package com.takeaway.go3.rest;

import com.takeaway.go3.service.GameService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
public class GameStartRest {

    private final GameService gameService;

    @GetMapping(value = "/go3/api/v1/start-sync/{initialValue}")
    public ResponseEntity<String> startSync(@PathVariable int initialValue) {
        return ResponseEntity.ok(gameService.startGame(initialValue));
    }

    @GetMapping(value = "/go3/api/v1/start-async/{initialValue}")
    public ResponseEntity<String> startAsync(@PathVariable int initialValue) {
        return ResponseEntity.ok(gameService.startGame(initialValue));
    }
}
