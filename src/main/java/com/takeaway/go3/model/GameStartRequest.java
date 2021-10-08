package com.takeaway.go3.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
@Accessors(chain = true)
@ApiModel()
public class GameStartRequest {
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean isAsync;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean initialRandomly;
    private Integer initialValue;
    private String otherPlayerAddress;
    private String starterPlayerName;
}
