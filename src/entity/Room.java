package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Author l30049897
 * @Date 2023/8/2 14:55
 * @Version 1.0
 */
public class Room {
    @JsonProperty("roomNum")
    private String roomNum;

    @JsonProperty("monthlyBills")
    private List<MonthlyBill> monthlyBills;

    public String getRoomNum() {
        return roomNum;
    }

    public List<MonthlyBill> getMonthlyBills() {
        return monthlyBills;
    }

}