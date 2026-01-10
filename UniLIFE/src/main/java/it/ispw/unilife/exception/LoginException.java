package it.ispw.unilife.exception;

public class LoginException extends Exception {
    public LoginException(String errStr) {
        System.out.println(errStr);
    }

    public LoginException() {
    }
}
