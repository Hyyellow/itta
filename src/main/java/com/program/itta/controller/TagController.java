package com.program.itta.controller;

import com.program.itta.common.annotation.RequestLog;
import com.program.itta.common.exception.tag.TagAddFailException;
import com.program.itta.common.exception.tag.TagDelFailException;
import com.program.itta.common.result.HttpResult;
import com.program.itta.domain.dto.TagDTO;
import com.program.itta.domain.dto.TimerDTO;
import com.program.itta.domain.entity.ScheduleTag;
import com.program.itta.domain.entity.Tag;
import com.program.itta.domain.entity.TaskTag;
import com.program.itta.mapper.TaskTagMapper;
import com.program.itta.service.ScheduleTagService;
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

    @Autowired
    private TagService tagService;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private TaskTagService taskTagService;

    @Autowired
    private ScheduleTagService scheduleTagService;

    @RequestLog(module = "标签模块",operationDesc = "添加任务标签")
    @ApiOperation(value = "添加任务标签", notes = "(添加此标签，当任务已经创建后添加标签时使用)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 50001, message = "标签添加失败")})
    @PostMapping("/addTaskTag")
    public HttpResult addTaskTag(@ApiParam(name = "任务id", value = "传入Json格式", required = true)
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

    @RequestLog(module = "标签模块",operationDesc = "删除任务标签")
    @ApiOperation(value = "删除任务标签", notes = "(删除此任务标签)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 50002, message = "标签删除失败")})
    @PostMapping("/deleteTaskTag")
    public HttpResult deleteTaskTag(@ApiParam(name = "任务id", value = "传入Json格式", required = true)
                                    @RequestParam(value = "taskId") Integer taskId,
                                    @ApiParam(name = "标签id", value = "传入Json格式", required = true)
                                    @RequestParam(value = "tagId") Integer tagId) {
        TaskTag taskTag = TaskTag.builder().taskId(taskId).tagId(tagId).build();
        Boolean deleteTaskTag = taskTagService.deleteTaskTag(taskTag);
        if (!deleteTaskTag) {
            throw new TagDelFailException("删除失败");
        }
        return HttpResult.success();
    }

    @RequestLog(module = "标签模块",operationDesc = "查找任务标签")
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

    @RequestLog(module = "标签模块",operationDesc = "添加日程标签")
    @ApiOperation(value = "添加日程标签", notes = "(添加此标签，当日程已经创建后添加标签时使用)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 50001, message = "标签添加失败")})
    @PostMapping("/addScheduleTag")
    public HttpResult addScheduleTag(@ApiParam(name = "日程id", value = "传入Json格式", required = true)
                                     @RequestParam(value = "scheduleId") Integer scheduleId,
                                     @ApiParam(name = "标签内容", value = "传入Json格式", required = true)
                                     @RequestParam(value = "content") String content) {
        Tag tag = Tag.builder().content(content).build();
        Boolean addTag = tagService.addTag(tag);
        Boolean addUserTag = userTagService.addUserTag(content);
        Boolean addScheduleTag = scheduleTagService.addScheduleTag(scheduleId, content);
        if (addTag && addScheduleTag && addUserTag) {
            return HttpResult.success();
        } else {
            throw new TagAddFailException("添加日程标签失败");
        }
    }

    @RequestLog(module = "标签模块",operationDesc = "删除日程标签")
    @ApiOperation(value = "删除日程标签", notes = "(删除此日程标签)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 50002, message = "标签删除失败")})
    @PostMapping("/deleteScheduleTag")
    public HttpResult deleteScheduleTag(@ApiParam(name = "日程id", value = "传入Json格式", required = true)
                                        @RequestParam(value = "scheduleId") Integer scheduleId,
                                        @ApiParam(name = "标签id", value = "传入Json格式", required = true)
                                        @RequestParam(value = "tagId") Integer tagId) {
        ScheduleTag scheduleTag = ScheduleTag.builder().scheduleId(scheduleId).tagId(tagId).build();
        Boolean deleteScheduleTag = scheduleTagService.deleteScheduleTag(scheduleTag);
        if (!deleteScheduleTag) {
            throw new TagDelFailException("删除失败");
        }
        return HttpResult.success();
    }

    @RequestLog(module = "标签模块",operationDesc = "查找日程标签")
    @ApiOperation(value = "查找日程标签", notes = "(查看该日程的所有标签)")
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功"), @ApiResponse(code = 200, message = "该日程尚无标签")})
    @GetMapping("/selectScheduleTag")
    public HttpResult selectScheduleTag(@ApiParam(name = "日程id", value = "传入Json格式", required = true)
                                        @RequestParam(value = "scheduleId") Integer scheduleId) {
        List<Integer> tagIdList = scheduleTagService.selectByScheduleId(scheduleId);
        List<TagDTO> tagDTOList = tagService.selectTagList(tagIdList);
        if (tagDTOList != null && !tagDTOList.isEmpty()) {
            return HttpResult.success(tagDTOList);
        } else {
            return HttpResult.success("该日程尚无标签");
        }
    }

    @RequestLog(module = "标签模块",operationDesc = "查找用户常用标签")
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
