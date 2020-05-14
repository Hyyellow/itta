package com.program.itta.controller;

import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.TagDTO;
import com.program.itta.domain.entity.Tag;
import com.program.itta.mapper.TaskTagMapper;
import com.program.itta.service.TagService;
import com.program.itta.service.TaskTagService;
import com.program.itta.service.UserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: itta
 * @description: 标签Api
 * @author: Mr. Boyle
 * @create: 2020-04-08 20:49
 **/
@RestController
@RequestMapping("/tag")
public class TagController {
    /*TODO
      1.标签的添加
      2.标签的删除
      3.标签的查找
      4.标签的更新
      5.标签与用户中间表相关
        (1).添加
        (2).删除
     */
    @Autowired
    private TagService tagService;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private TaskTagService taskTagService;

    @PostMapping("/addTag")
    public HttpResult addTag(@RequestParam(value = "taskId") Integer taskId,
                             @RequestParam(value = "content") String content) {
        Tag tag = Tag.builder().content(content).build();
        Boolean addTag = tagService.addTag(tag);
        Boolean addUserTag = userTagService.addUserTag(content);
        Boolean addTaskTag = taskTagService.addTaskTag(taskId, content);
        if (!(addTag && addTaskTag && addUserTag)) {
            throw new RuntimeException("添加任务标签失败");
        }
        return HttpResult.success();
    }
}
