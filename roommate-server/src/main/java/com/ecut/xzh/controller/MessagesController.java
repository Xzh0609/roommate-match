package com.ecut.xzh.controller;

import com.ecut.xzh.context.BaseContext;
import com.ecut.xzh.dto.StudentDTO;
import com.ecut.xzh.dto.StudentPageDTO;
import com.ecut.xzh.result.PageResult;
import com.ecut.xzh.result.Result;
import com.ecut.xzh.service.MessageService;
import com.ecut.xzh.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XZH
 */
@CrossOrigin
@RestController
@RequestMapping("/messages")
@Slf4j
@Api(tags = "学生信息")
public class MessagesController {
    @Autowired
    private MessageService messageService;

    /**
     * 分页查询学生信息
     * @param studentPageDTO
     * @return
     */
    @ApiOperation("分页查询学生信息")
    @GetMapping
    public Result<PageResult> page(StudentPageDTO studentPageDTO){
        log.info("开始分页查询");
        PageResult pageResult = messageService.pageQuery(studentPageDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增学生信息
     * @param studentDTO
     * @return
     */
    @ApiOperation("新增学生信息")
    @PostMapping
    public Result save(@RequestBody StudentDTO studentDTO){
        log.info("新增学生信息：{}", studentDTO);
        messageService.save(studentDTO);
        return Result.success();
    }


    /**
     * 批量删除学生信息
     * @param ids
     * @return
     */
    @ApiOperation("批量删除学生信息")
    @DeleteMapping("/{ids}")
    public Result deleteBatch(@PathVariable List<Long> ids){
        log.info("被删除学生的id：{}",ids);
        messageService.delete(ids);
        return Result.success();
    }

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询学生信息")
    @GetMapping("/{id}")
    public Result<StudentVO> getById(@PathVariable Long id){
        log.info("根据id:{}查询学生信息",id);
        StudentVO studentVO = messageService.getById(id);
        return Result.success(studentVO);
    }

    /**
     * 修改学生信息
     * @param studentDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改学生信息")
    public Result<StudentVO> update(@RequestBody StudentDTO studentDTO){
        log.info("修改学生信息:{}",studentDTO);

        messageService.update(studentDTO);

        return Result.success();
    }
}
