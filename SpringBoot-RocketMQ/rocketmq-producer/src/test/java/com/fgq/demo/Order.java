package com.fgq.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单
 */
@Data
public class Order implements Serializable {

    private int orderId;
    private String desc;

    public Order(int orderId, String desc) {
        this.orderId = orderId;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
