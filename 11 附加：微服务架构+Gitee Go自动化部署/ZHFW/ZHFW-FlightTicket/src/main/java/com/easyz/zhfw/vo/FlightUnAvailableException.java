package com.easyz.zhfw.vo;

public class FlightUnAvailableException extends Exception {

    public FlightUnAvailableException() {
        super("航班不可用或座位不足");
    }

    public FlightUnAvailableException(String message) {
        super(message);
    }

    public FlightUnAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlightUnAvailableException(Throwable cause) {
        super(cause);
    }
}
