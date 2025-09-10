package com.cooksys.tweeter.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cooksys.tweeter.dtos.ErrorDto;
import com.cooksys.tweeter.exceptions.BadRequestException;
import com.cooksys.tweeter.exceptions.NotAuthorizedException;
import com.cooksys.tweeter.exceptions.NotFoundException;

@ControllerAdvice(basePackages = { "com.cooksys.tweeter.controllers" })
@ResponseBody
public class TweeterControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ErrorDto handleBadRequestException(BadRequestException badRequestException) {
    return new ErrorDto(badRequestException.getMessage());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ErrorDto handleNotFoundException(NotFoundException notFoundException) {
    return new ErrorDto(notFoundException.getMessage());
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(NotAuthorizedException.class)
  public ErrorDto handleNotAuthorizedxception(NotAuthorizedException notAuthorizedException) {
    return new ErrorDto(notAuthorizedException.getMessage());
  }

}
