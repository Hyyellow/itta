package com.program.itta.controller;

import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.ItemDelFailException;
import com.program.itta.common.exception.news.NewsDelFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.dto.NewsDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Task;
import com.program.itta.service.NewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: itta
 * @description: 消息API
 * @author: Mr.Huang
 * @create: 2020-05-18 16:35
 **/
@RestController
@RequestMapping("/news")
public class NewsController {
    /*
     * ~~1. 消息的添加~~
     * 1. 消息的删除
     * 2. 消息的查看
     * */

    @Autowired
    private NewsService newsService;

    @Resource
    private JwtConfig jwtConfig;

    @DeleteMapping("/deleteNews")
    public HttpResult deleteItem(@RequestBody @Valid NewsDTO newsDTO) {
        News news = newsDTO.convertToNews();
        Boolean deleteNews = newsService.deleteNews(news);
        if (!deleteNews) {
            throw new NewsDelFailException("删除失败");
        }
        return HttpResult.success();
    }

    @GetMapping("/selectNews")
    public HttpResult selectNews() {
        List<News> newsList = newsService.selectNewsList();
        jwtConfig.removeThread();
        if (newsList != null && !newsList.isEmpty()) {
            return HttpResult.success(newsList);
        } else {
            return HttpResult.success("该用户尚无消息存在");
        }
    }
}
