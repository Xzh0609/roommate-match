package com.ecut.xzh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(description = "用户登录时传递的数据模型")
public class UserRegisterDTO implements Serializable {
    @ApiModelProperty("学号")
    private String number;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("密码")
    private String phone;
}
