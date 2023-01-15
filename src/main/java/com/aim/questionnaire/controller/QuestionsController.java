package com.aim.questionnaire.controller;

import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.domain.dto.*;
import com.aim.questionnaire.service.QuestionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/questions")
@Slf4j
@Api(value = "试题管理", tags = "试题管理接口")
public class QuestionsController {

    @Resource
    private QuestionsService questionsService;

    @ApiOperation(value = "试题列表分页查询")
    @PostMapping(value = "/queryQuestionnaireById")
    public ApiResult<Object> pageSearchQuestionnaireList(@RequestBody @Validated PageSearchQuestionDto psqd) {
        return ApiResult.success(200,"");
    }

    @ApiOperation(value = "试题新增")
    @PostMapping(value = "/add")
    public ApiResult<Object> add(@RequestBody @Validated AddQuestionsDto addDto) {
        return questionsService.save(addDto);
    }

    @ApiOperation(value = "试题修改")
    @PostMapping(value = "/update")
    public ApiResult<Object> update(@RequestBody @Validated AddQuestionsDto updateDto) {
        return questionsService.update(updateDto);
    }

    @ApiOperation(value = "试题导入")
    @PostMapping(value = "/importQuestions")
    public ApiResult<Object> importQuestions(Long quesId) {
        return questionsService.importQuestions(quesId);
    }


}
