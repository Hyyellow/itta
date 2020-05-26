package com.program.itta.service.impl;

import com.program.itta.domain.dto.TagDTO;
import com.program.itta.domain.entity.Tag;
import com.program.itta.mapper.TagMapper;
import com.program.itta.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     *
     * @param tag
     * @return
     */
    @Override
    public Boolean addTag(Tag tag) {
        Boolean judgeTag = judgeTag(tag.getContent());
        if (judgeTag) {
            return true;
        }
        int insert = tagMapper.insert(tag);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<TagDTO> selectTagList(List<Integer> tagIdList) {
        List<TagDTO> tagDTOList = new ArrayList<>();
        for (Integer tagId : tagIdList) {
            Tag tag = tagMapper.selectByPrimaryKey(tagId);
            TagDTO tagDTO = new TagDTO();
            tagDTO = tagDTO.convertFor(tag);
            tagDTOList.add(tagDTO);
        }
        return tagDTOList;
    }

    private Boolean judgeTag(String content) {
        Tag tag = tagMapper.selectByContent(content);
        if (tag != null) {
            return true;
        }
        return null;
    }
}
