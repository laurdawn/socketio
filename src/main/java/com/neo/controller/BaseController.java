package com.neo.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.neo.entity.Result;
import com.neo.entity.User;
import com.neo.enums.EResultType;

public class BaseController {


    @Autowired
    HttpSession session;


    public User getSessionUser(){
        return (User) session.getAttribute("username");
    }

    protected Logger logger = LogManager.getLogger(getClass().getName());


    protected String retResultData(EResultType type) {
        return JSON.toJSONString(new Result(type));
    }

    protected String retResultData(EResultType type, Object data) {
        return JSON.toJSONString(new Result(type, data));
    }

    protected String retResultData(Integer code, String message) {
        return JSON.toJSONString(new Result(code, message));
    }

    protected String retResultData(Integer code, String message, Object data) {
        return JSON.toJSONString(new Result(code, message, data));
    }





}
