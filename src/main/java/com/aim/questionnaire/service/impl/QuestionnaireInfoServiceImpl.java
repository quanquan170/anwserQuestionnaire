package com.aim.questionnaire.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.constant.Constans;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.domain.dto.*;
import com.aim.questionnaire.domain.entity.ProjectQuestionEntity;
import com.aim.questionnaire.domain.entity.QuestionnaireInfoEntity;
import com.aim.questionnaire.domain.vo.QuestionnaireVO;
import com.aim.questionnaire.mapper.ProjectQuestionMapper;
import com.aim.questionnaire.mapper.QuestionnaireInfoMapper;
import com.aim.questionnaire.service.QuestionnaireInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class QuestionnaireInfoServiceImpl extends ServiceImpl<QuestionnaireInfoMapper, QuestionnaireInfoEntity>  implements QuestionnaireInfoService {

    @Resource
    private ProjectQuestionMapper projectQuestionMapper;


    @Override
    public List<QuestionnaireInfoEntity> getQuesCombos() {
        return baseMapper.selectList(Wrappers.lambdaQuery());
    }

    @Override
    public ApiResult<Object> add(AddQuestionnaireDto dto) {
        /*
          校验项目名称是否重复
         */
        QuestionnaireInfoEntity question = getOne(Wrappers.<QuestionnaireInfoEntity>lambdaQuery().eq(QuestionnaireInfoEntity::getName,dto.getName()).last(" limit 1"));
        if(Objects.nonNull(question)){
            return ApiResult.fail(400,"问卷名称已存在");
        }
        QuestionnaireInfoEntity po = new QuestionnaireInfoEntity();
        BeanUtil.copyProperties(dto, po);
        po.setCreateBy(Constans.DEFAULT_USER);
        po.setCreateTime(new Date());
        baseMapper.insert(po);
        //新增关联表
        ProjectQuestionEntity projectQuestionEntity = new ProjectQuestionEntity();
        projectQuestionEntity.setProjectId(dto.getProjectId());
        projectQuestionEntity.setQuestionnaireId(po.getId());
        projectQuestionEntity.setLink(0);
        projectQuestionMapper.insert(projectQuestionEntity);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> update(UpdateQuestionnaireDto dto) {
        QuestionnaireInfoEntity po = baseMapper.selectById(dto.getId());
        if (ObjectUtil.isNull(po)) {
            return ApiResult.fail(400, "修改失败，问卷信息不存在");
        }
        /*
         * 如果问卷已经被 发送过，则不能修改，如果问卷已经启动，需要停止之后再修改
         */
        if (po.getExamination() == 1) {
            return ApiResult.fail(400, "问卷已被发送，无法修改");
        }
        if (po.getStatus() == 1) {
            return ApiResult.fail(400, "问卷已启用，无法修改");
        }
        /*
         * 校验名称唯一
         */
        QuestionnaireInfoEntity question = getOne(Wrappers.<QuestionnaireInfoEntity>lambdaQuery().eq(QuestionnaireInfoEntity::getName,
                dto.getName()).last(" limit 1"));
        if(Objects.nonNull(question) && !question.getId().equals(dto.getId())){
            return ApiResult.fail(400,"问卷名称已存在");
        }
        po.setType(dto.getType());
        po.setName(dto.getName());
        po.setDescription(dto.getDescription());
        po.setTitle(dto.getTitle());
//        po.setStartTime(DateUtil.parseDateTime(dto.getStartTime()));
//        po.setEndTime(DateUtil.parseDateTime(dto.getEndTime()));
        po.setUpdateBy(Constans.DEFAULT_USER);
        po.setUpdateTime(new Date());
        baseMapper.updateById(po);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> delete(Long id) {
        QuestionnaireInfoEntity po = baseMapper.selectById(id);
        if (ObjectUtil.isNull(po)) {
            return ApiResult.fail(400, "删除失败，问卷信息不存在");
        }
        /*
         * 如果问卷已经被 发送过，则不能修改，如果问卷已经启动，需要停止之后再修改
         */
        if (po.getExamination() == 1) {
            return ApiResult.fail(400, "问卷已被发送，无法删除");
        }
        if (po.getStatus() == 1) {
            return ApiResult.fail(400, "问卷已启用，无法删除");
        }
        baseMapper.deleteById(id);
        /*
         * 删除项目问卷关联
         */
        LambdaQueryWrapper<ProjectQuestionEntity> wrap = Wrappers.lambdaQuery();
        wrap.eq(ProjectQuestionEntity::getQuestionnaireId, id);
        projectQuestionMapper.delete(wrap);
        return ApiResult.success();
    }

    @Override
    public ListPage<QuestionnaireVO> pageSearch(PageSearchQuestionnaireDto dto) {
        List<QuestionnaireVO> voList = baseMapper.pageSearchList(dto);
        return new ListPage<>(voList, dto.getPageNum(), dto.getPageSize());
    }

    @Override
    public ApiResult<Object> startAndStop(Long id) {
        QuestionnaireInfoEntity po = baseMapper.selectById(id);
        if (ObjectUtil.isNull(po)) {
            return ApiResult.fail(400, "启停失败，问卷信息不存在");
        }
        if (po.getStatus() == 1) {
            /*
             * 停用
             */
            po.setStatus(2);
        } else {
            /*
             * 启用
             */
            po.setStatus(1);
        }
        baseMapper.updateById(po);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> modifyHistoryQuestionnaireStatus(ModifyQuestionnaireDto modifyQuestionnaireDto) {
        QuestionnaireInfoEntity po = baseMapper.selectById(modifyQuestionnaireDto.getId());

        if (ObjectUtil.isNull(po)) {
            return ApiResult.fail(400, "启停失败，问卷信息不存在");
        }
        po.setEndTime(modifyQuestionnaireDto.getEndTime());

        po.setStatus(1);
        baseMapper.updateById(po);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> sendLink(SendLinkQuestionnaireDto sendLinkQuestionnaireDto) {
        ProjectQuestionEntity projectQuestionEntity = projectQuestionMapper.selectOne(Wrappers.<ProjectQuestionEntity>lambdaQuery().eq(ProjectQuestionEntity::getProjectId, sendLinkQuestionnaireDto.getProjectId()).eq(ProjectQuestionEntity::getQuestionnaireId, sendLinkQuestionnaireDto.getQuestionnaireId()));
        if (ObjectUtil.isNull(projectQuestionEntity)) {
            return ApiResult.fail(400, "发送链接失败，问卷信息不存在");
        }
        projectQuestionEntity.setLink(1);
        projectQuestionMapper.update(projectQuestionEntity,Wrappers.<ProjectQuestionEntity>lambdaQuery().eq(ProjectQuestionEntity::getProjectId, sendLinkQuestionnaireDto.getProjectId()).eq(ProjectQuestionEntity::getQuestionnaireId, sendLinkQuestionnaireDto.getQuestionnaireId()));
        return ApiResult.success();
    }
}
