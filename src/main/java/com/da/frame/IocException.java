package com.da.frame;

/**
 * @Author Da
 * @Description:
 * @Date: 2022-07-26
 * @Time: 10:42
 * 自定义异常
 */
public class IocException extends RuntimeException {
    public IocException() {
    }

    public IocException(String message) {
        super(message);
    }

    public IocException(String message, Throwable cause) {
        super(message, cause);
    }

    public IocException(Throwable cause) {
        super(cause);
    }

    public IocException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
