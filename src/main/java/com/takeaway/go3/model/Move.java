package com.takeaway.go3.model;

import lombok.Data;

import java.util.Date;

@Data
public class Move {
    private final Date occurredAt = new Date();
    private final String gameId;
    private final String playerName;
    private final int startValue;
    private final int determination;
    private final int finalValue;
}
