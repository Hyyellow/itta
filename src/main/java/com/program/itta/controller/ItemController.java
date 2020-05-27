package com.program.itta.controller;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.*;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.dto.UserDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.service.ItemService;
import com.program.itta.service.TaskService;
import com.program.itta.service.UserItemServive;
import com.program.itta.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: itta
 * @description: 项目API
 * @author: Mr.Huang
 * @create: 2020-04-08 08:39
 **/
@Api(tags = "项目接口")
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

    @Autowired
    private TaskService taskService;

    @Resource
    private JwtConfig jwtConfig;


    @ApiOperation(value = "添加项目", notes = "(创建项目)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 20002, message = "项目添加失败")})
    @PostMapping("/addItem")
    public HttpResult addItem(@ApiParam(name = "项目DTO类", value = "传入Json格式", required = true)
                              @RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean addItem = itemService.addItem(item);
        Boolean addUserItem = userItemServive.addUserItem(item.getName());
        if (!(addItem && addUserItem)) {
            throw new ItemAddFailException("项目添加失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success(item.getMarkId());
    }

    @ApiOperation(value = "加入项目", notes = "(通过项目标志id查找到项目后，加入该项目)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 20008, message = "项目用户成员添加失败")})
    @PostMapping("/joinItem")
    public HttpResult joinItem(@ApiParam(name = "项目DTO类", value = "传入Json格式", required = true)
                               @RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean addUserItem = userItemServive.addItemMember(item.getId());
        if (!addUserItem) {
            throw new ItemAddMemberFailException("项目成员添加失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @ApiOperation(value = "添加项目成员", notes = "(通过个人标志id拉该用户加入项目)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 20008, message = "项目用户成员添加失败")})
    @PostMapping("/addItemMember")
    public HttpResult addItemMember(@ApiParam(name = "成员标志id", value = "传入Json格式", required = true)
                                    @RequestParam(value = "markId") String markId,
                                    @ApiParam(name = "项目id", value = "传入Json格式", required = true)
                                    @RequestParam(value = "itemId") Integer itemId) {
        UserDTO userDTO = userService.selectByMarkId(markId);
        Boolean addUserItem = userItemServive.addItemMember(userDTO.getId(), itemId);
        if (!addUserItem) {
            throw new ItemAddMemberFailException("项目成员添加失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @ApiOperation(value = "删除项目", notes = "(删除此项目以及该项目下的所有任务)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 20003, message = "项目删除失败")})
    @DeleteMapping("/deleteItem")
    public HttpResult deleteItem(@ApiParam(name = "项目DTO类", value = "传入Json格式", required = true)
                                 @RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean deleteUserItem = userItemServive.deleteUserItem(item.getId());
        Boolean deleteItem = itemService.deleteItem(item);
        Boolean deleteByItemId = taskService.deleteByItemId(item.getId());
        if (!(deleteItem && deleteUserItem && deleteByItemId)) {
            throw new ItemDelFailException("项目删除失败");
        }
        jwtConfig.removeThread();
        return HttpResult.success();
    }

    @ApiOperation(value = "编辑项目", notes = "(编辑该项目的内容)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 20004, message = "项目更新失败")})
    @PutMapping("/updateItem")
    public HttpResult updateItem(@ApiParam(name = "项目DTO类", value = "传入Json格式", required = true)
                                 @RequestBody @Valid ItemDTO itemDTO) {
        Item item = itemDTO.convertToItem();
        Boolean updateItem = itemService.updateItem(item);
        if (!updateItem) {
            throw new ItemUpdateFailException("项目更新失败");
        }
        return HttpResult.success();
    }

    @ApiOperation(value = "查找所有项目", notes = "(查看该用户下的所有项目)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户尚无项目存在")})
    @GetMapping("/selectItem")
    public HttpResult selectItem() {
        List<Integer> itemIdList = userItemServive.selectByUserId();
        jwtConfig.removeThread();
        if (itemIdList != null && !itemIdList.isEmpty()) {
            List<Item> itemList = itemService.selectItemList(itemIdList);
            return HttpResult.success(itemList);
        } else {
            return HttpResult.success("该用户尚无项目存在");
        }
    }

    @ApiOperation(value = "查找项目", notes = "(通过项目标志id查找项目)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 20005, message = "项目不存在，项目id查找为空")})
    @GetMapping("/selectItemByMarkId")
    public HttpResult selectItemByMarkId(@ApiParam(name = "项目标志id", value = "传入Json格式", required = true)
                                         @RequestParam(value = "markId") String markId) {
        List<Integer> itemIdList = userItemServive.selectByUserId();
        ItemDTO itemDTO = itemService.selectByMarkId(markId, itemIdList);
        jwtConfig.removeThread();
        if (itemDTO != null) {
            return HttpResult.success(itemDTO);
        } else {
            throw new ItemNotExistsException("项目id错误，该项目查找为空");
        }
    }

    @ApiOperation(value = "查找项目成员", notes = "(查看该项目下的所有项目成员)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 20007, message = "项目用户成员查找失败")})
    @GetMapping("/selectUserListByItemId")
    public HttpResult selectUserListByItemId(@ApiParam(name = "项目id", value = "传入Json格式", required = true)
                                             @RequestParam(value = "itemId") Integer itemId) {
        List<Integer> userIdList = userItemServive.selectByItemId(itemId);
        List<UserDTO> userList = userService.selectUserList(userIdList);
        if (userList != null && !userList.isEmpty()) {
            return HttpResult.success(userList);
        } else {
            throw new ItemFindUserListException("项目用户成员查找失败");
        }
    }
}
