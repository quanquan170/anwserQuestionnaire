package com.aim.questionnaire.service;


import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.domain.dto.AddProjectDto;
import com.aim.questionnaire.domain.dto.PageSearchProjectDto;
import com.aim.questionnaire.domain.dto.UpdateProjectDto;
import com.aim.questionnaire.domain.entity.ProjectInfoEntity;

import java.util.List;

public interface ProjectInfoService {

    /**
     * 分页查询项目列表
     */
    ListPage<ProjectInfoEntity> pageSearchProjectList(PageSearchProjectDto pageSearchProjectDto);

    /**
     * 项目新增
     * @param addDto 参数
     */
    ApiResult<Object> saveProjectInfo(AddProjectDto addDto);

    /**
     * 项目修改
     * @param updateDto 参数
     */
    ApiResult<Object> updateProjectInfo(UpdateProjectDto updateDto);

    /**
     * 项目删除
     * @param id 项目id
     */
    ApiResult<Object> deleteProjectInfo(Long id);

    /**
     * 获取项目下拉框
     */
    List<ProjectInfoEntity> getProjectCombos();

    /**
     * 项目-问卷绑定
     */
    ApiResult<Object> bindingQuestionnaire(Long projectId, List<Long> quesIds);

    ApiResult<Object> getById(Long id);
}
