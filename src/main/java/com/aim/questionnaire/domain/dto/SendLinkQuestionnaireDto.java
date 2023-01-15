package com.aim.questionnaire.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SendLinkQuestionnaireDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问卷id")
    private Long questionnaireId;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

}
