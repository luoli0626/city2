package com.wan.sys.controller.forum;

import com.wan.sys.entity.comment.Comment;
import com.wan.sys.entity.comment.CommentQuery;
import com.wan.sys.entity.comment.CommentTypeEnum;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.forum.Forum;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.image.ImageTypeEnum;
import com.wan.sys.pojo.*;
import com.wan.sys.service.comment.ICommentService;
import com.wan.sys.service.forum.IForumService;
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
@RequestMapping("cityApp/forum")
public class ForumController {

    @Autowired
    private IForumService forumService;

    @Autowired
    private ICommentService commentService;

    private static final String COMMENT_TYPE = CommentTypeEnum.FORUM.toString();
    private static final String IMG_TYPE = ImageTypeEnum.FORUM.getIndex();

    @ResponseBody
    @RequestMapping(value = "add", method = POST)
    public ResponseHead add(@Valid @RequestBody Forum forum, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        if (forum.getImages() != null) {
            for (Image image : forum.getImages()) {
                image.setType(IMG_TYPE);
            }
        }
        forumService.add(forum);

        return OperateSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseSuccess(forumService.getList(query));
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {

        Forum forum = forumService.getById(id);

        if (forum == null) {
            return NoResultResponseFail.Instance();
        }
        return new ResponseSuccess(forum);
    }

    @ResponseBody
    @RequestMapping(value = "addComment", method = POST)
    public ResponseHead addComment(@Valid @RequestBody Comment comment, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }
        comment.setType(COMMENT_TYPE);
        commentService.add(comment);

        return OperateSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getCommentList")
    public ResponseHead getCommentList(@Valid CommentQuery query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        query.setType(COMMENT_TYPE);
        return new ResponseSuccess(commentService.getList(query));
    }
}
