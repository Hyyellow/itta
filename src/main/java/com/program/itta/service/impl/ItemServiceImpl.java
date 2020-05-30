package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.ItemNameExistsException;
import com.program.itta.common.exception.item.ItemNotExistsException;
import com.program.itta.common.exception.item.ItemNotPermissFindException;
import com.program.itta.common.exception.permissions.NotItemLeaderException;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.mapper.ItemMapper;
import com.program.itta.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @program: itta
 * @description: Service Item实现类
 * @author: Mr.Huang
 * @create: 2020-04-22 19:45
 **/
@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemMapper itemMapper;

    @Resource
    private JwtConfig jwtConfig;

    /**
     * 添加项目
     *
     * @param item
     * @return
     */
    @Override
    public Boolean addItem(Item item) {
        Integer userId = jwtConfig.getUserId();
        String markId = UUID.randomUUID().toString();
        item.setUserId(userId);
        item.setMarkId(markId);
        judgeItemNameExists(item);
        int insert = itemMapper.insert(item);
        if (insert != 0) {
            logger.info("用户" + userId + "添加项目：" + item.getName());
            return true;
        }
        return false;
    }

    /**
     * 删除项目
     *
     * @param item
     * @return
     */
    @Override
    public Boolean deleteItem(Item item) {
        Integer userId = jwtConfig.getUserId();
        judgeItemNotExists(item);
        judgeDeletePermissions(userId, item);
        int delete = itemMapper.deleteByPrimaryKey(item.getId());
        if (delete != 0) {
            logger.info("删除项目：" + item.getName() + "项目id为: " + item.getId());
            return true;
        }
        return false;
    }

    /**
     * 更新项目
     *
     * @param item
     * @return
     */
    @Override
    public Boolean updateItem(Item item) {
        judgeItemNotExists(item);
        judgeItemNameExists(item);
        item.setUpdateTime(new Date());
        int update = itemMapper.updateByPrimaryKey(item);
        if (update != 0) {
            logger.info("项目：" + item.getId() + "更新项目信息为：" + item);
            return true;
        }
        return false;
    }

    @Override
    public List<ItemDTO> selectItemList(List<Integer> itemIdList) {
        List<ItemDTO> itemList = new ArrayList<>();
        for (Integer integer : itemIdList) {
            Item item = itemMapper.selectByPrimaryKey(integer);
            ItemDTO itemDTO = new ItemDTO();
            itemDTO = itemDTO.convertFor(item);
            itemList.add(itemDTO);
        }
        return itemList;
    }

    @Override
    public Item selectByItemId(Integer id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public ItemDTO selectByMarkId(String markId, List<Integer> itemIds) {
        Item item = itemMapper.selectByMarkId(markId);
        if (item != null) {
            Item itemFind = judgeItemFind(item, itemIds);
            ItemDTO itemDTO = new ItemDTO();
            itemDTO = itemDTO.convertFor(itemFind);
            return itemDTO;
        }
        return null;
    }

    private void judgeItemNotExists(Item item) {
        boolean judgeItemExists = false;
        Item select = itemMapper.selectByPrimaryKey(item.getId());
        if (select != null) {
            judgeItemExists = true;
        }
        if (!judgeItemExists) {
            throw new ItemNotExistsException("该项目不存在，项目id查找为空");
        }
    }

    private void judgeItemNameExists(Item item) {
        boolean judgeItem = false;
        List<Item> items = itemMapper.selectByUserId(item.getUserId());
        for (Item item1 : items) {
            if (item1.getName().equals(item.getName())) {
                judgeItem = true;
            }
        }
        if (judgeItem) {
            throw new ItemNameExistsException("项目名称已存在");
        }
    }

    private Item judgeItemFind(Item item, List<Integer> itemIds) {
        if (item.getActionScope().equals(1)) {
            return item;
        } else {
            if (itemIds != null && !itemIds.isEmpty()) {
                for (Integer itemId : itemIds) {
                    if (itemId.equals(item.getId())) {
                        return item;
                    }
                }
            }
            throw new ItemNotPermissFindException("该项目只有项目成员可访问");
        }
    }

    private void judgeDeletePermissions(Integer userId, Item item) {
        boolean leaderPermissions = false;
        if (item.getUserId().equals(userId)) {
            leaderPermissions = true;
        }
        if (!leaderPermissions) {
            throw new NotItemLeaderException("无该项目负责人的权限");
        }
    }
}
