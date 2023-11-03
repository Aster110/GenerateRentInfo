/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author l30049897
 * @Date 2023/11/3 10:06
 * @Version 1.0
 */
public class Bill {
    @JsonProperty("year")
    private int year;

    @JsonProperty("month")
    private int month;

    @JsonProperty("period")
    private int period;

    @JsonProperty("water")
    private double water;

    @JsonProperty("electric")
    private double electric;

    @JsonProperty("baseRent")
    private double baseRent;

    public Bill(int year, int month, int period, double water, double electric, double baseRent) {
        this.baseRent = baseRent;
        this.electric = electric;
        this.water = water;
        this.year = year;
        this.month = month;
        this.period = period;
    }
}
