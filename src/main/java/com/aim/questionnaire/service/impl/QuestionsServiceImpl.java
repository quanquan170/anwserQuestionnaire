package com.aim.questionnaire.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.constant.Constans;
import com.aim.questionnaire.domain.dto.AddQuestionsDto;
import com.aim.questionnaire.domain.entity.QuestionnaireInfoEntity;
import com.aim.questionnaire.domain.entity.QuestionsInfoEntity;
import com.aim.questionnaire.mapper.QuestionnaireInfoMapper;
import com.aim.questionnaire.mapper.QuestionsInfoMapper;
import com.aim.questionnaire.service.QuestionsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class QuestionsServiceImpl extends ServiceImpl<QuestionsInfoMapper, QuestionsInfoEntity> implements QuestionsService {

    @Resource
    private QuestionnaireInfoMapper questionnaireInfoMapper;

    @Override
    public ApiResult<Object> save(AddQuestionsDto dto) {
        Date sysDate = new Date();
        Long questionnaireId = dto.getQuestionnaireId();
        List<QuestionsInfoEntity> questions = dto.getQuestions();
        if (ObjectUtil.isNull(dto) || ObjectUtil.isNull(questionnaireId) || CollectionUtil.isEmpty(questions)) {
            return ApiResult.fail(400, "保存试题失败，未选中问卷或题目不存在");
        }
        for (QuestionsInfoEntity question : questions) {
            QuestionsInfoEntity po = new QuestionsInfoEntity();
            po.setQuestionnaireId(questionnaireId);
            po.setNum(question.getNum());
            po.setType(question.getType());
            po.setRequired(question.getRequired());
            po.setName(question.getName());
            po.setOptions(question.getOptions());
            po.setCreateBy(Constans.DEFAULT_USER);
            po.setCreateTime(sysDate);
            baseMapper.insert(po);
        }
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> update(AddQuestionsDto dto) {
        Date sysDate = new Date();
        Long questionnaireId = dto.getQuestionnaireId();
        List<QuestionsInfoEntity> questions = dto.getQuestions();
        if (ObjectUtil.isNull(dto) || ObjectUtil.isNull(questionnaireId) || CollectionUtil.isEmpty(questions)) {
            return ApiResult.fail(400, "修改试题失败，未选中问卷或题目不存在");
        }
        /*
         * 先删后增
         */
        LambdaQueryWrapper<QuestionsInfoEntity> queryWrap = Wrappers.lambdaQuery();
        queryWrap.eq(QuestionsInfoEntity::getQuestionnaireId, questionnaireId);
        baseMapper.delete(queryWrap);
        for (QuestionsInfoEntity question : questions) {
            QuestionsInfoEntity po = new QuestionsInfoEntity();
            po.setQuestionnaireId(questionnaireId);
            po.setNum(question.getNum());
            po.setType(question.getType());
            po.setRequired(question.getRequired());
            po.setName(question.getName());
            po.setOptions(question.getOptions());
            po.setCreateBy(Constans.DEFAULT_USER);
            po.setCreateTime(sysDate);
            baseMapper.insert(po);
        }
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> importQuestions(Long quesId) {
        Date sysDate = new Date();
        QuestionnaireInfoEntity questionnaireInfoEntity = questionnaireInfoMapper.selectById(quesId);
        if (ObjectUtil.isNull(questionnaireInfoEntity)) {
            return ApiResult.fail(400, "问卷导入失败，问卷信息不存在");
        }
        /*
         * 检查问卷下是否有试题
         */
        LambdaQueryWrapper<QuestionsInfoEntity> queryWrap = Wrappers.lambdaQuery();
        queryWrap.eq(QuestionsInfoEntity::getQuestionnaireId, quesId);
        List<QuestionsInfoEntity> poList = baseMapper.selectList(queryWrap);
        if (CollectionUtil.isEmpty(poList)) {
            return ApiResult.fail(400, "问卷导入失败，问卷【" + questionnaireInfoEntity.getName() + "】下无试题");
        }
        for (QuestionsInfoEntity question : poList) {
            QuestionsInfoEntity po = new QuestionsInfoEntity();
            po.setQuestionnaireId(quesId);
            po.setNum(question.getNum());
            po.setType(question.getType());
            po.setRequired(question.getRequired());
            po.setName(question.getName());
            po.setOptions(question.getOptions());
            po.setCreateBy(Constans.DEFAULT_USER);
            po.setCreateTime(sysDate);
            baseMapper.insert(po);
        }
        return ApiResult.success();
    }
}
