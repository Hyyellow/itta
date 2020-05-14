package com.program.itta.service;

import java.util.List;

public interface UserTagService {
    // 添加用户标签中间关系
    Boolean addUserTag(String content);

    // 查看该用户使用靠前3的标签
    List<Integer> selectThreeTag();
}
