package com.ecut.xzh.exception;

/**
 * 用户名或密码为空
 */
public class AccountAlreadyExistException extends BaseException {

    public AccountAlreadyExistException() {
    }

    public AccountAlreadyExistException(String msg) {
        super(msg);
    }

}
