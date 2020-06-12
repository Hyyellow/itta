package com.program.itta.controller;

import com.program.itta.common.annotation.RequestLog;
import com.program.itta.common.config.JwtConfig;
import com.program.itta.common.exception.item.ItemDelFailException;
import com.program.itta.common.exception.news.NewsDelFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.common.valid.Delete;
import com.program.itta.common.valid.Update;
import com.program.itta.domain.dto.ItemDTO;
import com.program.itta.domain.dto.NewsDTO;
import com.program.itta.domain.entity.Item;
import com.program.itta.domain.entity.News;
import com.program.itta.domain.entity.Task;
import com.program.itta.service.NewsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Api(tags = "消息接口")
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Resource
    private JwtConfig jwtConfig;

    @RequestLog(module = "消息模块", operationDesc = "删除消息")
    @ApiOperation(value = "删除消息", notes = "(删除该条消息)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 60001, message = "消息删除失败")})
    @DeleteMapping("/deleteNews")
    public HttpResult deleteItem(@RequestBody
                                 @Validated(Delete.class) NewsDTO newsDTO) {
        News news = newsDTO.convertToNews();
        Boolean deleteNews = newsService.deleteNews(news);
        if (!deleteNews) {
            throw new NewsDelFailException("删除失败");
        }
        return HttpResult.success();
    }

    @RequestLog(module = "消息模块", operationDesc = "查找消息")
    @ApiOperation(value = "查找消息", notes = "(获取该用户的所有消息)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该用户无消息存在")})
    @GetMapping("/selectNews")
    public HttpResult selectNews() {
        List<NewsDTO> newsList = newsService.selectNewsList();
        jwtConfig.removeThread();
        if (newsList != null && !newsList.isEmpty()) {
            return HttpResult.success(newsList);
        } else {
            return HttpResult.success("该用户尚无消息存在");
        }
    }
}
