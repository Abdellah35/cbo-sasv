package com.cbo.core.exception;

public class AppRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 8848873977589121782L;

    public AppRuntimeException() {
        super();
    }

    /**
     * @param message format exception message
     */
    public AppRuntimeException(final String message) {
        super(message);
    }

    /**
     * @param message format exception message
     * @param cause   actual cause
     */
    public AppRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause actual cause
     */
    public AppRuntimeException(final Throwable cause) {
        super(cause);
    }


}
