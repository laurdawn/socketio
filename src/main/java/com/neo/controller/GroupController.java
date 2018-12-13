package com.neo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.neo.entity.Group;
import com.neo.entity.GroupMember;
import com.neo.entity.User;
import com.neo.serivce.GroupService;
import com.neo.serivce.UserService;

@Controller
@RequestMapping(value = "/group")
public class GroupController extends BaseController {

    @Autowired
    GroupService groupService;
    
    @Autowired
    UserService userService;

    /**
     * 根据名字 查询 对应的群，群名称为空则查询 所有群
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/creat")
    public String creatGroup(String name, String avatar) {
        Group groupEntity = groupService.creatGroup(name, avatar, getSessionUser());
        return retResultData(0, "", groupEntity);
    }


    /**
     * 根据 名字 查询对应的群
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/findGroupsByName")
    public String findGroupsByName(String page, String name) {
        List<Group> list = groupService.findGroupsByGroupName(name);
        return retResultData(0, "", list);
    }


    /**
     * 查询指定群下面的 群成员
     * id 群id
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/findGroupUsers")
    public String findGroupMembers(String id) {
        List<GroupMember> list = groupService.findMembersByGroupId(id);
        List<String> userIdList = new ArrayList<String>();
        for (GroupMember member : list) {
        	userIdList.add(member.getGroupId());
        }
        List<User> userList = userService.findUsersById(userIdList);
        JSONObject obj = new JSONObject();
        obj.put("list", userList);
        return retResultData(0, "", obj);
    }


}
