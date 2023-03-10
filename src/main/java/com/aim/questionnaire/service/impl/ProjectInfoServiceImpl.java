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
         * ??????????????????????????????
         */
        ProjectInfoEntity projectInfoEntity = getOne(Wrappers.<ProjectInfoEntity>lambdaQuery().eq(ProjectInfoEntity::getProjectName,addDto.getProjectName()).last(" limit 1"));
        if(Objects.nonNull(projectInfoEntity)){
            return ApiResult.fail(400,"?????????????????????");
        }
        po.setProjectName(addDto.getProjectName());
        po.setProjectContent(addDto.getProjectContent());
        /*
         * ????????????????????????
         */
        po.setCreateBy(Constans.DEFAULT_USER);
        po.setCreateTime(new Date());
        baseMapper.insert(po);

        /*
         * ????????????????????????????????????
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
            return ApiResult.fail(400, "????????????????????????????????????");
        }
        /*
         * ?????????????????????????????????????????????
         */
        if (checkExamination(updateDto.getId())) {
            return ApiResult.fail(400, "??????????????????????????????????????????????????????????????????");
        }
        /*
         * ??????????????????????????????
         */
        ProjectInfoEntity projectInfoEntity = getOne(Wrappers.<ProjectInfoEntity>lambdaQuery().eq(ProjectInfoEntity::getProjectName,
                updateDto.getProjectName()).last(" limit 1"));
        if(Objects.nonNull(projectInfoEntity) && !projectInfoEntity.getId().equals(updateDto.getId())){
            return ApiResult.fail(400,"?????????????????????");
        }
        po.setProjectContent(updateDto.getProjectContent());
        po.setProjectName(updateDto.getProjectName());
        /*
         * ????????????????????????
         */
        po.setUpdateBy(Constans.DEFAULT_USER);
        po.setUpdateTime(new Date());
        baseMapper.updateById(po);


        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> deleteProjectInfo(Long id) {
        /*
         * ?????????????????????????????????????????????
         */
        if (checkExamination(id)) {
            return ApiResult.fail(400, "??????????????????????????????????????????????????????????????????");
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
            return ApiResult.fail(400, "??????????????????????????????????????????");
        }
        /*
         * ????????????????????????????????????
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
     * ?????????????????????????????????????????????
     */
    private boolean checkExamination(Long projectId) {
        List<QuestionnaireInfoEntity> poList = baseMapper.getQuestionnaireEntitiesByProjectId(projectId);
        return CollectionUtils.isNotEmpty(poList);
    }
}
