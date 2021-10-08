package com.takeaway.go3.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class Move {

    private Date occurredAt = new Date();
    private String gameId;
    private String playerName;
    private int startValue;
    private int determination;
    private int finalValue;

}
