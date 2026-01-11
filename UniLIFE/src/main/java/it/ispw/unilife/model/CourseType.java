package it.ispw.unilife.model;

public enum CourseType {
    BACHELOR,
    MASTER,
    SINGLE_CYCLE,
    PHD;

    public static CourseType stringToCourseType(String stringDegree) {
        if (stringDegree == null) return null;

        switch (stringDegree) {
            case "Bachelor" , "BACHELOR"-> {
                return CourseType.BACHELOR;
            }
            case "Master" , "MASTER" -> {
                return CourseType.MASTER;
            }
            case "Single Cycle" , "SINGLE_CYCLE" -> {
                return CourseType.SINGLE_CYCLE;
            }
            case "PhD", "PHD" -> {
                return CourseType.PHD;
            }
            default -> {
                return null;
            }
        }
    }

    public static String degreeTypeToString(CourseType degreeType) {
        if (degreeType == null) return "";

        switch (degreeType) {
            case BACHELOR -> {
                return "Bachelor";
            }
            case MASTER -> {
                return "Master";
            }
            case SINGLE_CYCLE -> {
                return "Single Cycle";
            }
            case PHD -> {
                return "PhD";
            }
            default -> {
                return "";
            }
        }
    }
}