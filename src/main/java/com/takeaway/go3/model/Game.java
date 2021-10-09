package com.takeaway.go3.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Accessors(chain = true)
@Data(staticConstructor = "of")
@ApiModel(description = "The model that will passed in every game steps")
public class Game {

    @ApiModelProperty(value = "The track of game moves")
    private final List<Move> moves = new LinkedList<>();
    @ApiModelProperty(required = true, value = "The initial value when game started!")
    private final int initialValue;
    @ApiModelProperty(value = "The player name who started the game! (just for track of starter because B2B is the main player)")
    private final String startedBy;
    @ApiModelProperty(value = "The unique game id to track it")
    private final String gameId = UUID.randomUUID().toString();
    @ApiModelProperty(value = "When the game started!")
    private final Date startedAt = new Date();
    @ApiModelProperty(value = "Current request value!")
    private int currentNumber;
    @ApiModelProperty(required = true, value = "Which address should respond send! (it will handle systematically)")
    private String respondAddress;

    public Game(int initialValue, String startedBy) {
        this.initialValue = initialValue;
        this.startedBy = startedBy;
    }

    public Game() {
        initialValue = 0;
        startedBy = "";
    }

}
