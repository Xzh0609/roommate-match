package com.ecut.xzh.service;

import com.ecut.xzh.dto.*;
import com.ecut.xzh.entity.Student;
import com.ecut.xzh.vo.StudentVO;
import com.ecut.xzh.vo.StudentWithPhoneVO;

import java.util.List;

public interface UserService {

    /**
     * 查询学生信息
     * @param id
     * @return
     */
    StudentWithPhoneVO query(Long id);

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    Student login(UserLoginDTO userLoginDTO);

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 修改密码
     * @param updatePasswordDTO
     */
    void updatePassword(UpdatePasswordDTO updatePasswordDTO);

    /**
     * 修改学生信息
     * @param studentWithPhoneDTO
     * @return
     */
    void update(StudentWithPhoneDTO studentWithPhoneDTO);

    /**
     * 忘记密码
     * @param updatePasswordDTO
     */
    void forgetPassword(UpdatePasswordDTO updatePasswordDTO);
}
