package com.ecut.xzh.service.impl;

import com.ecut.xzh.constant.MessageConstant;
import com.ecut.xzh.dto.StudentDTO;
import com.ecut.xzh.dto.StudentPageDTO;
import com.ecut.xzh.entity.Habits;
import com.ecut.xzh.entity.Student;
import com.ecut.xzh.exception.AccountAlreadyExistException;
import com.ecut.xzh.exception.EndEarlyBeginException;
import com.ecut.xzh.exception.NumberTooLongException;
import com.ecut.xzh.exception.RemarksTooLongException;
import com.ecut.xzh.mapper.HabitsMapper;
import com.ecut.xzh.mapper.MessageMapper;
import com.ecut.xzh.mapper.UserMapper;
import com.ecut.xzh.result.PageResult;
import com.ecut.xzh.service.MessageService;
import com.ecut.xzh.vo.StudentVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private HabitsMapper habitsMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询学生信息
     *
     * @param studentPageDTO
     * @return
     */
    @Override
    public PageResult pageQuery(StudentPageDTO studentPageDTO) {
/*      Long currentId = BaseContext.getCurrentId();
        studentPageDTO.setCurrentId(currentId);*///没有效果？
        PageHelper.startPage(studentPageDTO.getPage(), studentPageDTO.getPageSize());

        LocalTime sleepEndTime = studentPageDTO.getSleepEndTime();
        //将sleepEndTime转化成字符串以至于其可以超过24。数据库中也可超24，即可比较
        if (sleepEndTime != null) {
            // 定义时间格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            // 将 sleepEndTime 格式化为字符串
            String sleepEndTimeStr = sleepEndTime.format(formatter);
            String[] HourParts = sleepEndTimeStr.split(":");
            int hours = Integer.parseInt(HourParts[0]);
            if (sleepEndTime.isBefore(LocalTime.parse("07:00:00"))) {
                hours += 24;
            }
            String newSleepEndTimeStr = String.format("%02d:%s:%s", hours, HourParts[1], HourParts[2]);
            studentPageDTO.setSleepEndTimeOver24(newSleepEndTimeStr);
        }


        Page<StudentVO> page = messageMapper.pageQuery(studentPageDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增学生信息
     *
     * @param studentDTO
     */
    @Override
    public void save(StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());

        messageMapper.insert(student);
    }

    /**
     * 删除学生信息
     *
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            messageMapper.deleteByNumber(id);
        }
    }

    /**
     * 根据id查询学生信息
     *
     * @param id
     * @return
     */
    @Override
    public StudentVO getById(Long id) {
        StudentVO studentVO = messageMapper.getById(id);
        return studentVO;
    }

    /**
     * 修改学生信息
     *
     * @param studentDTO
     * @return
     */
    @Override
    @Transactional
    public void update(StudentDTO studentDTO) {
        Student student = new Student();
        Habits habits = new Habits();

        //对学号进行判断,前端已判断
        if (studentDTO.getNumber().length() > 10) {
            throw new NumberTooLongException(MessageConstant.NUMBER_TOO_LONG);
        }

        //学号已存在
        Student dbStudent = userMapper.getByNumber(studentDTO.getNumber());
        if (dbStudent != null && !studentDTO.getNumber().equals(dbStudent.getNumber())) {
            throw new AccountAlreadyExistException(MessageConstant.NUMBER_ALREADY_EXITS);
        }

        //对设置时间进行判断
        LocalTime sleepBeginTime = studentDTO.getSleepBeginTime();
        LocalTime sleepEndTime = studentDTO.getSleepEndTime();
        LocalTime getUpBeginTime = studentDTO.getGetUpBeginTime();
        LocalTime getUpEndTime = studentDTO.getGetUpEndTime();

        //判断睡觉时间的最早和最晚
        if (sleepBeginTime != null && sleepEndTime != null) {
            int sleepBeginInt = timeToIntSleep(sleepBeginTime);
            int sleepEndTimeInt = timeToIntSleep(sleepEndTime);

            if (sleepBeginInt > sleepEndTimeInt) {
                throw new EndEarlyBeginException(MessageConstant.END_EARLY_BEGIN);
            }
        }

        //判断睡起床时间的最早和最晚
        if (getUpBeginTime != null && getUpEndTime != null) {
            int getUpBeginTimeInt = timeToIntGetUp(getUpBeginTime);
            int getUpEndTimeInt = timeToIntGetUp(getUpEndTime);

            if (getUpBeginTimeInt > getUpEndTimeInt) {
                throw new EndEarlyBeginException(MessageConstant.END_EARLY_BEGIN);
            }
        }


        if (studentDTO.getRemarks() != null && studentDTO.getRemarks().length() > 200) {
            throw new RemarksTooLongException("备注过长");
        }

        BeanUtils.copyProperties(studentDTO, student);
        student.setUpdateTime(LocalDateTime.now());

        BeanUtils.copyProperties(studentDTO, habits);
/*        habits.setSleepBeginTime(studentDTO.getSleepTime().get(0));
        habits.setSleepEndTime(studentDTO.getSleepTime().get(1));
        habits.setGetUpBeginTime(studentDTO.getGetUpTime().get(0));
        habits.setGetUpEndTime(studentDTO.getGetUpTime().get(1));*/


        messageMapper.updateById(student);
        habitsMapper.updateByStudentId(habits);

    }

    private int timeToIntSleep(LocalTime sleepEndTime) {
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // 将 sleepEndTime 格式化为字符串
        String sleepEndTimeStr = sleepEndTime.format(formatter);
        //拿到三个数字
        String[] threeParts = sleepEndTimeStr.split(":");
        int hoursBegin = Integer.parseInt(threeParts[0]);
        int minutesBegin = Integer.parseInt(threeParts[1]);
        int secondsBegin = Integer.parseInt(threeParts[2]);
        //如果小于7点说明是第二天
        if (sleepEndTime.isBefore(LocalTime.parse("07:00:00"))) {
            hoursBegin += 24;
        }

        //计算总和（秒）
        int sum = hoursBegin * 60 * 60 + minutesBegin * 60 + secondsBegin;
        return sum;
    }
    private int timeToIntGetUp(LocalTime sleepEndTime) {
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // 将 sleepEndTime 格式化为字符串
        String sleepEndTimeStr = sleepEndTime.format(formatter);
        //拿到三个数字
        String[] threeParts = sleepEndTimeStr.split(":");
        int hoursBegin = Integer.parseInt(threeParts[0]);
        int minutesBegin = Integer.parseInt(threeParts[1]);
        int secondsBegin = Integer.parseInt(threeParts[2]);

        //计算总和（秒）
        int sum = hoursBegin * 60 * 60 + minutesBegin * 60 + secondsBegin;
        return sum;
    }
}
