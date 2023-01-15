package com.aim.questionnaire.domain.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageSearchQuestionnaireDto {

    private Integer type;

    private String title;

    private String name;

    private Integer status;

    private Integer examination;

    @NotNull(message = "分页数不能为空")
    private Integer pageNum = 1;

    @NotNull(message = "分页大小不能为空")
    private Integer pageSize = 10;
}
