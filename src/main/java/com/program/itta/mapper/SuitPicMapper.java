package com.program.itta.mapper;

import com.program.itta.domain.entity.SuitPic;
import java.util.List;

public interface SuitPicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SuitPic record);

    SuitPic selectByPrimaryKey(Integer id);

    List<SuitPic> selectAll();

    int updateByPrimaryKey(SuitPic record);
}