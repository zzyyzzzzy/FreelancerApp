package com.mercury.FreelancerApp.exception;


public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(){}
    public OrderNotFoundException(String msg){super(msg);}

}
