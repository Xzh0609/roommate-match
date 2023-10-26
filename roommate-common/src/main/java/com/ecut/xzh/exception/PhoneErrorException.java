package com.ecut.xzh.exception;

/**
 * 用户名或密码为空
 */
public class PhoneErrorException extends BaseException {

    public PhoneErrorException() {
    }

    public PhoneErrorException(String msg) {
        super(msg);
    }

}
