package com.aim.questionnaire.controller;

import com.aim.questionnaire.common.ApiResult;
import com.aim.questionnaire.domain.dto.LoginDto;
import com.aim.questionnaire.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin")
@Slf4j
public class SysLoginController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/userLogin")
    public ApiResult<Object> userLogin(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }
}
