package com.aim.questionnaire.domain.dto;

import com.aim.questionnaire.domain.entity.QuestionsInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddQuestionsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问卷id")
    private Long questionnaireId;

    @ApiModelProperty(value = "题目列表")
    private List<QuestionsInfoEntity> questions;
}
