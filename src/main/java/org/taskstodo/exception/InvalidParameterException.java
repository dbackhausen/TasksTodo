package org.taskstodo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.PRECONDITION_FAILED, reason="Invalid parameters!") 
public class InvalidParameterException extends RuntimeException {

}
