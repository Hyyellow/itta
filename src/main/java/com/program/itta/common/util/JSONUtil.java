package com.program.itta.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @program: itta
 * @description: Json工具类
 * @author: Mr.Huang
 * @create: 2020-04-05 12:18
 **/
public enum JSONUtil {
    ;

    /**
     * 将JSON字符串转为Java对象
     */
    public static <T> T jsonString2Object(String result, Class<T> clazz) {
        return JSONObject.toJavaObject(JSONObject.parseObject(result), clazz);
    }

    /**
     * JSON字符串对象解析成java List对象
     */
    public static <T> List<T> jsonStringList2Object(String resultList, Class<T> clazz) {
        return JSONArray.parseArray(resultList).toJavaList(clazz);
    }

}
