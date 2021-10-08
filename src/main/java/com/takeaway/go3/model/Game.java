package com.takeaway.go3.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class Game {
    private int initialValue;
    private String startedBy;

    private int currentNumber;
    private String uriToRespond;

    private String gameId = UUID.randomUUID().toString();
    private Date startedAt = new Date();
    private List<Move> moves = new LinkedList<>();


}
