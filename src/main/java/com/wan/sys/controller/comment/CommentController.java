package com.wan.sys.controller.comment;

import com.wan.sys.entity.comment.Comment;
import com.wan.sys.entity.comment.CommentQuery;
import com.wan.sys.pojo.OperateSuccess;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.comment.ICommentService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("cityApp/comment")
public class CommentController {

    @Autowired
    ICommentService commentService;

    /**
     * 新增评论接口
     * @param comment 评论
     * @param result 对象绑定结果
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add", method = POST)
    public ResponseHead add(@Valid @RequestBody Comment comment, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        commentService.add(comment);

        return OperateSuccess.Instance();
    }

    /**
     * 获取评论列表接口
     * @param query 查询参数
     * @param result 对象绑定结果
     * @return
     */
    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid CommentQuery query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseSuccess(commentService.getList(query));
    }
}
