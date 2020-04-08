package com.program.itta.service.impl;
import	java.util.stream.Collectors;

import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.UserItem;
import com.program.itta.mapper.UserItemMapper;
import com.program.itta.service.UserItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: itta
 * @description: 用户项目中间表
 * @author: Mr.Huang
 * @create: 2020-04-08 14:16
 **/
@Service
public class UserItemServiceImpl implements UserItemService {
    @Autowired
    private UserItemMapper userItemMapper;

    @Override
    public Boolean addUserItem(UserItem userItem) {
        int insert = userItemMapper.insert(userItem);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUserItem(UserItem userItem) {
        int delete = userItemMapper.deleteByPrimaryKey(userItem.getId());
        if (delete != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> selectItemByUserId(Integer userId) {
        List<UserItem> userItemList = userItemMapper.selectItemByUserId(userId);
        if (userItemList != null && !userItemList.isEmpty()) {
            List<Integer> itemList = userItemList.stream().
                    map(userItem -> userItem.getItemId()).
                    collect(Collectors.toList());
            return itemList;
        }
        return null;
    }
}
