package com.farm.core.auth.exception;

public class CheckCodeErrorException
  extends Exception
{
  private static final long serialVersionUID = 6482296763929242398L;
  
  public CheckCodeErrorException(String message)
  {
    super(message);
  }
}

