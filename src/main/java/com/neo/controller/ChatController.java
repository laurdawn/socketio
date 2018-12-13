package com.neo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.enums.EResultType;
import com.neo.serivce.ChatService;


@Controller
@RequestMapping(value = "/chat")
public class ChatController extends BaseController {

    @Autowired
    ChatService chatService;

    @ResponseBody
    @RequestMapping(value = "/t")
    public String test() {
        return retResultData(EResultType.SUCCESS, "ks");
    }

    @ResponseBody
    @RequestMapping(value = "/t4")
    public String tests() {
        chatService.sendApnData();
        return retResultData(EResultType.SUCCESS);
    }


}
