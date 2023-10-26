package com.ecut.xzh.exception;

/**
 * 学号过长
 */
public class RemarksTooLongException extends BaseException {

    public RemarksTooLongException() {
    }

    public RemarksTooLongException(String msg) {
        super(msg);
    }

}
