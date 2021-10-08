package com.takeaway.go3.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class Game {
    private final int initialValue;
    private final String startedBy;
    private final String gameId = UUID.randomUUID().toString();
    private final Date startedAt = new Date();

    private LinkedList<Move> moves;
    private int currentNumber;
    private String uriToRespond;

    public Game(int initialValue, String startedBy) {
        this.initialValue = initialValue;
        this.currentNumber = initialValue;
        this.startedBy = startedBy;
    }

}
