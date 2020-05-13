package com.program.itta.service;

import com.program.itta.domain.entity.Item;

import java.util.List;

public interface ItemService {
    // 添加项目
    Boolean addItem(Item item);

    // 删除项目
    Boolean deleteItem(Item item);

    // 更新项目
    Boolean updateItem(Item item);

    // 查找该用户的所有项目
    List<Item> selectAllItem(List<Integer> itemIdList);

    // 根据项目id查找项目
    Item selectById(Integer id);

    // 根据项目的标志id查找项目
    Item selectByMarkId(String markId,List<Integer> itemIds);
}
