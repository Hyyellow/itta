package com.program.itta.service.impl;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.Timer;
import com.program.itta.mapper.NewsMapper;
import com.program.itta.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Access;
import java.util.List;

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

    @Resource
    private JwtConfig jwtConfig;

    @Override
    public Boolean deleteNews(News news) {
        int delete = newsMapper.deleteByPrimaryKey(news.getId());
        if (delete != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<News> selectNewsList() {
        Integer userId = jwtConfig.getUserId();
        List<News> newsList = newsMapper.selectByUserId(userId);
        if (newsList != null && !newsList.isEmpty()) {
            return newsList;
        }
        return null;
    }

    @Override
    public Void insertTaskNews(Task task,Integer userId) {
        News news = News.builder()
                .senderId(userId)
                .isUser(true)
                .recipientId(task.getUserId())
                .content("edit")
                .build();
        newsMapper.insert(news);
        return null;
    }

    @Override
    public Void insertScheduleNews(Timer timer) {
        News news = News.builder()
                .senderId(0)
                .isUser(false)
                .recipientId(timer.getScheduleId())
                .content("todo")
                .build();
        newsMapper.insert(news);
        return null;
    }
}
