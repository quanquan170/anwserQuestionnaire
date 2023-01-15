package com.aim.questionnaire.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class UpdateQuestionnaireDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "问卷类型(1-测试、2-生产、3-本地 ...这种格式 自己定义)")
    private Integer type;

    @ApiModelProperty(value = "问卷名称")
    @NotNull(message = "问卷名称不能为空")
    private String name;

    @ApiModelProperty(value = "问卷描述")
    private String description;

    @ApiModelProperty(value = "问卷标题")
    private String title;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
