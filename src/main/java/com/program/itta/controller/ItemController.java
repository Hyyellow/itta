package com.program.itta.controller;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.*;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.dto.UserDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.service.ItemService;
import com.program.itta.service.UserItemServive;
import com.program.itta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        6.添加用户进入项目
      */
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserItemServive userItemServive;

    @Autowired
    private UserService userService;

    @Resource
    private JwtConfig jwtConfig;


    @PostMapping("/addItem")
    public HttpResult addItem(@RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean addItem = itemService.addItem(item);
        Boolean addUserItem = userItemServive.addUserItem(item.getName());
        if (!(addItem && addUserItem)) {
            throw new ItemAddFailException("项目添加失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success(item.getMarkId());
    }

    @PostMapping("/joinItem")
    public HttpResult joinItem(@RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean addUserItem = userItemServive.addItemMember(item.getId());
        if (!addUserItem) {
            throw new ItemAddMemberFailException("项目成员添加失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @PostMapping("/addItemMember")
    public HttpResult addItemMember(@RequestParam(value = "markId") String markId,
                                    @RequestParam(value = "itemId") Integer itemId) {
        UserDTO userDTO = userService.selectByMarkId(markId);
        Boolean addUserItem = userItemServive.addItemMember(userDTO.getId(),itemId);
        if (!addUserItem) {
            throw new ItemAddMemberFailException("项目成员添加失败");
        }
        jwtConfig.removeThread();
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
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @PutMapping("/updateItem")
    public HttpResult updateItem(@RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean updateItem = itemService.updateItem(item);
        if (!updateItem) {
            throw new ItemUpdateFailException("项目更新失败");
        }
        return HttpResult.success();
    }

    @GetMapping("/selectItem")
    public HttpResult selectItem() {
        List<Integer> itemIdList = userItemServive.selectAllItem();
        jwtConfig.removeThread();
        if (itemIdList != null && !itemIdList.isEmpty()) {
            List<Item> itemList = itemService.selectAllItem(itemIdList);
            return HttpResult.success(itemList);
        } else {
            return HttpResult.success("该用户尚无项目存在");
        }
    }

    @GetMapping("/selectItemByMarkId")
    public HttpResult selectItemByMarkId(@RequestParam(value = "markId") String markId) {
        List<Integer> itemIdList = userItemServive.selectAllItem();
        ItemDTO itemDTO = itemService.selectByMarkId(markId, itemIdList);
        jwtConfig.removeThread();
        if (itemDTO != null) {
            return HttpResult.success(itemDTO);
        } else {
            throw new ItemNotExistsException("项目id错误，该项目查找为空");
        }
    }

    @GetMapping("/selectUserListByItemId")
    public HttpResult selectUserListByItemId(@RequestParam(value = "itemId") Integer itemId) {
        List<Integer> userIdList = userItemServive.selectAllUser(itemId);
        List<UserDTO> userList = userService.selectUserByIdList(userIdList);
        if (userList != null && !userList.isEmpty()) {
            return HttpResult.success(userList);
        } else {
            throw new ItemFindUserListException("项目用户成员查找失败");
        }
    }
}
