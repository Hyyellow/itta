package com.program.itta.service.impl;

import com.program.itta.domain.entity.News;
import com.program.itta.mapper.NewsMapper;
import com.program.itta.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;

/**
 * @program: itta
 * @description: Service News实现类
 * @author: Mr.Huang
 * @create: 2020-05-18 16:39
 **/
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Boolean deleteNews(News news) {
        int delete = newsMapper.deleteByPrimaryKey(news.getId());
        if (delete != 0){
            return true;
        }
        return false;
    }
}
