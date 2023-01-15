package com.aim.questionnaire.controller;

import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.BaseFileResponse;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.domain.dto.*;
import com.aim.questionnaire.domain.entity.UserInfoEntity;
import com.aim.questionnaire.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户管理", tags = "用户管理接口")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户列表分页查询")
    @PostMapping(value = "/pageSearchUserList")
    public ApiResult<ListPage<UserInfoEntity>> pageSearchProjectList(@RequestBody PageSearchUserDto pageSearchUserDto) {
        return ApiResult.success(userService.pageSearchUserList(pageSearchUserDto));
    }

    @ApiOperation(value = "用户新增")
    @PostMapping(value = "/add")
    public ApiResult<Object> add(@RequestBody @Validated AddUserDto addDto) {
        return userService.addUser(addDto);
    }

    @ApiOperation(value = "用户修改")
    @PostMapping(value = "/update")
    public ApiResult<Object> update(@RequestBody @Validated UpdateUserDto updateDto) {
        return userService.updateUser(updateDto);
    }

    @ApiOperation(value = "用户状态修改")
    @PostMapping(value = "/updateStatus/{id}")
    public ApiResult<Object> updateStatus(@PathVariable("id") Long id) {
        return userService.update(id);
    }

    @ApiOperation(value = "用户删除")
    @PostMapping(value = "/delete/{id}")
    public ApiResult<Object> delete(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @ApiOperation(value = "重置密码")
    @PostMapping(value = "/resetPwd/{id}")
    public ApiResult<Object> resetPwd(@PathVariable("id") Long id) {
        return userService.resetPwd(id);
    }

    @ApiOperation(value = "导入excel")
    @PostMapping("/importExcel")
    public ApiResult<Object> importExcel(MultipartFile file) {
        return userService.importUser(file);
    }

    @ApiOperation(value = "导出")
    @PostMapping("/exportUser")
    public ApiResult<Object> exportUser(HttpServletResponse res, @RequestBody PageSearchUserDto pageSearchUserDto) {
        File exportFile = null;
        InputStream stream = null;
        try {
            stream = getClass().getClassLoader().getResourceAsStream("templates/user_temp.xlsx");
            File file = new File("templates/user_temp.xlsx");
            if (stream != null) {
                FileUtils.copyInputStreamToFile(stream, file);
            }
            BaseFileResponse response = new BaseFileResponse(res);
            Map<String, Object> map = userService.exportUser(file, pageSearchUserDto).getData();
            exportFile = (File) map.get("file");
            String fileName = (String) map.get("fileName");
            response.setFile(exportFile);
            response.setFileName(fileName + ".xlsx");
            response.flush();
            return ApiResult.success();
        } catch (Throwable ex) {
            log.error("导出文件失败，错误原因：", ex);
            return ApiResult.fail(400, "导出失败,失败原因：" + ex);
        } finally {
            if (exportFile != null) {
                exportFile.delete();
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @ApiOperation(value = "用户列表")
    @PostMapping(value = "/userList")
    public ApiResult<List<UserInfoEntity>> userList() {
        return userService.userList();
    }


}
