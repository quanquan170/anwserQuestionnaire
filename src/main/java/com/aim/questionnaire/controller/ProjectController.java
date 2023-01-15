package com.aim.questionnaire.controller;

import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.domain.dto.AddProjectDto;
import com.aim.questionnaire.domain.dto.PageSearchProjectDto;
import com.aim.questionnaire.domain.dto.ProjectBindDto;
import com.aim.questionnaire.domain.dto.UpdateProjectDto;
import com.aim.questionnaire.domain.entity.ProjectInfoEntity;
import com.aim.questionnaire.service.ProjectInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/project")
@Slf4j
@Api(value = "项目管理", tags = "项目管理接口")
public class ProjectController {

    @Resource
    private ProjectInfoService projectInfoService;

    @ApiOperation(value = "项目列表分页查询")
    @PostMapping(value = "/pageSearchProjectList")
    public ApiResult<ListPage<ProjectInfoEntity>> pageSearchProjectList(@RequestBody PageSearchProjectDto dto) {
        return ApiResult.success(projectInfoService.pageSearchProjectList(dto));
    }

    @ApiOperation(value = "项目新增")
    @PostMapping(value = "/add")
    public ApiResult<Object> add(@RequestBody @Validated AddProjectDto addDto) {
        return projectInfoService.saveProjectInfo(addDto);
    }

    @ApiOperation(value = "项目修改")
    @PostMapping(value = "/update")
    public ApiResult<Object> update(@RequestBody @Validated UpdateProjectDto updateDto) {
        return projectInfoService.updateProjectInfo(updateDto);
    }

    @ApiOperation(value = "项目删除")
    @PostMapping(value = "/delete/{id}")
    public ApiResult<Object> delete(@PathVariable("id") Long id) {
        return projectInfoService.deleteProjectInfo(id);
    }

    @ApiOperation(value = "项目下拉框")
    @PostMapping(value = "/getProjectCombo")
    public ApiResult<List<ProjectInfoEntity>> getProjectCombo(){
        return ApiResult.success(projectInfoService.getProjectCombos());
    }

    @ApiOperation(value = "项目和问卷绑定")
    @PostMapping(value = "/binding")
    public ApiResult<Object> binding(@RequestBody ProjectBindDto dto) {
        return projectInfoService.bindingQuestionnaire(dto.getProjectId(), dto.getQuesIds());
    }

    @ApiOperation(value = "获取项目详情")
    @PostMapping(value = "/getById/{id}")
    public ApiResult<Object> getById(@PathVariable("id") Long id) {
        return projectInfoService.getById(id);
    }


}
