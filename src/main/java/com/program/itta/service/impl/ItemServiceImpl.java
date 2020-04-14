package com.program.itta.service.impl;

import com.program.itta.common.exception.GlobalExceptionHandler;
import com.program.itta.domain.entity.Item;
import com.program.itta.mapper.ItemMapper;
import com.program.itta.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: itta
 * @description: 项目Service
 * @author: Mr.Huang
 * @create: 2020-04-08 09:01
 **/
@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 添加项目
     *
     * @param item
     * @return
     */
    @Override
    public Boolean addItem(Item item) {
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
        item.setUpdateTime(new Date());
        int update = itemMapper.updateByPrimaryKey(item);
        if (update != 0) {
            logger.info("项目:" + item.getId() + "更新项目信息：" + item);
            return true;
        }
        return false;
    }

    @Override
    public Boolean judgeItem(Item item) {
        List<Item> items = itemMapper.selectAllItemByUserId(item.getUserId());
        for (Item item1 : items) {
            if (item1.getName().equals(item.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Item> selectAllItem(List<Integer> itemIdList) {
        List<Item> itemList = null;
        for (int i = 0; i < itemIdList.size(); i++){
            Item item = itemMapper.selectByPrimaryKey(itemIdList.get(i));
            itemList.add(item);
        }
        return itemList;
    }
}
