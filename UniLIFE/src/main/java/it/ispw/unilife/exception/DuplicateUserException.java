package it.ispw.unilife.exception;

public class DuplicateUserException extends Exception{
    public DuplicateUserException (String errStr){System.out.println(errStr);}
}
