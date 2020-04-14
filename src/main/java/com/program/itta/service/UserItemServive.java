package com.program.itta.service;


import com.program.itta.domain.entity.UserItem;

import java.util.List;

public interface UserItemServive {
    // 增加用户项目关系
    Boolean addUserItem(String itemName);
    // 删除用户项目关系
    Boolean deleteUserItem(Integer itemId);
    // 查找用户下的所有项目
    List<Integer> selectAllItem();
}
