package com.aim.questionnaire.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("questionnaire_info")
public class QuestionsInfoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "问卷id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionnaireId;

    @ApiModelProperty(value = "试题编号")
    private Integer num;

    @ApiModelProperty(value = "题目类型(1-单选、2-多选、3-填空)")
    private Integer type;

    @ApiModelProperty(value = "是否必答(1-必答、2-选答)")
    private Integer required;

    @ApiModelProperty(value = "题目名称")
    private String name;

    @ApiModelProperty(value = "选项-以@分割")
    private String options;

}
