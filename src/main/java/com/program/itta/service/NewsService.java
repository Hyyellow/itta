package com.program.itta.service;


import com.program.itta.domain.dto.NewsDTO;
import com.program.itta.domain.dto.ScheduleDTO;
import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Schedule;
import com.program.itta.domain.entity.Task;
import com.program.itta.domain.entity.Timer;

import java.util.List;

public interface NewsService {
    Boolean deleteNews(News news);

    List<NewsDTO> selectNewsList();

    Void addTaskNews(Task task, Integer userId);

    Void addScheduleNews(ScheduleDTO schedule);
}
