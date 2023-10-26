package com.ecut.xzh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithPhoneVO implements Serializable {

    private Long id;

    //学号
    private String number;

    //姓名
    private String name;

    //手机号
    private String phone;

    //QQ
    private String qq;

    //性别
    private String gender;

    //备注
    private String remarks;

    //专业
    private String major;

    //TODO 加习惯

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
