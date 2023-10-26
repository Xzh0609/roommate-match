package com.ecut.xzh.exception;

/**
 * 学号过长
 */
public class NumberTooLongException extends BaseException {

    public NumberTooLongException() {
    }

    public NumberTooLongException(String msg) {
        super(msg);
    }

}
