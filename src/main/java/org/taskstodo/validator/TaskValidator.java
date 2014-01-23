package org.taskstodo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.taskstodo.model.Task;

@Component  
public class TaskValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return Task.class.equals(clazz);
  }

  @Override
  public void validate(Object obj, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "validator.title.required");
  }
}
