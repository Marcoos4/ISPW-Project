package it.ispw.unilife.bean;

import java.util.List;

public class InterestSearchBean {

    private final List<String> interests;

    public InterestSearchBean(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getSelectedInterest() {
        return interests;
    }
}