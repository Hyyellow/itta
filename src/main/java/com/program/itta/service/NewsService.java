package com.program.itta.service;


import com.program.itta.domain.entity.News;

import java.util.List;

public interface NewsService {
    Boolean deleteNews(News news);

    List<News> selectNewsList();
}
