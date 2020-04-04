package com.program.itta.mapper;

import com.program.itta.entity.UserItem;
import java.util.List;

public interface UserItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserItem record);

    UserItem selectByPrimaryKey(Integer id);

    List<UserItem> selectAll();

    int updateByPrimaryKey(UserItem record);
}