package com.program.itta.service.impl;

import com.program.itta.domain.entity.Item;
import com.program.itta.mapper.ItemMapper;
import com.program.itta.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: itta
 * @description: 项目Service
 * @author: Mr.Huang
 * @create: 2020-04-08 09:01
 **/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 添加项目
     * @param item
     * @return
     */
    @Override
    public Boolean addItem(Item item) {
        int insert = itemMapper.insert(item);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除项目
     * @param item
     * @return
     */
    @Override
    public Boolean deleteItem(Item item) {
        int delete = itemMapper.deleteByPrimaryKey(item.getId());
        if (delete != 0) {
            return true;
        }
        return false;
    }

    /**
     * 更新项目
     * @param item
     * @return
     */
    @Override
    public Boolean updateItem(Item item) {
        item.setUpdateTime(new Date());
        int update = itemMapper.updateByPrimaryKey(item);
        if (update != 0) {
            return true;
        }
        return false;
    }

/*    *//**
     * 判断项目是否存在
     * @param item
     * @return
     *//*
    @Override
    public Boolean judgeItem(Item item) {
        List<Item> itemList = itemMapper.selectAll();
        for (Item item1 : itemList) {
            if (item1.get().equalsIgnoreCase(user.getWxOpenid())) {
                return true;
            }
        }
        return false;
    }*/
}
