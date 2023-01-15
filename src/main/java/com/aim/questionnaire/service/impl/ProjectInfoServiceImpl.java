package com.aim.questionnaire.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.constant.Constans;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.common.utils.StringUtils;
import com.aim.questionnaire.domain.dto.AddProjectDto;
import com.aim.questionnaire.domain.dto.PageSearchProjectDto;
import com.aim.questionnaire.domain.dto.UpdateProjectDto;
import com.aim.questionnaire.domain.entity.ProjectInfoEntity;
import com.aim.questionnaire.domain.entity.ProjectQuestionEntity;
import com.aim.questionnaire.domain.entity.QuestionnaireInfoEntity;
import com.aim.questionnaire.domain.vo.ProjectInfoVo;
import com.aim.questionnaire.mapper.ProjectInfoMapper;
import com.aim.questionnaire.mapper.ProjectQuestionMapper;
import com.aim.questionnaire.service.ProjectInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfoEntity> implements ProjectInfoService {

    @Resource
    private ProjectQuestionMapper projectQuestionMapper;

    @Override
    public ListPage<ProjectInfoEntity> pageSearchProjectList(PageSearchProjectDto pageSearchProjectDto) {
        List<ProjectInfoEntity> rtnList;
        if (!StringUtils.isNull(pageSearchProjectDto.getName())) {
            LambdaQueryWrapper<ProjectInfoEntity> queryWrap = Wrappers.lambdaQuery();
            queryWrap.like(ProjectInfoEntity::getProjectName, pageSearchProjectDto.getName());
            rtnList = baseMapper.selectList(queryWrap);
        } else {
            rtnList = baseMapper.getAllProject();
        }
        return new ListPage<>(rtnList, pageSearchProjectDto.getPageNum(), pageSearchProjectDto.getPageSize());
    }

    @Override
    public ApiResult<Object> saveProjectInfo(AddProjectDto addDto) {
        ProjectInfoEntity po = new ProjectInfoEntity();
        /*
         * 校验项目名称是否重复
         */
        ProjectInfoEntity projectInfoEntity = getOne(Wrappers.<ProjectInfoEntity>lambdaQuery().eq(ProjectInfoEntity::getProjectName,addDto.getProjectName()).last(" limit 1"));
        if(Objects.nonNull(projectInfoEntity)){
            return ApiResult.fail(400,"项目名称已存在");
        }
        po.setProjectName(addDto.getProjectName());
        po.setProjectContent(addDto.getProjectContent());
        /*
         * 用户相关默认赋值
         */
        po.setCreateBy(Constans.DEFAULT_USER);
        po.setCreateTime(new Date());
        baseMapper.insert(po);

        /*
         * 维护项目与问卷的关联关系
         */
//        List<Long> questionnaires = addDto.getQuestionnaireIds();
//        for (Long questionnaire : questionnaires) {
//            ProjectQuestionEntity proQue = new ProjectQuestionEntity();
//            proQue.setProjectId(po.getId());
//            proQue.setQuestionnaireId(questionnaire);
//            projectQuestionMapper.insert(proQue);
//        }
        ProjectQuestionEntity proQue = new ProjectQuestionEntity();
        proQue.setProjectId(po.getId());
        proQue.setQuestionnaireId(addDto.getQuestionId());
        proQue.setLink(0);
        projectQuestionMapper.insert(proQue);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> updateProjectInfo(UpdateProjectDto updateDto) {
        ProjectInfoEntity po = baseMapper.selectById(updateDto.getId());
        if (ObjectUtil.isNull(po)) {
            return ApiResult.fail(400, "修改失败，项目信息不存在");
        }
        /*
         * 检查项目下是否存在进行中的问卷
         */
        if (checkExamination(updateDto.getId())) {
            return ApiResult.fail(400, "该项目下存在状态为【进行中】的问卷，无法修改");
        }
        /*
         * 检查项目名称是否重复
         */
        ProjectInfoEntity projectInfoEntity = getOne(Wrappers.<ProjectInfoEntity>lambdaQuery().eq(ProjectInfoEntity::getProjectName,
                updateDto.getProjectName()).last(" limit 1"));
        if(Objects.nonNull(projectInfoEntity) && !projectInfoEntity.getId().equals(updateDto.getId())){
            return ApiResult.fail(400,"项目名称已存在");
        }
        po.setProjectContent(updateDto.getProjectContent());
        po.setProjectName(updateDto.getProjectName());
        /*
         * 用户部分默认赋值
         */
        po.setUpdateBy(Constans.DEFAULT_USER);
        po.setUpdateTime(new Date());
        baseMapper.updateById(po);


        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> deleteProjectInfo(Long id) {
        /*
         * 检查项目下是否存在进行中的问卷
         */
        if (checkExamination(id)) {
            return ApiResult.fail(400, "该项目下存在状态为【进行中】的问卷，无法删除");
        }
        baseMapper.deleteById(id);
        LambdaQueryWrapper<ProjectQuestionEntity> queryWrap = Wrappers.lambdaQuery();
        queryWrap.eq(ProjectQuestionEntity::getProjectId, id);
        projectQuestionMapper.delete(queryWrap);
        return ApiResult.success();
    }

    @Override
    public List<ProjectInfoEntity> getProjectCombos() {
        return baseMapper.getAllProject();
    }

    @Override
    public ApiResult<Object> bindingQuestionnaire(Long projectId, List<Long> quesIds) {
        ProjectInfoEntity project = baseMapper.selectById(projectId);
        if (ObjectUtil.isNull(project)) {
            return ApiResult.fail(400, "关联问卷失败，项目信息不存在");
        }
        /*
         * 维护项目与问卷的关联关系
         */
        if (CollectionUtil.isNotEmpty(quesIds)) {
            LambdaQueryWrapper<ProjectQuestionEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(ProjectQuestionEntity::getProjectId, projectId);
            projectQuestionMapper.delete(queryWrapper);
        }
        for (Long questionnaire : quesIds) {
            ProjectQuestionEntity proQue = new ProjectQuestionEntity();
            proQue.setProjectId(projectId);
            proQue.setQuestionnaireId(questionnaire);
            projectQuestionMapper.insert(proQue);
        }
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> getById(Long id) {
        ProjectInfoEntity projectInfoEntity = baseMapper.selectById(id);
        List<QuestionnaireInfoEntity> poList = baseMapper.getQuestionnaireEntitiesByProjectId(id);
        ProjectInfoVo projectInfoVo = new ProjectInfoVo();
        projectInfoVo.setPoList(poList);
        projectInfoVo.setProjectInfoEntity(projectInfoEntity);
        return ApiResult.success(projectInfoVo);
    }

    /**
     * 校验项目下是否存在进行中的文件
     */
    private boolean checkExamination(Long projectId) {
        List<QuestionnaireInfoEntity> poList = baseMapper.getQuestionnaireEntitiesByProjectId(projectId);
        return CollectionUtils.isNotEmpty(poList);
    }
}
