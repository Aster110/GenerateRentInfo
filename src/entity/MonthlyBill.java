package entity;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author l30049897
 * @Date 2023/8/2 14:56
 * @Version 1.0
 */

public class MonthlyBill {
    @JsonProperty("tenant")
    private String tenant;

    @JsonProperty("year")
    private int year;

    @JsonProperty("month")
    private int month;

    @JsonProperty("period")
    private int period;

    @JsonProperty("waterMeter")
    private double waterMeter;

    @JsonProperty("electricMeter")
    private double electricMeter;

    @JsonProperty("otherFees")
    private double otherFees;

    @JsonProperty("baseRent")
    private double baseRent;

    @JsonProperty("waterFee")
    private double waterFee;

    @JsonProperty("electricFee")
    private double electricFee;

    @JsonProperty("total")
    private double total;

    public String getTenant() {
        return tenant;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getPeriod() {
        return period;
    }

    public double getWaterMeter() {
        return waterMeter;
    }

    public double getElectricMeter() {
        return electricMeter;
    }

    public double getOtherFees() {
        return otherFees;
    }

    public double getBaseRent() {
        return baseRent;
    }

    public double getWaterFee() {
        return waterFee;
    }

    public double getElectricFee() {
        return electricFee;
    }

    public double getTotal() {
        return total;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setWaterMeter(double waterMeter) {
        this.waterMeter = waterMeter;
    }

    public void setElectricMeter(double electricMeter) {
        this.electricMeter = electricMeter;
    }

    public void setOtherFees(double otherFees) {
        this.otherFees = otherFees;
    }

    public void setBaseRent(double baseRent) {
        this.baseRent = baseRent;
    }

    public void setWaterFee(double waterFee) {
        this.waterFee = waterFee;
    }

    public void setElectricFee(double electricFee) {
        this.electricFee = electricFee;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // TODO: 可以考虑使用构造函数，填入水表，电表，剩下的数据初始化自动生成
}