package com.program.itta.service.impl;
import	java.util.ArrayList;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.domain.dto.NewsDTO;
import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Schedule;
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
    public List<NewsDTO> selectNewsList() {
        Integer userId = jwtConfig.getUserId();
        List<News> newsList = newsMapper.selectByUserId(userId);
        if (newsList != null && !newsList.isEmpty()) {
            List<NewsDTO> newsDTOList = convertToNewsDTOList(newsList);
            return newsDTOList;
        }
        return null;
    }

    @Override
    public Void addTaskNews(Task task,Integer userId) {
        News news = News.builder()
                .senderId(userId)
                .whetherUser(true)
                .recipientId(task.getUserId())
                .content("edit")
                .build();
        newsMapper.insert(news);
        return null;
    }

    @Override
    public Void addScheduleNews(Schedule schedule) {
        News news = News.builder()
                .senderId(0)
                .whetherUser(false)
                .recipientId(schedule.getId())
                .content("todo")
                .build();
        newsMapper.insert(news);
        return null;
    }

    private List<NewsDTO> convertToNewsDTOList(List<News> newsList){
        List<NewsDTO> newsDTOList = new ArrayList<> ();
        for (News news : newsList){
            NewsDTO newsDTO = new NewsDTO();
            newsDTO = newsDTO.convertFor(news);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }
}
