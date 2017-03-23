package com.boyoi.core.exception;

/**
 * 消息异常
 * @author Chenj
 */
public class MsgException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4190001092027657079L;

	public MsgException() {
        super();
    }

    public MsgException(String message) {
        super(message);
    }

    public MsgException(String message, Throwable cause) {
        super(message, cause);
    }

    public MsgException(Throwable cause) {
        super(cause);
    }

    protected MsgException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
