package com.ecut.xzh.mapper;

import com.ecut.xzh.dto.UpdatePasswordDTO;
import com.ecut.xzh.entity.Student;
import com.ecut.xzh.vo.StudentVO;
import com.ecut.xzh.vo.StudentWithPhoneVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 查询学生信息
     * @param id
     * @return
     */
    @Select("select s.id, number, name, phone, qq, gender, create_time, update_time, remarks, " +
            "major, smoke, sleep_begin_time, sleep_end_time, get_up_begin_time, " +
            "get_up_end_time, student_id from students s,habits h where s.id = h.student_id and s.id = #{id}")
    StudentWithPhoneVO select(Long id);

    /**
     * 根据学号查询用户信息
     * @param number
     * @return
     */
    @Select("select id, number, name, qq, gender, create_time, update_time, remarks, major, password from students where number = #{number}")
    Student getByNumber(String number);

    /**
     * 新增用户
     * @param student
     */
    //TODO 插入
    void insert(Student student);

    /**
     * 更新用户信息
     * @param updatePasswordDTO
     */
    void update(UpdatePasswordDTO updatePasswordDTO);

/*    *//**
     * 根据用户id查询手机号
     * @param id
     * @return
     *//*
    @Select("select phone from students where id = #{id}")
    String getPhoneById(Long id);*/

    /**
     * 查询用户的所有信息
     * @param number
     * @return
     */
    @Select("select * from students where number = #{number}")
    Student selectAll(String number);

    /**
     * 修改密码
     * @param updatePasswordDTO
     */
    @Update("update students set password = #{newPassword} where number = #{number}")
    void updateByNewPassword(UpdatePasswordDTO updatePasswordDTO);
}
