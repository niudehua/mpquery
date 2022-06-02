package cn.niudehua.mybatisplus.one2one.controller;

import java.util.LinkedHashMap;
import java.util.Objects;

import org.springframework.http.HttpStatus;

public class CommonResult extends LinkedHashMap<String, Object> {
    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";
    private static final long serialVersionUID = 1L;

    public CommonResult() {
    }

    public CommonResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public CommonResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (Objects.nonNull(data)) {
            super.put(DATA_TAG, data);
        }

    }

    public static CommonResult success() {
        return success("操作成功");
    }

    public static CommonResult success(Object data) {
        return success("操作成功", data);
    }

    public static CommonResult success(String msg) {
        return success(msg, null);
    }

    public static CommonResult success(String msg, Object data) {
        return new CommonResult(HttpStatus.OK.value(), msg, data);
    }

    public static CommonResult error() {
        return error("操作失败");
    }

    public static CommonResult error(String msg) {
        return error(msg, null);
    }

    public static CommonResult error(String msg, Object data) {
        return new CommonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    public static CommonResult error(int code, String msg) {
        return new CommonResult(code, msg, null);
    }
}
