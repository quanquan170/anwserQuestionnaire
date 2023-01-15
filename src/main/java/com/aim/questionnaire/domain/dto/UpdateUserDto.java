package com.aim.questionnaire.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class UpdateUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "用户名不能为空")
    private String password;

    @ApiModelProperty(value = "是否启用（1启用，0不启用）")
    private Integer status;

}
