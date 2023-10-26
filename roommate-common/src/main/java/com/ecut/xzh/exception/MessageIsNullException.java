package com.ecut.xzh.exception;

/**
 * 用户名或密码为空
 */
public class MessageIsNullException extends BaseException {

    public MessageIsNullException() {
    }

    public MessageIsNullException(String msg) {
        super(msg);
    }

}
