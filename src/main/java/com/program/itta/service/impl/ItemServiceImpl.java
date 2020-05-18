package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.ItemNameExistsException;
import com.program.itta.common.exception.item.ItemNotExistsException;
import com.program.itta.common.exception.item.ItemNotPermissFindException;
import com.program.itta.common.exception.permissions.NotItemLeaderException;
import com.program.itta.common.util.fineGrainedPermissions.ItemPermissionsUtil;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.mapper.ItemMapper;
import com.program.itta.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    private ItemPermissionsUtil itemPermissionsUtil;

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
        item.setUserId(userId);
        Boolean judgeItem = judgeItemName(item);
        if (judgeItem) {
            throw new ItemNameExistsException("项目名称已存在");
        }
        int insert = itemMapper.insert(item);
        if (insert != 0) {
            logger.info("添加项目：" + item.getName());
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
        Boolean judgeItemExists = judgeItemExists(item);
        if (!judgeItemExists) {
            throw new ItemNotExistsException("该项目不存在，项目id查找为空");
        }
        Boolean leaderPermissions = itemPermissionsUtil.DeletePermissions(userId, item);
        if (!leaderPermissions) {
            throw new NotItemLeaderException("无该项目负责人的权限");
        }
        int delete = itemMapper.deleteByPrimaryKey(item.getId());
        if (delete != 0) {
            logger.info("删除项目：" + item.getName() + "项目id为:" + item.getId());
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
        Boolean judgeItemExists = judgeItemExists(item);
        if (!judgeItemExists) {
            throw new ItemNotExistsException("该项目不存在，项目id查找为空");
        }
        item.setUpdateTime(new Date());
        int update = itemMapper.updateByPrimaryKey(item);
        if (update != 0) {
            logger.info("项目：" + item.getId() + "更新项目信息为：" + item);
            return true;
        }
        return false;
    }

    @Override
    public List<Item> selectAllItem(List<Integer> itemIdList) {
        List<Item> itemList = null;
        for (int i = 0; i < itemIdList.size(); i++) {
            Item item = itemMapper.selectByPrimaryKey(itemIdList.get(i));
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public Item selectById(Integer id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item != null) {
            return item;
        }
        return null;
    }

    @Override
    public ItemDTO selectByMarkId(String markId, List<Integer> itemIds) {
        Item item = itemMapper.selectByMarkId(markId);
        if (item != null) {
            Item itemFind = judgeItemFind(item, itemIds);
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.convertFor(itemFind);
            return itemDTO;
        }
        return null;
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

    private Boolean judgeItemName(Item item) {
        List<Item> items = itemMapper.selectAllByUserId(item.getUserId());
        for (Item item1 : items) {
            if (item1.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    private Boolean judgeItemExists(Item item) {
        Item select = itemMapper.selectByPrimaryKey(item.getId());
        if (select != null) {
            return true;
        }
        return false;
    }
}
