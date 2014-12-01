package org.taskstodo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Username already exists") 
public class UserAlreadyExistsException extends RuntimeException {

}
