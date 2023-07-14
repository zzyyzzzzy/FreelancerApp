package com.mercury.FreelancerApp.exception;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException (){}
    public ServiceNotFoundException (String msg){super(msg);}
}
