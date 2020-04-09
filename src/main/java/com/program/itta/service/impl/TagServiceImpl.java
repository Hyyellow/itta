package com.program.itta.service.impl;

import com.program.itta.domain.entity.Tag;
import com.program.itta.mapper.TagMapper;
import com.program.itta.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: itta
 * @description: 标签Service
 * @author: Mr. Boyle
 * @create: 2020-04-08 20:34
 **/
@Service
public class TagServiceImpl implements TagService {
    @Autowired

    private TagMapper tagMapper;
    /**
     * 添加标签
     * @param tag
     * @return
     */
    @Override
    public Boolean addTag(Tag tag) {
        int insert = tagMapper.insert(tag);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteTag(Tag tag) {
        return null;
    }

    @Override
    public Boolean updateTag(Tag tag) {
        return null;
    }

    @Override
    public Boolean judgeTag(Tag tag) {
        return null;
    }
}
