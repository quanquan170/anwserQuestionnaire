package com.aim.questionnaire.service;


import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.domain.dto.*;
import com.aim.questionnaire.domain.entity.QuestionnaireInfoEntity;
import com.aim.questionnaire.domain.vo.QuestionnaireVO;

import java.util.List;

public interface QuestionnaireInfoService {

    /**
     * 问卷下拉框列表
     */
    List<QuestionnaireInfoEntity> getQuesCombos();

    /**
     * 新增问卷
     */
    ApiResult<Object> add(AddQuestionnaireDto dto);

    /**
     * 修改问卷
     */
    ApiResult<Object> update(UpdateQuestionnaireDto dto);

    /**
     * 问卷删除
     */
    ApiResult<Object> delete(Long id);

    /**
     * 分页查询
     */
    ListPage<QuestionnaireVO> pageSearch(PageSearchQuestionnaireDto dto);

    /**
     * 问卷启停
     */
    ApiResult<Object> startAndStop(Long id);

    /**
     * 项目启动
     */
    ApiResult<Object> modifyHistoryQuestionnaireStatus(ModifyQuestionnaireDto modifyQuestionnaireDto);

    ApiResult<Object> sendLink(SendLinkQuestionnaireDto sendLinkQuestionnaireDto);
}
