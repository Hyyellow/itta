package com.program.itta.service;


import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Task;

import java.util.List;

public interface NewsService {
    Boolean deleteNews(News news);

    List<News> selectNewsList();

    Void insertTaskNews(Task task, Integer userId);
}
