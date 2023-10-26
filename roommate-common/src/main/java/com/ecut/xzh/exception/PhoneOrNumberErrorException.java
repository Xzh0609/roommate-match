package com.ecut.xzh.exception;

/**
 * 用户名或密码为空
 */
public class PhoneOrNumberErrorException extends BaseException {

    public PhoneOrNumberErrorException() {
    }

    public PhoneOrNumberErrorException(String msg) {
        super(msg);
    }

}
