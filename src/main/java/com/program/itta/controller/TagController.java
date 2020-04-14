package com.program.itta.controller;

import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.TagDTO;
import com.program.itta.domain.entity.Tag;
import com.program.itta.service.TagService;
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

    @PostMapping("/addTag")
    public HttpResult addTag(@RequestBody @Valid TagDTO tagDTO) {
        Tag tag = tagDTO.convertToTag();
        //TODO 中间表进行判断是否存在
        tagService.addTag(tag);
        return HttpResult.success();
    }

    @DeleteMapping("/deletaTag")
    public HttpResult deleteTag(@Valid @RequestBody TagDTO tagDTO) {
        Tag tag = tagDTO.convertToTag();
        // TODO 中间表的相关删除
        tagService.deleteTag(tag);
        return  HttpResult.success();
    }

    @PutMapping("/updateTag")
    public HttpResult updateTag(@Valid @RequestBody TagDTO tagDTO) {
        Tag tag = tagDTO.convertToTag();
        tagService.updateTag(tag);
        return HttpResult.success();
    }
}
