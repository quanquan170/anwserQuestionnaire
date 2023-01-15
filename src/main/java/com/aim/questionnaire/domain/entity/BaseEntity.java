package com.aim.questionnaire.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}
