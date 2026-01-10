package it.ispw.unilife.model;

public enum Role {
    GUEST,
    STUDENT,
    TUTOR,
    UNIVERSITY_EMPLOYEE;

    public static Role stringToRole(String stringRole){
        switch (stringRole){
            case "Tutor" -> {
                return Role.TUTOR;
            }
            case "Student"->{
                return Role.STUDENT;
            }
            case "University Employee"-> {
                return Role.UNIVERSITY_EMPLOYEE;
            }
            default -> {
                return Role.GUEST;
            }
        }
    }

    public static String roleToString(Role role){
        switch (role){
            case TUTOR -> {
                return "Tutor";
            }
            case STUDENT->{
                return "Student";
            }
            case UNIVERSITY_EMPLOYEE-> {
                return "University Employee";
            }
            default -> {
                return "Guest";
            }
        }
    }
}
