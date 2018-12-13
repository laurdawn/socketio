package com.neo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {

    @RequestMapping("/")
    public String index(HttpSession session) {
//    	return "static/index.html";
        if (session.getAttribute("username") != null) {
            return "static/index.html";
        }
        return "static/login.html";
    }
}
