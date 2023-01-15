package com.aim.questionnaire.common;

import cn.hutool.http.HttpStatus;
import com.aim.questionnaire.common.constant.ResultConst;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
@Builder
@NoArgsConstructor
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = -8987146499044811408L;

    /**
     * 通用返回状态
     */
    private Integer code;

    /**
     * 通用返回信息
     */
    private String message;

    /**
     * 通用返回数据
     */
    private T data;

    private ApiResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private ApiResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResult<T> success(Integer code, String message, T data) {
        return new ApiResult<>(code, message, data);
    }

    public static <T> ApiResult<T> success(Integer code, String message) {
        return success(code, message, null);
    }

    public static <T> ApiResult<T> success(String message, T data) {
        return success(HttpStatus.HTTP_OK, message, data);
    }

    public static <T> ApiResult<T> success(T data) {
        return success(HttpStatus.HTTP_OK, ResultConst.MSG_SUCCESS, data);
    }

    public static ApiResult<Object> success() {
        return success(null);
    }

//    public static ApiResult<Object> fail(int code, String message) {
//        return new ApiResult<>(code, message);
//    }

    public static <T> ApiResult<T> fail(Integer code, String message) {
        return new ApiResult<>(code, message, null);
    }

    public static <T> ApiResult<T> failException(String message) {
        return new ApiResult<T>(500, message, null);
    }

    public static ApiResult<Object> paramMissing() {
        return fail(HttpStatus.HTTP_BAD_REQUEST, ResultConst.MSG_PARAM_MISSING);
    }

    public static ApiResult<Object> paramMissing(String errorMsg) {
        return fail(HttpStatus.HTTP_BAD_REQUEST, errorMsg);
    }

    public static ApiResult<Object> fail(String message) {
        return fail(HttpStatus.HTTP_INTERNAL_ERROR, message);
    }

    public static ApiResult<Object> fail() {
        return fail(HttpStatus.HTTP_INTERNAL_ERROR, ResultConst.MSG_SYSTEM_EXCEPTION);
    }

    public static ApiResult<Object> toAjax(boolean success) {
        return success ? success() : fail();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
