package com.program.itta.service;


import com.program.itta.domain.dto.TagDTO;
import com.program.itta.domain.entity.Tag;

import java.util.List;

public interface TagService {
    // 添加标签
    Boolean addTag(Tag tag);

    // 查找标签列表
    List<TagDTO> selectTagList(List<Integer> tagIdList);
}
