package it.ispw.unilife.exception;

public class DuplicateUserException extends RegistrationException{
    public DuplicateUserException (String errStr){System.out.println(errStr);}
}
