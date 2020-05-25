package com.program.itta.service;


import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.Timer;

import java.util.List;

public interface NewsService {
    Boolean deleteNews(News news);

    List<News> selectNewsList();

    Void addTaskNews(Task task, Integer userId);

    Void addScheduleNews(Timer timer);
}