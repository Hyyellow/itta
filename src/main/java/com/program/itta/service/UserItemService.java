package com.program.itta.service;

import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.entity.UserItem;

import java.util.List;

public interface UserItemService {
    // 添加项目用户中间表
    Boolean addUserItem(UserItem userItem);

    // 删除项目用户中间表
    Boolean deleteUserItem(UserItem userItem);

    // 根据用户id查找所有项目Id
    List<Integer> selectItemByUserId(Integer userId);
}
