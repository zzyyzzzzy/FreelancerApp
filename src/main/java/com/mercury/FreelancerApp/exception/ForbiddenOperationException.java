package com.mercury.FreelancerApp.exception;

public class ForbiddenOperationException extends RuntimeException{
    public ForbiddenOperationException(){}
    public ForbiddenOperationException(String msg){super(msg);}
}
