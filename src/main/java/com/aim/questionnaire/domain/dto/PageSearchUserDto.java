package com.aim.questionnaire.domain.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageSearchUserDto {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "是否启用（1启用，0不启用）")
    private Integer status;

    @NotNull(message = "分页数不能为空")
    private Integer pageNum = 1;

    @NotNull(message = "分页大小不能为空")
    private Integer pageSize = 10;
}
