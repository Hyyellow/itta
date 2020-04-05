package com.program.itta.mapper;

import com.program.itta.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    Item selectByPrimaryKey(Integer id);

    List<Item> selectAll();

    int updateByPrimaryKey(Item record);
}