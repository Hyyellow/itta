package com.program.itta.common.exception.news;

/**
 * @program: itta
 * @description: 消息删除失败
 * @author: Mr.Huang
 * @create: 2020-05-18 16:43
 **/
public class NewsDelFailException extends RuntimeException{
    /**
     * 错误码
     */
    protected Integer code;

    protected String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 有参构造器，返回码在枚举类中，这里可以指定错误信息
     *
     * @param msg
     */
    public NewsDelFailException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
