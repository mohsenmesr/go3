package com.takeaway.go3.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data(staticConstructor = "of")
@Accessors(chain = true)
@ApiModel(description = "Will be use for get the track of game moves")
public class Move {
    @ApiModelProperty("When move occurred")
    private Date occurredAt = new Date();
    @ApiModelProperty("Start value before move")
    private int startValue;
    @ApiModelProperty("Move value")
    private int determination;
    @ApiModelProperty("final value")
    private int finalValue;

}
