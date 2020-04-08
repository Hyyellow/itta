package com.program.itta.mapper;

import com.program.itta.domain.entity.UserSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserSchedule record);

    UserSchedule selectByPrimaryKey(Integer id);

    List<UserSchedule> selectAll();

    int updateByPrimaryKey(UserSchedule record);
}