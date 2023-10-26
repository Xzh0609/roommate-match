package com.ecut.xzh.service;

import com.ecut.xzh.dto.StudentDTO;
import com.ecut.xzh.dto.StudentPageDTO;
import com.ecut.xzh.result.PageResult;
import com.ecut.xzh.vo.StudentVO;

import java.util.List;

public interface MessageService {

    /**
     * 分页查询学生信息
     * @param studentPageDTO
     * @return
     */
    PageResult pageQuery(StudentPageDTO studentPageDTO);

    /**
     * 新增学生信息
     * @param studentDTO
     */
    void save(StudentDTO studentDTO);

    /**
     * 删除学生信息
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    StudentVO getById(Long id);

    /**
     * 修改学生信息
     * @param studentDTO
     * @return
     */
    void update(StudentDTO studentDTO);
}
