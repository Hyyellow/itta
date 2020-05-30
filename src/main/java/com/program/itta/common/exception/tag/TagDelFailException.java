package com.program.itta.common.exception.tag;

/**
 * @program: itta
 * @description: 标签删除失败
 * @author: Mr.Huang
 * @create: 2020-05-28 13:53
 **/
public class TagDelFailException extends RuntimeException{
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
    public TagDelFailException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
