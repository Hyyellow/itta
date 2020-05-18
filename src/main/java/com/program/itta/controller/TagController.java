package com.program.itta.controller;

import com.program.itta.common.exception.tag.TagAddFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.TagDTO;
import com.program.itta.domain.entity.Tag;
import com.program.itta.mapper.TaskTagMapper;
import com.program.itta.service.TagService;
import com.program.itta.service.TaskTagService;
import com.program.itta.service.UserTagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: itta
 * @description: 标签API
 * @author: Mr. Boyle
 * @create: 2020-04-08 20:49
 **/
@Api(tags = "标签接口")
@RestController
@RequestMapping("/tag")
public class TagController {
    /*TODO
      1.标签的添加
      2.标签的删除
      3.标签的查找
      4.标签的更新
      5.标签与用户中间表相关
        (1).添加
        (2).删除
     */
    @Autowired
    private TagService tagService;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private TaskTagService taskTagService;

    @ApiOperation(value = "添加标签", notes = "(添加此标签，当任务中添加标签时使用)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 50001, message = "标签添加失败")})
    @PostMapping("/addTag")
    public HttpResult addTag(@ApiParam(name = "任务id", value = "传入Json格式", required = true)
                             @RequestParam(value = "taskId") Integer taskId,
                             @ApiParam(name = "标签内容", value = "传入Json格式", required = true)
                             @RequestParam(value = "content") String content) {
        Tag tag = Tag.builder().content(content).build();
        Boolean addTag = tagService.addTag(tag);
        Boolean addUserTag = userTagService.addUserTag(content);
        Boolean addTaskTag = taskTagService.addTaskTag(taskId, content);
        if (addTag && addTaskTag && addUserTag) {
            return HttpResult.success();
        } else {
            throw new TagAddFailException("添加任务标签失败");
        }
    }

    @ApiOperation(value = "查找任务标签", notes = "(查看该任务的所有标签)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该任务尚无标签")})
    @GetMapping("/selectTaskTag")
    public HttpResult selectTaskTag(@ApiParam(name = "任务id", value = "传入Json格式", required = true)
                                    @RequestParam(value = "taskId") Integer taskId) {
        List<Integer> tagIdList = taskTagService.selectByTaskId(taskId);
        List<TagDTO> tagDTOList = tagService.selectTagList(tagIdList);
        if (tagDTOList != null && !tagDTOList.isEmpty()) {
            return HttpResult.success(tagDTOList);
        } else {
            return HttpResult.success("该任务尚无标签");
        }
    }

    @ApiOperation(value = "查找用户常用标签", notes = "(查看该用户常用的前3个标签)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该任务尚无标签")})
    @GetMapping("/selectUserTag")
    public HttpResult selectUserTag() {
        List<Integer> tagIdList = userTagService.selectThreeTag();
        List<TagDTO> tagDTOList = tagService.selectTagList(tagIdList);
        if (tagDTOList != null && !tagDTOList.isEmpty()) {
            return HttpResult.success(tagDTOList);
        } else {
            return HttpResult.success("该用户尚无常用标签");
        }
    }
}
