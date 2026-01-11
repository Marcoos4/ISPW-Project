package it.ispw.unilife.model;

public enum CourseType {
    BACHELOR,
    MASTER,
    SINGLE_CYCLE,
    PHD;

    public static CourseType stringToDegreeType(String stringDegree) {
        if (stringDegree == null) return null;

        switch (stringDegree) {
            case "Bachelor" -> {
                return CourseType.BACHELOR;
            }
            case "Master" -> {
                return CourseType.MASTER;
            }
            case "Single Cycle" -> {
                return CourseType.SINGLE_CYCLE;
            }
            case "PhD" -> {
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