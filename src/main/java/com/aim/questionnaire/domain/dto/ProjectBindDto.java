package com.aim.questionnaire.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ProjectBindDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "选中的问卷组")
    private List<Long> quesIds;

}
