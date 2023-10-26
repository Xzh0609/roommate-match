package com.ecut.xzh.mapper;

import com.ecut.xzh.entity.Habits;
import com.ecut.xzh.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HabitsMapper {

    //增加习惯对应的学生id
    @Insert("insert into habits (student_id) values (#{studentId})")
    void insert(Long studentId);

    //通过对应的学生id查询习惯id
    @Select("select id from habits where student_id = #{userId}")
    Long selectByStudentId(Long userId);

    //根据id删除习惯
    @Delete("delete from habits where id =#{id}")
    void deleteById(Long id);

    //根据对应的学生id更新数据
    void updateByStudentId(Habits habits);
}













