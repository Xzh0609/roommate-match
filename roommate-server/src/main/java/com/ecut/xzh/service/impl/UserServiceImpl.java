package com.ecut.xzh.service.impl;

import com.ecut.xzh.constant.MessageConstant;
import com.ecut.xzh.dto.*;
import com.ecut.xzh.entity.Habits;
import com.ecut.xzh.entity.Student;
import com.ecut.xzh.exception.*;
import com.ecut.xzh.mapper.HabitsMapper;
import com.ecut.xzh.mapper.MessageMapper;
import com.ecut.xzh.mapper.UserMapper;
import com.ecut.xzh.service.UserService;
import com.ecut.xzh.vo.StudentVO;
import com.ecut.xzh.vo.StudentWithPhoneVO;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HabitsMapper habitsMapper;
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询学生信息
     * @param id
     * @return
     */
    @Override
    public StudentWithPhoneVO query(Long id) {
        StudentWithPhoneVO studentWithPhoneVO = userMapper.select(id);
        return studentWithPhoneVO;
    }

    @Override
    public Student login(UserLoginDTO userLoginDTO) {
        String number = userLoginDTO.getNumber();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Student student = userMapper.getByNumber(number);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (student == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //将密码通过md5加密方式进行转化
        //password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(student.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //查一下习惯表中有没有该学生的外键
        Long studentId = student.getId();
        Long habitsId = habitsMapper.selectByStudentId(studentId);
        //如果没有则添加，此步已在注册中进行，再次查询防止未成功，
        //一般在学生表直接增加学生信息时在习惯表中不会增加，让其在登录时增加
        if(habitsId == null){
            habitsMapper.insert(studentId);
        }

        //3、返回实体对象
        return student;
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    @Override
    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) {
        //未填写信息
        if(StrUtil.isBlank(userRegisterDTO.getNumber()) || StrUtil.isBlank(userRegisterDTO.getPassword())){
            throw  new MessageIsNullException(MessageConstant.MESSAGE_IS_NULL);
        }

        //TODO 后续进行更加细致的判断
        //对学号进行判断
        if(userRegisterDTO.getNumber().length() > 10){
            throw new NumberTooLongException(MessageConstant.NUMBER_TOO_LONG);
        }

        //学号已存在
        Student dbStudent = userMapper.getByNumber(userRegisterDTO.getNumber());
        if(dbStudent != null){
            throw new AccountAlreadyExistException(MessageConstant.NUMBER_ALREADY_EXITS);
        }

        Student student = new Student();
        BeanUtils.copyProperties(userRegisterDTO, student);

        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());

        userMapper.insert(student);
        //获取当前插入数据的id，用useGeneratedKeys="true" keyProperty="id"为啥每次id都是1
        Student studentDB = userMapper.getByNumber(student.getNumber());
        Long studentId = studentDB.getId();

        //增加习惯对应的学生id
        Long id = habitsMapper.selectByStudentId(studentId);
        //如果习惯中对应的学生id已经存在
        if(id != null){
            habitsMapper.deleteById(id);
        }
        habitsMapper.insert(studentId);
    }

    /**
     * 修改密码
     * @param updatePasswordDTO
     */
    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {

        Student student = userMapper.selectAll(updatePasswordDTO.getNumber());
        //校验密码是否正确
        if(!student.getPassword().equals(updatePasswordDTO.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //校验新旧密码是否一样
        if(student.getPassword().equals(updatePasswordDTO.getNewPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_SAME);
        }

        //校验手机号是否正确
        if(!student.getPhone().equals(updatePasswordDTO.getPhone())){
            throw new PhoneErrorException(MessageConstant.PHONE_ERROR);
        }


        userMapper.updateByNewPassword(updatePasswordDTO);

    }

    /**
     * 修改学生信息
     *
     * @param studentWithPhoneDTO
     * @return
     */
    @Override
    @Transactional
    public void update(StudentWithPhoneDTO studentWithPhoneDTO) {
        Student student = new Student();
        Habits habits = new Habits();

        //对学号进行判断,前端已判断
        if(studentWithPhoneDTO.getNumber().length() > 10){
            throw new NumberTooLongException(MessageConstant.NUMBER_TOO_LONG);
        }

        //学号已存在
        Student dbStudent = userMapper.getByNumber(studentWithPhoneDTO.getNumber());
        if(dbStudent != null && !studentWithPhoneDTO.getNumber().equals(dbStudent.getNumber())){
            throw new AccountAlreadyExistException(MessageConstant.NUMBER_ALREADY_EXITS);
        }

        if (studentWithPhoneDTO.getRemarks() != null && studentWithPhoneDTO.getRemarks().length() > 200) {
            throw new RemarksTooLongException("备注过长");
        }

        BeanUtils.copyProperties(studentWithPhoneDTO, student);
        student.setUpdateTime(LocalDateTime.now());

        BeanUtils.copyProperties(studentWithPhoneDTO, habits);
/*        habits.setSleepBeginTime(studentDTO.getSleepTime().get(0));
        habits.setSleepEndTime(studentDTO.getSleepTime().get(1));
        habits.setGetUpBeginTime(studentDTO.getGetUpTime().get(0));
        habits.setGetUpEndTime(studentDTO.getGetUpTime().get(1));*/


        messageMapper.updateById(student);
        habitsMapper.updateByStudentId(habits);

    }

    /**
     * 忘记密码
     * @param updatePasswordDTO
     */
    @Override
    public void forgetPassword(UpdatePasswordDTO updatePasswordDTO) {
        Student student = userMapper.selectAll(updatePasswordDTO.getNumber());
        if(updatePasswordDTO.getPhone().equals(student.getPhone())){
            //校验新旧密码是否一样
            if(student.getPassword().equals(updatePasswordDTO.getNewPassword())){
                throw new PasswordErrorException(MessageConstant.PASSWORD_SAME);
            }
            userMapper.updateByNewPassword(updatePasswordDTO);
        }else{
            throw new PhoneOrNumberErrorException(MessageConstant.PHONE_OR_NUMBER_ERROR);
        }
    }
}
