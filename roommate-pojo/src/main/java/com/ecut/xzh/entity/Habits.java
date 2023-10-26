package com.ecut.xzh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habits implements Serializable {

    private Long id;

    //抽烟
    private String smoke;

    //睡觉最早时间
    private LocalTime sleepBeginTime;
    //睡觉最晚时间
    private LocalTime sleepEndTime;

    //起床最早时间
    private LocalTime getUpBeginTime;
    //起床最晚时间
    private LocalTime getUpEndTime;

}
