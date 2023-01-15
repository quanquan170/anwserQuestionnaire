package com.aim.questionnaire.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QuestionnaireVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer type;
    private String name;
    private String description;
    private String title;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Integer examination;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long projectId;
    private String projectName;

}
