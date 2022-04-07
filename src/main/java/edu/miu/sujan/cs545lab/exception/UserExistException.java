package edu.miu.sujan.cs545lab.exception;

public class UserExistException extends RuntimeException {

  public UserExistException(String message) {
    super(message);
  }
}
