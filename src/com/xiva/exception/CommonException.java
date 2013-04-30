package com.xiva.exception;

public class CommonException extends RuntimeException{

    /**
     * 
     */
    private static final long serialVersionUID = 3930640172395921011L;

    public CommonException() {
        super();
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    
}
