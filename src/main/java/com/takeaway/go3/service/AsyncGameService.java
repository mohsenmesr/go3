package com.takeaway.go3.service;

import com.takeaway.go3.model.Game;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface AsyncGameService extends GameService {
    void play(@Valid Game game);
}
