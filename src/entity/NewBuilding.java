/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Author l30049897
 * @Date 2023/11/3 10:04
 * @Version 1.0
 */
public class NewBuilding {
    @JsonProperty("roomNum")
    private String roomNum;

    @JsonProperty("bills")
    private List<Bill> bills;

     public NewBuilding(String roomNum, List<Bill> bills) {
         this.roomNum = roomNum;
         this.bills = bills;
     }
}
