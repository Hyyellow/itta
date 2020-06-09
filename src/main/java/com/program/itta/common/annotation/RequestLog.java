package com.program.itta.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLog {
    /**
     * 请求模块名称
     *
     * @return
     */
    String module() default "";

    /**
     * 接口详情描述
     *
     * @return
     */
    String operationDesc() default "";
}
