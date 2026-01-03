package it.ispw.unilife.model;

public class Tutor {
    private static Tutor instance = null;


    private Tutor(){
    }

    public static Tutor getInstance(){
        if (Tutor.instance == null){
            Tutor.instance = new Tutor();
        }
        return Tutor.instance;
    }
}
