package com.program.itta.controller;

import com.program.itta.common.result.HttpResult;
import com.program.itta.common.util.COSClientUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.program.itta.common.result.ResultCodeEnum.SERVER_ERROR;

/**
 * @program: itta
 * @description:
 * @author: Mr.Huang
 * @create: 2020-04-22 11:52
 **/
@RestController
@RequestMapping("/upload")
public class UploadController {
    @PostMapping("/upload")
    public HttpResult uploadGoodsPic(@RequestParam(value = "file") MultipartFile file) {
        COSClientUtil cosClientUtil = new COSClientUtil();
        String url = cosClientUtil.uploadGoodsPic(file);
        if (url != null) {
            return HttpResult.success(url);
        } else {
            return HttpResult.failure(SERVER_ERROR);
        }
    }
}
