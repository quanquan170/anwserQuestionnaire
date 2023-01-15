package com.aim.questionnaire.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("questionnaire_info")
public class QuestionnaireInfoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "问卷类型(1-测试、2-生产、3-本地 ...这种格式 自己定义)")
    private Integer type;

    @ApiModelProperty(value = "问卷名称")
    private String name;

    @ApiModelProperty(value = "问卷描述")
    private String description;

    @ApiModelProperty(value = "问卷标题")
    private String title;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "问卷状态(1-启用、2-停用)")
    private Integer status;

    @ApiModelProperty(value = "是否考试中(1-是、2-否)")
    private Integer examination;

    @ApiModelProperty(value = "发送链接")
    @TableField(exist = false)
    private Integer link;

}
