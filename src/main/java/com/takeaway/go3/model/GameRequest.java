package com.takeaway.go3.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class GameRequest {
    private Integer initialValue;
    private Boolean isAsync;
}
