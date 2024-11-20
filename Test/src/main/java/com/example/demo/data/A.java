package com.example.demo.data;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Data
public class A {

    // 代码
    private String code;
    // 涨跌幅(%)
    private BigDecimal pc;
    // 当前价格(元)
    private BigDecimal p;
    // 最高价(元)
    private BigDecimal h;
    // 最低价(元)
    private BigDecimal l;
    // 换手(%)
    private BigDecimal hs;
    // 量比
    private BigDecimal lb;
    // 开盘价(元)
    private BigDecimal o;
    // 成交额(元)
    private BigDecimal cje;
    // 成交量(手)
    private BigDecimal v;
    // 涨跌额(元)
    private BigDecimal ud;
    // 振幅(%)
    private BigDecimal zf;
    // 数据更新时间
    private String t;

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", 涨跌幅=" + pc +
                ", 当前价格=" + p +
                ", 最高价=" + h +
                ", 最低价=" + l +
                ", 换手=" + Optional.ofNullable(zf).map(val -> hs + "%").orElse(null) +
                ", 量比=" + lb +
                ", 开盘价=" + o +
                ", 成交额=" + Optional.ofNullable(cje).map(val -> val.divide(new BigDecimal("100000000"), 2, RoundingMode.UP) + "亿").orElse(null) +
                ", 成交量=" + Optional.ofNullable(v).map(val -> val.divide(new BigDecimal("10000"), 2, RoundingMode.UP) + "万手").orElse(null) +
                ", 涨跌额=" + ud +
                ", 振幅=" + Optional.ofNullable(zf).map(val -> zf + "%").orElse(null) +
                ", 数据更新时间='" + t + '\'' +
                '}';
    }
}
