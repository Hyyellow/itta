package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.UserItem;
import com.program.itta.mapper.ItemMapper;
import com.program.itta.mapper.UserItemMapper;
import com.program.itta.service.UserItemServive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: itta
 * @description: UserItemService 实现类
 * @author: Mr.Huang
 * @create: 2020-04-11 16:57
 **/
@Service
public class UserItemServiceImpl implements UserItemServive {

    private static final Logger logger = LoggerFactory.getLogger(UserItemServiceImpl.class);

    @Autowired
    private UserItemMapper userItemMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public Boolean addUserItem(String itemName) {
        Integer userId = jwtConfig.getUserId();
        List<Item> items = itemMapper.selectAllItemByUserId(userId);
        Integer itemId = selectItemIdByName(itemName,items).getId();
        UserItem userItem = UserItem.builder()
                .userId(userId)
                .itemId(itemId)
                .build();
        int insert = userItemMapper.insert(userItem);
        if (insert != 0) {
            logger.info("用户：" + userItem.getUserId() + "添加项目：" + userItem.getItemId());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUserItem(Integer itemId) {
        int delete = 0;
        Integer userId = jwtConfig.getUserId();
        List<UserItem> userItemList = userItemMapper.selectAllItem(userId);
        for (UserItem userItem : userItemList) {
            if (userItem.getItemId().equals(itemId)) {
                logger.info("用户：" + userItem.getUserId() + "删除项目：" + userItem.getItemId());
                // 负责人删除
                if (userItem.getIsLeader()) {
                    deleteUserItemList(itemId);
                    delete = itemMapper.deleteByPrimaryKey(itemId);
                } else {
                    // 正常删除
                    delete = userItemMapper.deleteByPrimaryKey(userItem.getId());
                }
            }
        }
        if (delete != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> selectAllItem() {
        Integer userId = jwtConfig.getUserId();
        List<UserItem> userItemList = userItemMapper.selectAllItem(userId);
        List<Integer> itemIds = userItemList.stream()
                .map(UserItem -> UserItem.getItemId())
                .collect(Collectors.toList());
        if (itemIds != null && !itemIds.isEmpty()) {
            return itemIds;
        }
        return null;
    }

    // 删除用户项目队列
    private void deleteUserItemList(Integer itemId) {
        List<UserItem> userItemList = userItemMapper.selectAllItemByItemId(itemId);
        for (UserItem userItem : userItemList) {
            userItemMapper.deleteByPrimaryKey(userItem.getId());
        }
    }

    // 根据项目名称查找项目
    private Item selectItemIdByName(String itemName, List<Item> items) {
        for (Item item : items) {
            if (itemName.equals(item.getName())) {
                return item;
            }
        }
        return null;
    }
}
