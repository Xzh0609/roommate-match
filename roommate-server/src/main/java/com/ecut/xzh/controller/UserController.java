package com.ecut.xzh.controller;

import com.ecut.xzh.constant.JwtClaimsConstant;
import com.ecut.xzh.constant.MessageConstant;
import com.ecut.xzh.context.BaseContext;
import com.ecut.xzh.dto.*;
import com.ecut.xzh.entity.Student;
import com.ecut.xzh.exception.MessageIsNullException;
import com.ecut.xzh.properties.JwtProperties;
import com.ecut.xzh.result.Result;
import com.ecut.xzh.service.UserService;
import com.ecut.xzh.utils.JwtUtil;
import com.ecut.xzh.vo.StudentVO;
import com.ecut.xzh.vo.StudentWithPhoneVO;
import com.ecut.xzh.vo.UserLoginVO;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XZH
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = "用户信息")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 查询学生信息
     * @param
     * @return
     */
    @ApiOperation("查询学生信息")
    @GetMapping
    public Result<StudentWithPhoneVO> query(){
        log.info("开始查询当前用户信息");
        //获取当前用户id
        StudentWithPhoneVO studentWithPhoneVO = userService.query(BaseContext.getCurrentId());
        return Result.success(studentWithPhoneVO);
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录：{}", userLoginDTO);

        Student student = userService.login(userLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, student.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);


        UserLoginVO userLoginVO = UserLoginVO.builder()
                .number(student.getNumber())
                .token(token)
                //.code(1)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO){
        log.info("用户注册：{}", userRegisterDTO);

        userService.register(userRegisterDTO);

        return Result.success();
    }

    /**
     * 修改密码
     * @param updatePasswordDTO
     * @return
     */
    @PutMapping("/password")
    @ApiOperation(value = "修改密码")
    public Result updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO){

        userService.updatePassword(updatePasswordDTO);
        return Result.success();
    }

    /**
     * 忘记密码
     * @param updatePasswordDTO
     * @return
     */
    @PutMapping("/forget")
    @ApiOperation(value = "修改密码")
    public Result forgetPassword(@RequestBody UpdatePasswordDTO updatePasswordDTO){

        userService.forgetPassword(updatePasswordDTO);
        return Result.success();
    }

    /**
     * 修改学生信息
     * @param studentWithPhoneDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改学生信息")
    public Result update(@RequestBody StudentWithPhoneDTO studentWithPhoneDTO){
        log.info("修改学生信息:{}",studentWithPhoneDTO);

        userService.update(studentWithPhoneDTO);

        return Result.success();
    }
}
