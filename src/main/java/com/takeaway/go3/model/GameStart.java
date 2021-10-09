package com.takeaway.go3.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data(staticConstructor = "of")
@ApiModel(description = "Starter model for game start")
public class GameStart {

    @ApiModelProperty(value = "Whether the initial value should generate randomly (below 10000)", required = true)
    private boolean initialRandomly;

    @ApiModelProperty(value = "Initial value for starting the game (NULL will be generate randomly)")
    private Integer initialValue;

}
