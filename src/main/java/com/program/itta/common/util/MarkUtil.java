package com.program.itta.common.util;

import java.util.UUID;

/**
 * @program: itta
 * @description: 生成随机数作为标志
 * @author: Mr.Huang
 * @create: 2020-04-05 22:24
 **/
public class MarkUtil {
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
