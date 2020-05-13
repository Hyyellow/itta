package com.program.itta.common.exception.item;

/**
 * @program: itta
 * @description: 只有项目成员可访问
 * @author: Mr.Huang
 * @create: 2020-05-13 15:30
 **/
public class ItemNotPermissFindException extends RuntimeException{
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
    public ItemNotPermissFindException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
