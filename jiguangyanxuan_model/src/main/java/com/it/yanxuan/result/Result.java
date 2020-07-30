package com.it.yanxuan.result;

import java.io.Serializable;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/24 17:00
 */
public class Result implements Serializable {

    private Boolean code;//成功返回true

    private String message;//返回信息

    public Result() {
    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
