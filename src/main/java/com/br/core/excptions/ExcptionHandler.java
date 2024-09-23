package com.br.core.excptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExcptionHandler extends ResponseEntityExceptionHandler {

  // @ExceptionHandler(Exception.class)
  // public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
  // var status = HttpStatus.INTERNAL_SERVER_ERROR;

  // return new ResponseEntity<>(new ExceptionResponse(status, ex.getMessage()), status);
  // }


}
