package com.zyb.util;

import java.io.Serializable;
import java.util.List;

/**
 *@author: zhouyongbao
 *@description:
 *@date: 2019/10/24 002418:47
 *
 */
public class Result implements Serializable {

    private String code;
    private String message;
    private List list;
    private Object object;

    public Result(String code, String message, List list, Object object) {
        this.code = code;
        this.message = message;
        this.list = list;
        this.object = object;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", list=" + list +
                ", object=" + object +
                '}';
    }
}
