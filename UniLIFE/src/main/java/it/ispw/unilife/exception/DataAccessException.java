package it.ispw.unilife.exception;

public class DataAccessException extends Exception {
    public DataAccessException (String errStr){System.out.println(errStr);}
}
