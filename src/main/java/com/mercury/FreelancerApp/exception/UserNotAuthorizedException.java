package com.mercury.FreelancerApp.exception;

public class UserNotAuthorizedException extends RuntimeException{
    public UserNotAuthorizedException(){}
    public UserNotAuthorizedException(String msg){super(msg);}

}
