package com.wan.sys.controller.dynamic;

import com.wan.sys.entity.comment.Comment;
import com.wan.sys.entity.comment.CommentQuery;
import com.wan.sys.entity.comment.CommentTypeEnum;
import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.dynamic.Dynamic;
import com.wan.sys.entity.view.View;
import com.wan.sys.entity.view.ViewTypeEnum;
import com.wan.sys.pojo.NoResultResponseFail;
import com.wan.sys.pojo.OperateSuccess;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.comment.ICommentService;
import com.wan.sys.service.dynamic.IDynamicService;
import com.wan.sys.service.view.IViewService;
import com.wan.sys.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 
 */
@Controller
@RequestMapping("cityApp/dynamic")
public class DynamicController {

    @Autowired
    private IDynamicService dynamicService;

    @Autowired
    private IViewService viewService;

    @Autowired
    private ICommentService commentService;

    private static final String COMMENT_TYPE = CommentTypeEnum.DYNAMIC.toString();
    private static final String VIEW_TYPE = ViewTypeEnum.DYNAMIC.toString();

    @ResponseBody
    @RequestMapping("getList")
    public ResponseHead getList(@Valid Query query, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        return new ResponseSuccess(dynamicService.getList(query));
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {

        Dynamic dynamic = dynamicService.getById(id);

        if (dynamic == null) {
            return NoResultResponseFail.Instance();
        }
        View view = new View();
        view.setBelongId(dynamic.getId());
        view.setType(VIEW_TYPE);
        viewService.add(view);

        return new ResponseSuccess(dynamic);
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
