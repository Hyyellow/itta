package com.program.itta.common.result;

/**
 * @program: itta
 * @description: 统一返回格式枚举
 * @author: Mr.Huang
 * @create: 2020-04-04 10:37
 **/
public enum  ResultCodeEnum {
    /*** 通用部分 100 - 599***/
    // 成功请求
    SUCCESS(200, "successful"),
    // 重定向
    REDIRECT(301, "redirect"),
    // 资源未找到
    NOT_FOUND(404, "not found"),
    // 服务器错误
    SERVER_ERROR(500,"server error"),
    /*** 这里可以根据不同模块用不同的区级分开错误码，例如:  ***/
    // 1000～1999 区间表示用户模块错误
    User_Exists_Exception(10001,"用户已存在"),
    User_Not_Exists_Exception(10002,"用户不存在"),
    // 2000～2999 区间表示项目模块错误
    Item_Name_Exists_Exception(20001,"项目名称已存在"),
    Item_Add_Fail_Exception(20001,"项目添加失败"),
    Item_Del_Fail_Exception(20001,"项目删除失败"),
    // 3000～3999 区间表示商品模块错误
    // 。。。

    ;

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
