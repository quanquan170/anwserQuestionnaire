package com.aim.questionnaire.service;


import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.domain.dto.AddQuestionsDto;

public interface QuestionsService {

    /**
     * 试题保存
     * @param dto 要保存的试题
     */
    ApiResult<Object> save(AddQuestionsDto dto);

    /**
     * 试题修改
     * @param dto 要修改的试题
     */
    ApiResult<Object> update(AddQuestionsDto dto);

    /**
     * 试题导入-以问卷为维度导入
     * @param quesId 导入的试题id
     */
    ApiResult<Object> importQuestions(Long quesId);
}
