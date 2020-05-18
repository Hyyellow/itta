package com.program.itta.service;


import com.program.itta.domain.entity.UserItem;

import java.util.List;

public interface UserItemServive {
    // 增加用户项目关系——创建项目
    Boolean addUserItem(String itemName);

    // 增加用户项目关系——增加用户
    Boolean addItemMember(Integer itemId);

    // 增加用户项目关系——增加用户
    Boolean addItemMember(Integer userId,Integer itemId);

    // 删除用户项目关系
    Boolean deleteUserItem(Integer itemId);

    // 查找用户下的所有项目
    List<Integer> selectByUserId();

    // 查找项目下的所有用户
    List<Integer> selectByItemId(Integer itemId);
}
