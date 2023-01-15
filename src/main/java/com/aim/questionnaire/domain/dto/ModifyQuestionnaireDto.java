package com.aim.questionnaire.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class ModifyQuestionnaireDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问卷id")
    private Long id;

    @ApiModelProperty(value = "项目结束时间")
    private Date endTime;

}
