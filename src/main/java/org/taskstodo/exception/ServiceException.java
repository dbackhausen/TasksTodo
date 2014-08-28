package org.taskstodo.exception;

public class ServiceException extends Exception {
  private static final long serialVersionUID = 8956795330017580998L;

  private int code;
  private String message;
  private Throwable throwable;
  
  public ServiceException(String message) {
    this.message = message;
  }
  
  public ServiceException(int code, String message) {
    this.code = code;
    this.message = message;
  }
  
  public ServiceException(int code, String message, Throwable throwable) {
    this.code = code;
    this.message = message;
    this.throwable = throwable;
  }
  
  public int getCode() {
    return code;
  }
  
  @Override
  public String getMessage() {
    return message;
  }
  
  public Throwable getThrowable() {
    return throwable;
  }
}
