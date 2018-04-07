package com.wan.sys.controller.forum;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.forum.Forum;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.forum.IForumService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("cityApp/forum")
public class ForumController {

    @Autowired
    private IForumService forumService;

    @ResponseBody
    @RequestMapping("add")
    public ResponseHead add(@Valid Forum forum, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }
        forumService.add(forum);

        return ResponseSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseHead(forumService.getList(query));
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {
        return new ResponseHead(forumService.getById(id));
    }
}
