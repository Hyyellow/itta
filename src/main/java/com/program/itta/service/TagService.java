package com.program.itta.service;


import com.program.itta.domain.entity.Tag;

public interface TagService {
    // 添加标签
    Boolean addTag(Tag tag);
    // 删除标签
    Boolean deleteTag(Tag tag);
    // 更新标签
    Boolean updateTag(Tag tag);
    // 判断标签是否存在
    Boolean judgeTag(Tag tag);
}
