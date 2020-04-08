package com.program.itta.service;

import com.program.itta.domain.entity.Item;

public interface ItemService {
/*    // 添加用户
    Boolean addUser(User user);
    // 更新用户
    Boolean updateUser(User user);
    // 删除用户(尚完善)
    Boolean deleteUser(User user);
    // 判断用户是否存在
    Boolean judgeUser(User user);
    // 查找模块——后期可考虑ES
    User selectUser(Integer userId);*/
    // 添加项目
    Boolean addItem(Item item);
    // 删除项目
    Boolean deleteItem(Item item);
    // 更新项目
    Boolean updateItem(Item item);
/*    // 判断项目是否存在
    Boolean judgeItem(Item item);*/
}
