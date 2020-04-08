package com.program.itta.mapper;

import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.UserItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserItem record);

    UserItem selectByPrimaryKey(Integer id);

    List<UserItem> selectAll();

    int updateByPrimaryKey(UserItem record);

    List<UserItem> selectItemByUserId(Integer userId);
}