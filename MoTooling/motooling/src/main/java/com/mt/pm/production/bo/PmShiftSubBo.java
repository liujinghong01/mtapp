package com.mt.pm.production.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: Randy
 * @Date: Create in 2018-01-10 8:53
 * @Description:
 * @Modified By:
 */
public class PmShiftSubBo {

    /**序号*/
    private Integer seq;
    /**开始时间*/
    private String startTime;
    /**结束时间*/
    private String endTime;
    /**段内有效时间*/
    private Integer shiftMins;
    /**是否跨天段*/
    private String isOverDay;
    /**日期*/
    private LocalDateTime theDate;
    /**每人每日工时*/
    private BigDecimal dayHours;

    public PmShiftSubBo() {

    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getShiftMins() {
        return shiftMins;
    }

    public void setShiftMins(Integer shiftMins) {
        this.shiftMins = shiftMins;
    }

    public String getIsOverDay() {
        return isOverDay;
    }

    public void setIsOverDay(String isOverDay) {
        this.isOverDay = isOverDay;
    }

    public LocalDateTime getTheDate() {
        return theDate;
    }

    public void setTheDate(LocalDateTime theDate) {
        this.theDate = theDate;
    }

    public BigDecimal getDayHours() {
        return dayHours;
    }

    public void setDayHours(BigDecimal dayHours) {
        this.dayHours = dayHours;
    }

    public PmShiftSubBo(Integer seq, String startTime, String endTime, Integer shiftMins, String isOverDay, LocalDateTime theDate, BigDecimal dayHours) {
        this.seq = seq;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftMins = shiftMins;
        this.isOverDay = isOverDay;
        this.theDate = theDate;
        this.dayHours = dayHours;
    }

    @Override
    public String toString() {
        return "PmShiftSubBo{" +
                "seq=" + seq +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", shiftMins=" + shiftMins +
                ", isOverDay='" + isOverDay + '\'' +
                ", theDate=" + theDate +
                ", dayHours=" + dayHours +
                '}';
    }
}
