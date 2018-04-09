package com.wan.sys.controller.forum;

import com.wan.sys.entity.common.Query;
import com.wan.sys.entity.forum.Forum;
import com.wan.sys.entity.image.Image;
import com.wan.sys.pojo.ImageTypeEnum;
import com.wan.sys.pojo.ResponseHead;
import com.wan.sys.pojo.OperateSuccess;
import com.wan.sys.pojo.ResponseSuccess;
import com.wan.sys.service.forum.IForumService;
import com.wan.sys.service.image.IImageService;
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

    @ResponseBody
    @RequestMapping(value = "add", method = POST)
    public ResponseHead add(@Valid @RequestBody Forum forum, BindingResult result) {

        if (result.hasErrors()) {
            return ValidUtil.errorResponse(result);
        }

        Long id = forumService.add(forum);

        if (forum.getImages() != null) {
            for (Image image : forum.getImages()) {
                image.setType(ImageTypeEnum.FORUM.getIndex());
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

        for (Forum forum : forums) {
            forum.setImages(getImages(forum));
        }

        return new ResponseSuccess(forumService.getList(query));
    }

    @ResponseBody
    @RequestMapping("getById")
    public ResponseHead getById(Long id) {

        Forum forum = forumService.getById(id);

        if (forum != null) {
            forum.setImages(getImages(forum));
        }

        return new ResponseSuccess(forum);
    }

    private List<Image> getImages(Forum forum) {
        Image image = new Image();
        image.setBelongId(forum.getId());
        image.setType(ImageTypeEnum.FORUM.getIndex());

        return imageService.getList(image);
    }
}
