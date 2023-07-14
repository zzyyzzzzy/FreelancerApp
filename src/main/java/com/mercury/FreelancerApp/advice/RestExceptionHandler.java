package com.mercury.FreelancerApp.advice;

import com.mercury.FreelancerApp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handInvalidInputException(HttpServletRequest request, InvalidInputException invalidInputException)
    {
        String passedInErrMsg = invalidInputException.getMessage();
        String errMsg = passedInErrMsg != null? passedInErrMsg : "Invalid Input";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMsg);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(HttpServletRequest request, UserNotFoundException userNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Object> handleServiceNotFoundException(HttpServletRequest request, ServiceNotFoundException serviceNotFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Service not found");
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException(HttpServletRequest request, UserAlreadyExistException userAlreadyExistException){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exist");
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundExceptionException(HttpServletRequest request, OrderNotFoundException orderNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Not Found...");
    }
    @ExceptionHandler(RequirementNotFoundException.class)
    public ResponseEntity<Object> handleRequirementNotFoundException(HttpServletRequest request, RequirementNotFoundException requirementNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requirement Not Found...");
    }
    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<Object> handleUserNotAuthorizedException(HttpServletRequest request, UserNotAuthorizedException userNotAuthorizedException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You cant perform this action...");
    }
    @ExceptionHandler(OrderServiceFileNotFoundException.class)
    public ResponseEntity<Object> handleOrderServiceFileNotFoundException(HttpServletRequest request, OrderServiceFileNotFoundException orderServiceFileNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not Found...");
    }
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<Object> handleReviewAlreadyExistException(HttpServletRequest request, ReviewNotFoundException reviewNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review Not Found...");
    }
    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<Object> handleForbiddenOperationException(HttpServletRequest request, ForbiddenOperationException forbiddenOperationException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot perform operation");
    }
}
