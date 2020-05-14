package com.program.itta.service.impl;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.domain.entity.Tag;
import com.program.itta.domain.entity.UserTag;
import com.program.itta.mapper.TagMapper;
import com.program.itta.mapper.UserTagMapper;
import com.program.itta.service.UserTagService;
import com.program.itta.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Collections.*;

/**
 * @program: itta
 * @description: 用户标签Service
 * @author: Mr.Huang
 * @create: 2020-05-13 21:51
 **/
@Service
public class UserTagServiceImpl implements UserTagService {
    @Autowired
    private UserTagMapper userTagMapper;

    @Resource
    private JwtConfig jwtConfig;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Boolean addUserTag(String content) {
        Integer userId = jwtConfig.getUserId();
        Tag tag = tagMapper.selectByContent(content);
        UserTag userTag = UserTag.builder()
                .userId(userId)
                .tagId(tag.getId())
                .build();
        Boolean judgeUserTag = judgeUserTag(userTag);
        if (judgeUserTag) {
            return true;
        }
        int insert = userTagMapper.insert(userTag);
        if (insert != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> selectThreeTag() {
        Integer userId = jwtConfig.getUserId();
        List<UserTag> userTags = new ArrayList<>();
        List<UserTag> userTagList = userTagMapper.selectAllTag(userId);
        Collections.sort(userTagList);
        userTags.addAll(userTagList.subList(0,3));
        List<Integer> tagIds = userTags.stream()
                .map(UserTag -> UserTag.getTagId())
                .collect(Collectors.toList());
        return tagIds;
    }

    private Boolean judgeUserTag(UserTag userTag) {
        UserTag selectByUserTag = userTagMapper.selectByUserTag(userTag);
        if (selectByUserTag != null) {
            selectByUserTag.setNumber(selectByUserTag.getNumber() + 1);
            userTagMapper.updateByPrimaryKey(selectByUserTag);
            return true;
        }
        return false;
    }
}
