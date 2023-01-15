package com.aim.questionnaire.service;


import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.domain.dto.AddUserDto;
import com.aim.questionnaire.domain.dto.LoginDto;
import com.aim.questionnaire.domain.dto.PageSearchUserDto;
import com.aim.questionnaire.domain.dto.UpdateUserDto;
import com.aim.questionnaire.domain.entity.UserInfoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 用户登录
     */
    ApiResult<Object> login(LoginDto dto);

    /**
     * 用户分页查询
     */
    ListPage<UserInfoEntity> pageSearchUserList(PageSearchUserDto pageSearchUserDto);

    /**
     * 用户新增
     */
    ApiResult<Object> addUser(AddUserDto addUserDto);

    /**
     * 用户修改
     */
    ApiResult<Object> updateUser(UpdateUserDto updateUserDto);

    /**
     * 用户删除
     */
    ApiResult<Object> deleteUser(Long id);

    /**
     * 密码重置
     */
    ApiResult<Object> resetPwd(Long id);

    /**
     * 用户excel导出
     */
    ApiResult<Map<String, Object>> exportUser(File file, PageSearchUserDto pageSearchUserDto);

    /**
     * 用户导入
     */
    ApiResult<Object> importUser(MultipartFile file);

    ApiResult<Object> update(Long id);

    ApiResult<List<UserInfoEntity>> userList();
}
