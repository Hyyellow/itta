package com.program.itta.mapper;

import com.program.itta.domain.entity.UserTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTag record);

    UserTag selectByPrimaryKey(Integer id);

    List<UserTag> selectAll();

    int updateByPrimaryKey(UserTag record);

    UserTag selectByUserTag(UserTag record);
}