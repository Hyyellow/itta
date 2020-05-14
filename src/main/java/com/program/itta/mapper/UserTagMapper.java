package com.program.itta.mapper;

import com.program.itta.domain.entity.UserTag;
import java.util.List;

public interface UserTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTag record);

    UserTag selectByPrimaryKey(Integer id);

    List<UserTag> selectAll();

    int updateByPrimaryKey(UserTag record);

    UserTag selectByUserTag(UserTag record);
}