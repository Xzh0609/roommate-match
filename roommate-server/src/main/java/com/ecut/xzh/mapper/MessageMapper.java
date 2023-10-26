package com.ecut.xzh.mapper;

import com.ecut.xzh.dto.StudentDTO;
import com.ecut.xzh.dto.StudentPageDTO;
import com.ecut.xzh.entity.Student;
import com.ecut.xzh.vo.StudentVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 分页查询学生信息
     *
     * @param studentPageDTO@return
     */
    Page<StudentVO> pageQuery(StudentPageDTO studentPageDTO);

    /**
     * 新增学生信息
     * @param student
     */
    @Insert("insert into students (number, name, qq, gender, create_time, update_time, remarks, major) " +
            "values (#{number}, #{name}, #{qq}, #{gender}, #{createTime}, #{updateTime}, #{remarks}, #{major})")
    void insert(Student student);

    /**
     * 删除学生信息
     * @param id
     */
    @Delete("delete from students where id = #{id}")
    void deleteByNumber(Long id);

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    @Select("select id, number, name, qq, gender, create_time, update_time, remarks, major, password from students where id = #{id}")
    StudentVO getById(Long id);

    /**
     * 修改学生信息
     * @param student
     */
    void updateById(Student student);
}
