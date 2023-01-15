package com.aim.questionnaire.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageSearchQuestionDto {
    @NotNull(message = "分页数不能为空")
    private Integer id = 1;
}
