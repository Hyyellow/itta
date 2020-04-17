package com.program.itta.controller;

import com.program.itta.common.exception.item.ItemAddFailException;
import com.program.itta.common.exception.item.ItemDelFailException;
import com.program.itta.common.exception.item.ItemNameExistsException;
import com.program.itta.common.exception.item.ItemUpdateFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.service.ItemService;
import com.program.itta.service.UserItemServive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
      1.项目的添加(完成)
      2.项目的删除(完成)
      3.项目的查找(完成)（待ES改善）
      4.项目的更新(完成)
      5.项目与用户中间表相关：
        （1）.添加(完成)
        （2）.删除(完成)
      */
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserItemServive userItemServive;

    @PostMapping("/addItem")
    public HttpResult addItem(@RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean addItem = itemService.addItem(item);
        Boolean addUserItem = userItemServive.addUserItem(item.getName());
        if (!(addItem && addUserItem)) {
            throw new ItemAddFailException("项目添加失败");
        }
        return HttpResult.success();
    }

    @DeleteMapping("/deleteItem")
    public HttpResult deleteItem(@RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean deleteUserItem = userItemServive.deleteUserItem(item.getId());
        Boolean deleteItem = itemService.deleteItem(item);
        if (!(deleteItem && deleteUserItem)) {
            throw new ItemDelFailException("项目删除失败");
        }
        return HttpResult.success();
    }

    @PutMapping("/updateItem")
    public HttpResult updateItem(@RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean updateItem = itemService.updateItem(item);
        if (!updateItem){
            throw new ItemUpdateFailException("项目更新失败");
        }
        return HttpResult.success();
    }

    @GetMapping("/selectItem")
    public HttpResult selectItem() {
        List<Integer> itemIdList = userItemServive.selectAllItem();
        if (itemIdList != null) {
            List<Item> itemList = itemService.selectAllItem(itemIdList);
            return HttpResult.success(itemList);
        } else {
            return HttpResult.success("该用户尚无项目存在");
        }
    }
}
