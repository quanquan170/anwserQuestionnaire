package com.aim.questionnaire.domain.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageSearchProjectDto {

    @ApiModelProperty(value = "项目名称")
    private String name;

    @NotNull(message = "分页数不能为空")
    private Integer pageNum = 1;

    @NotNull(message = "分页大小不能为空")
    private Integer pageSize = 10;
}
