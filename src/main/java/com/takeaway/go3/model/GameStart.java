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

    @ApiModelProperty(value = "Better Better more than 3 staticConstructor start")
    private Integer initialValue;

    @ApiModelProperty(value = "Address of player to respond " +
            "(http://IP:PORT for syn/TOPIC_NAME for async)," +
            " if not provided system defaults will be used")
    private String otherPlayerAddress;

    @ApiModelProperty(value = "Custom name of game starter," +
            " if not provided App_Name will be use!")
    private String starterPlayerName;
}
