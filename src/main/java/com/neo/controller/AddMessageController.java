package com.neo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.neo.serivce.SysmsgService;

@Controller
@RequestMapping(value = "/addMsg")
public class AddMessageController extends BaseController {

    @Autowired
    SysmsgService addMessageService;

    /**
     * 询消息盒子信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/findAddInfo")
    public String findAddInfo(String page) {
        JSONObject obj = addMessageService.findAddInfo(getSessionUser().getId());
        return retResultData(0, "", obj);
    }

}
