package com.aim.questionnaire.mapper;

import com.aim.questionnaire.domain.entity.UserInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserInfoEntity> {

}
