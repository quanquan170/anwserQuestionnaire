package com.aim.questionnaire.controller;

import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.domain.dto.*;
import com.aim.questionnaire.domain.entity.QuestionnaireInfoEntity;
import com.aim.questionnaire.domain.vo.QuestionnaireVO;
import com.aim.questionnaire.service.QuestionnaireInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/questionnaire")
@Slf4j
@Api(value = "问卷管理", tags = "问卷管理接口")
public class QuestionnaireController {

    @Resource
    private QuestionnaireInfoService questionnaireInfoService;

    @ApiOperation(value = "问卷列表分页查询")
    @PostMapping(value = "/pageSearchQuestionnaireList")
    public ApiResult<ListPage<QuestionnaireVO>> pageSearchQuestionnaireList(@RequestBody PageSearchQuestionnaireDto dto) {
        return ApiResult.success(questionnaireInfoService.pageSearch(dto));
    }

    @ApiOperation(value = "问卷新增")
    @PostMapping(value = "/add")
    public ApiResult<Object> add(@RequestBody @Validated AddQuestionnaireDto addDto) {
        return questionnaireInfoService.add(addDto);
    }

    @ApiOperation(value = "问卷修改")
    @PostMapping(value = "/update")
    public ApiResult<Object> update(@RequestBody @Validated UpdateQuestionnaireDto updateDto) {
        return questionnaireInfoService.update(updateDto);
    }

    @ApiOperation(value = "问卷删除")
    @PostMapping(value = "/delete/{id}")
    public ApiResult<Object> delete(@PathVariable("id") Long id) {
        return questionnaireInfoService.delete(id);
    }

    @ApiOperation(value = "问卷下拉框")
    @PostMapping(value = "/getCombo")
    public ApiResult<List<QuestionnaireInfoEntity>> getCombo(){
        return ApiResult.success(questionnaireInfoService.getQuesCombos());
    }

    @ApiOperation(value = "问卷启停")
    @PostMapping(value = "/startOrStop/{id}")
    public ApiResult<Object> startOrStop(@PathVariable("id") Long id) {
        return questionnaireInfoService.startAndStop(id);
    }


    @ApiOperation(value = "问卷启用")
    @PostMapping(value = "/modifyHistoryQuestionnaireStatus")
    public ApiResult<Object> modifyHistoryQuestionnaireStatus(@RequestBody ModifyQuestionnaireDto modifyQuestionnaireDto) {
        return questionnaireInfoService.modifyHistoryQuestionnaireStatus(modifyQuestionnaireDto);
    }

    @ApiOperation(value = "发送链接")
    @PostMapping(value = "/sendLink")
    public ApiResult<Object> sendLink(@RequestBody  SendLinkQuestionnaireDto sendLinkQuestionnaireDto) {
        return questionnaireInfoService.sendLink(sendLinkQuestionnaireDto);
    }

}
