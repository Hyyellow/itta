package com.program.itta.controller;

import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: itta
 * @description: 项目Api
 * @author: Mr.Huang
 * @create: 2020-04-08 08:39
 **/
@RestController
@RequestMapping("/item")
public class ItemController {
    /* TODO
      1.项目的添加
      2.项目的删除
      3.项目的查找（ES）
      4.项目的更新
      5.项目与用户中间表相关：
        （1）.添加
        （2）.删除
      */
    @Autowired
    private ItemService itemService;

    @PostMapping("/addItem")
    public HttpResult addItem(@RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        // TODO 中间表进行判断是否存在
        itemService.addItem(item);
        return HttpResult.success();
    }

    @DeleteMapping("/deleteItem")
    public HttpResult deleteItem(@Valid @RequestBody ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        // TODO 中间表的相关删除
        itemService.deleteItem(item);
        return HttpResult.success();
    }

    @PutMapping("/updateItem")
    public HttpResult updateItem(@Valid @RequestBody ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        itemService.updateItem(item);
        return HttpResult.success();
    }
}
