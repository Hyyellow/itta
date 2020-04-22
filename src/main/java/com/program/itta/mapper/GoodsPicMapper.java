package com.program.itta.mapper;

import com.program.itta.domain.entity.GoodsPic;
import java.util.List;

public interface GoodsPicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsPic record);

    GoodsPic selectByPrimaryKey(Integer id);

    List<GoodsPic> selectAll();

    int updateByPrimaryKey(GoodsPic record);
}