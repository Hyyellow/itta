package com.program.itta.common.exception.user;

/**
 * @program: itta
 * @description: 参考异常模版(Token验证)
 * @author: Mr.Huang
 * @create: 2020-04-04 11:00
 **/
public class UserExistsException extends RuntimeException{
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
    public UserExistsException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
