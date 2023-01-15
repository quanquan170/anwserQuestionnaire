package com.aim.questionnaire.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.common.constant.Constans;
import com.aim.questionnaire.common.excel.BaseExcel;
import com.aim.questionnaire.common.utils.BaseUtil;
import com.aim.questionnaire.common.utils.ListPage;
import com.aim.questionnaire.common.utils.StringUtils;
import com.aim.questionnaire.domain.dto.AddUserDto;
import com.aim.questionnaire.domain.dto.LoginDto;
import com.aim.questionnaire.domain.dto.PageSearchUserDto;
import com.aim.questionnaire.domain.dto.UpdateUserDto;
import com.aim.questionnaire.domain.entity.UserInfoEntity;
import com.aim.questionnaire.mapper.UserMapper;
import com.aim.questionnaire.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfoEntity> implements UserService {

    @Override
    public ApiResult<Object> login(LoginDto dto) {
        UserInfoEntity user = getOne(Wrappers.<UserInfoEntity>lambdaQuery().eq(UserInfoEntity::getUserName,
                dto.getUsername()).last(" limit 1"));
        if (ObjectUtil.isNull(user)) {
            return ApiResult.fail(400, "用户信息不存在");
        }
        if (!user.getPassword().equals(dto.getPassword())) {
            return ApiResult.fail(400, "用户名或密码输入错误");
        }
        if (user.getStatus() == 0) {
            return ApiResult.fail(400, "账号已停用，无法登陆");
        }
        return ApiResult.success(user);
    }

    @Override
    public ListPage<UserInfoEntity> pageSearchUserList(PageSearchUserDto pageSearchUserDto) {
        LambdaQueryWrapper<UserInfoEntity> queryWrap = Wrappers.lambdaQuery();
        if (!StringUtils.isNull(pageSearchUserDto.getUserName())) {
            queryWrap.like(UserInfoEntity::getUserName, pageSearchUserDto.getUserName());
        }
        List<UserInfoEntity> rtnList = baseMapper.selectList(queryWrap);
        return new ListPage<>(rtnList, pageSearchUserDto.getPageNum(), pageSearchUserDto.getPageSize());
    }

    @Override
    public ApiResult<Object> addUser(AddUserDto addUserDto) {
        UserInfoEntity po = new UserInfoEntity();
        /*
         * 校验用户名是否重复
         */
        UserInfoEntity userInfo = getOne(Wrappers.<UserInfoEntity>lambdaQuery().eq(UserInfoEntity::getUserName,addUserDto.getUserName()).last(" limit 1"));
        if(Objects.nonNull(userInfo)){
            return ApiResult.fail(400,"用户名称已存在");
        }
        po.setUserName(addUserDto.getUserName());
        po.setPassword(addUserDto.getPassword());
        po.setStatus(addUserDto.getStatus());
        /*
         * 用户相关默认赋值
         */
        po.setCreateBy(Constans.DEFAULT_USER);
        po.setCreateTime(new Date());
        baseMapper.insert(po);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> updateUser(UpdateUserDto updateUserDto) {
        UserInfoEntity user = baseMapper.selectById(updateUserDto.getId());
        if (ObjectUtil.isNull(user)) {
            return ApiResult.fail(400, "用户修改失败，修改的用户信息不存在!");
        }
        /*
         * 用户名称重复校验
         */
        UserInfoEntity checkUser = getOne(Wrappers.<UserInfoEntity>lambdaQuery().eq(UserInfoEntity::getUserName,
                updateUserDto.getUserName()).last(" limit 1"));
        if(Objects.nonNull(checkUser) && !checkUser.getId().equals(updateUserDto.getId())){
            return ApiResult.fail(400,"用户名称已存在");
        }
        user.setUserName(updateUserDto.getUserName());
        user.setPassword(updateUserDto.getPassword());
        user.setStatus(updateUserDto.getStatus());
        /*
         * 用户部分默认赋值
         */
        user.setUpdateBy(Constans.DEFAULT_USER);
        user.setUpdateTime(new Date());
        baseMapper.updateById(user);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> deleteUser(Long id) {
        UserInfoEntity user = baseMapper.selectById(id);
        if (ObjectUtil.isNull(user)) {
            return ApiResult.fail(400, "用户删除失败，用户信息不存在!");
        }
        baseMapper.deleteById(id);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> resetPwd(Long id) {
        UserInfoEntity user = baseMapper.selectById(id);
        if (ObjectUtil.isNull(user)) {
            return ApiResult.fail(400, "用户删除失败，用户信息不存在!");
        }
        user.setPassword(Constans.DEFAULT_PWD);
        baseMapper.updateById(user);
        return ApiResult.success();
    }

    @Override
    public ApiResult<Map<String, Object>> exportUser(File file, PageSearchUserDto pageSearchUserDto) {
        Map<String, Object> rtnMap = new HashMap<>(2);
        LambdaQueryWrapper<UserInfoEntity> queryWrap = Wrappers.lambdaQuery();
        if (!StringUtils.isNull(pageSearchUserDto.getUserName())) {
            queryWrap.like(UserInfoEntity::getUserName, pageSearchUserDto.getUserName());
        }
        List<UserInfoEntity> rtnList = baseMapper.selectList(queryWrap);
        try {
            BaseExcel baseExcel = BaseExcel.newInstrance(file);
            baseExcel.selectSheet(0);
            int rowIndex = 1;
            for (UserInfoEntity po : rtnList) {
                int columnIndex = 0;
                baseExcel.writeString(rowIndex, columnIndex++, BaseUtil.repNull(po.getUserName()));
                baseExcel.writeString(rowIndex, columnIndex++, BaseUtil.repNull(po.getPassword()));
                if (rowIndex >= 2) {
                    for (int i = 0; i < columnIndex; i++) {
                        baseExcel.copyCellStyle(1, i, rowIndex, i);
                    }
                }
                rowIndex++;
            }
            baseExcel.save();
            String fileName = "用户报表";
            rtnMap.put("fileName", fileName);
            rtnMap.put("file", file);
        } catch (Exception e) {
            log.error("用户列表导出失败", e);
            return ApiResult.fail(400, "用户列表导出失败,失败原因：" + e);
        }
        return ApiResult.success(rtnMap);
    }

    @Override
    public ApiResult<Object> importUser(MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            return ApiResult.fail(400, "文件为空！");
        }
        try {
            String name = BaseUtil.getExtensionName(Objects.requireNonNull(file.getOriginalFilename()));
            if (!Constans.EXCEL_2003_SUFFIX.equalsIgnoreCase(name)
                    && !Constans.EXCEL_2007_SUFFIX.equalsIgnoreCase(name)) {
                return ApiResult.fail(400, "文件扩展名不正确!");
            }
            BaseExcel baseExcel = BaseExcel.newInstrance(file);
            baseExcel.selectSheet(0);
            if (baseExcel.getLastRowNum() <= 0) {
                return ApiResult.fail(400, "导入数据不存在");
            }
            Date sysDate = new Date();
            List<UserInfoEntity> poList = new ArrayList<>();
            for (int i = 1; i <= baseExcel.getLastRowNum(); i++) {
                UserInfoEntity po = new UserInfoEntity();
                if (baseExcel.isEmptyRow(i)) {
                    continue;
                }
                String userName = baseExcel.getString(i, 0);
                String pwd = baseExcel.getString(i, 1);
                if (StringUtils.isNull(userName)) {
                    return ApiResult.fail(400, "用户excel导入失败,第：" + i + "行，用户名称不能为空");
                }
                if (StringUtils.isNull(pwd)) {
                    return ApiResult.fail(400, "用户excel导入失败,第：" + i + "行，密码不能为空");
                }
                po.setUserName(userName);
                po.setPassword(pwd);
                po.setStatus(1);
                po.setCreateTime(sysDate);
                po.setCreateBy(Constans.DEFAULT_USER);
                poList.add(po);
            }
            this.saveBatch(poList);
        } catch (Exception e) {
            log.error("用户excel导入失败，失败原因：{}", e);
            return ApiResult.fail(400, "用户excel导入失败");
        }
        return ApiResult.success();
    }

    @Override
    public ApiResult<Object> update(Long id) {
        UserInfoEntity userInfo = getById(id);
        if(ObjectUtil.isNotNull(userInfo)){
            Integer status = userInfo.getStatus()==1?0:1;
            userInfo.setStatus(status);
            updateById(userInfo);
            return ApiResult.success(200, "用户状态更新成功");
        }
        return ApiResult.fail(400, "用户状态更新失败");
    }

    @Override
    public ApiResult<List<UserInfoEntity>> userList() {
        return ApiResult.success(baseMapper.selectList(null));
    }
}
