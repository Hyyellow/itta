package com.program.itta.common.convert;

/**
 * @program: itta
 * @description: DTO转换抽象接口
 * @author: Mr.Huang
 * @create: 2020-04-04 10:26
 **/
public interface DTOConvert<A,B> {
    // DTO转换为Entity
    B doForward(A a);

    // Entity转换为DTO
    A doBackward(B b);
}
