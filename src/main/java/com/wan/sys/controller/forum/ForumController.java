package com.wan.sys.controller.forum;

import com.wan.sys.entity.comment.Comment;
import com.wan.sys.entity.comment.CommentQuery;
import com.wan.sys.entity.comment.CommentTypeEnum;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.forum.Forum;
import com.wan.sys.entity.image.Image;
import com.wan.sys.entity.image.ImageTypeEnum;
import com.wan.sys.entity.user.UserInfo;
import com.wan.sys.entity.view.View;
import com.wan.sys.entity.view.ViewTypeEnum;
import com.wan.sys.pojo.*;
import com.wan.sys.service.comment.ICommentService;
import com.wan.sys.service.forum.IForumService;
import com.wan.sys.service.image.IImageService;
import com.wan.sys.service.user.IUserInfoService;
import com.wan.sys.service.view.IViewService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("cityApp/forum")
public class ForumController {

    @Autowired
    private IForumService forumService;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IViewService viewService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserInfoService userInfoService;

    private static final String COMMENT_TYPE = CommentTypeEnum.FORUM.toString();
    private static final String VIEW_TYPE = ViewTypeEnum.FORUM.toString();
    private static final String IMG_TYPE = ImageTypeEnum.FORUM.getIndex();

    @ResponseBody
    @RequestMapping(value = "add", method = POST)
    public ResponseHead add(@Valid @RequestBody Forum forum, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        Long id = forumService.add(forum);

        if (forum.getImages() != null) {
            for (Image image : forum.getImages()) {
                image.setType(IMG_TYPE);
                image.setBelongId(id);
            }
            imageService.addImages(forum.getImages());
        }

        return OperateSuccess.Instance();
    }

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        List<Forum> forums = forumService.getList(query);
        for (int i=0; i < forums.size(); i++) {
            Forum forum = forums.get(i);
            forums.set(i, fillForum(forum));
        }

        return new ResponseSuccess(forumService.getList(query));
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {

        Forum forum = forumService.getById(id);

        if (forum != null) {
            forum = fillForum(forum);
            return new ResponseSuccess(forum);
        }

        return NoResultResponseFail.Instance();

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

    private Forum fillForum (Forum forum) {

        forum.setImages(getImages(forum));
        forum.setViewCount(getViewCount(forum));
        forum.setCommentCount(getCommentCount(forum));

        return forum;
    }

    private List<Image> getImages(Forum forum) {

        Image image = new Image();
        image.setBelongId(forum.getId());
        image.setType(IMG_TYPE);

        return imageService.getList(image);
    }

    private Long getViewCount(Forum forum) {

        View view = new View();
        view.setBelongId(forum.getId());
        view.setType(VIEW_TYPE);

        return viewService.count(view);
    }

    private Long getCommentCount(Forum forum) {

        Comment comment = new Comment();
        comment.setBelongId(forum.getId());
        comment.setType(COMMENT_TYPE);

        return commentService.count(comment);
    }
}
