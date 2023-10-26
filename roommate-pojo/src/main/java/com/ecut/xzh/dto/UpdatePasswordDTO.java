package com.ecut.xzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDTO implements Serializable {

    private Long id;

    private String number;

    private String password;
    private String newPassword;

    private String phone;
}
