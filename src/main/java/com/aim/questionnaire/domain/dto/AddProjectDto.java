package com.aim.questionnaire.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class AddProjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名称")
    @NotNull(message = "项目名称不能为空")
    private String projectName;

    @ApiModelProperty(value = "项目说明")
    private String projectContent;

    @ApiModelProperty(value = "问卷id")
    private Long questionId;

}
