package com.program.itta.common.convert;


import	java.util.function.Function;

/**
 * @program: itta
 * @description: DTO转换抽象接口
 * @author: Mr.Huang
 * @create: 2020-04-04 10:26
 **/
public abstract class DTOConvert<A,B> implements Function<A, B>{
    // DTO转换为Entity
    protected abstract B doForward(A a);

    // Entity转换为DTO
    protected abstract A doBackward(B b);

}
