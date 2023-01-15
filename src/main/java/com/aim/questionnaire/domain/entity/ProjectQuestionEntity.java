package com.aim.questionnaire.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("project_question")
public class ProjectQuestionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;

    @ApiModelProperty(value = "问卷id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionnaireId;

    @ApiModelProperty(value = "发送")
    private Integer link;
}
