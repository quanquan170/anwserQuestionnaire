package com.aim.questionnaire.mapper;

import com.aim.questionnaire.domain.dto.PageSearchQuestionnaireDto;
import com.aim.questionnaire.domain.entity.QuestionnaireInfoEntity;
import com.aim.questionnaire.domain.vo.QuestionnaireVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionnaireInfoMapper extends BaseMapper<QuestionnaireInfoEntity> {

    List<QuestionnaireVO> pageSearchList(@Param("dto") PageSearchQuestionnaireDto dto);
}
