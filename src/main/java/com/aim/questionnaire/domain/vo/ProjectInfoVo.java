package com.aim.questionnaire.domain.vo;

import com.aim.questionnaire.domain.entity.ProjectInfoEntity;
import com.aim.questionnaire.domain.entity.QuestionnaireInfoEntity;
import lombok.Data;

import java.util.List;

@Data
public class ProjectInfoVo {
    private ProjectInfoEntity projectInfoEntity;

    private List<QuestionnaireInfoEntity> poList;
}
