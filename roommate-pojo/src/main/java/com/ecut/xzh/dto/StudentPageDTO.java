package com.ecut.xzh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentPageDTO implements Serializable {

    private Long id;

    //学号
    private String number;

    //姓名
    private String name;

    //QQ
    private String qq;

    //性别
    private String gender;

    //备注
    private String remarks;

    //专业
    private String major;

    //页码
    private int page;

    //每页记录数
    private int pageSize;

    //当前用户number，用于将记录放在第一条展示
    private Long numberId;

    //当前用户id，用于将记录放在第一条展示
    private Long currentId;

    //TODO 加习惯

    //抽烟
    private String smoke;

    //同样是接收数据为什么StudentDTO中不用加@DateTimeFormat
    //StudentDTO中前端是一个form，而这里的前端是一个''
    //睡觉最早时间
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime sleepBeginTime;
    //睡觉最晚时间
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime sleepEndTime;

    //起床最早时间
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime getUpBeginTime;
    //起床最晚时间
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime getUpEndTime;

    //对于凌晨时间的处理
    private String sleepEndTimeOver24;



}














