package com.aim.questionnaire.mapper;

import com.aim.questionnaire.domain.entity.ProjectInfoEntity;
import com.aim.questionnaire.domain.entity.QuestionnaireInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectInfoMapper extends BaseMapper<ProjectInfoEntity> {

    /**
     * 通过项目id，查询所有正在进行中的问卷列表
     * @param projectId 项目id
     */
    List<QuestionnaireInfoEntity> getQuestionnaireEntitiesByProjectId(@Param("projectId") Long projectId);

    /**
     * 获取所有项目信息
     */
    List<ProjectInfoEntity> getAllProject();
}
