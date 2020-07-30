package com.it.yanxuan.converter;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * @auther: cyb
 * @create: 2020/7/21 10:48
 */

/**
 * 将BigDecimal类型转换为Double类型
 */
public class CustomConverter implements Converter<BigDecimal, String> {
    @Override
    public String convert(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.toPlainString();
    }
}
