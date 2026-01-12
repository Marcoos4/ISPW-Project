package it.ispw.unilife.model;

public enum ReservationStatus {
    PENDING,
    CONFIRMED,
    FAILED,
    CANCELLED,
    REJECTED,
    ACCEPTED;

    public static ReservationStatus stringToStatus(String stringStatus){
        switch (stringStatus){
            case "Confirmed"->{
                return ReservationStatus.CONFIRMED;
            }
            case "Failed"-> {
                return ReservationStatus.FAILED;
            }
            case "Cancelled"-> {
                return ReservationStatus.CANCELLED;
            }
            case "Rejected"-> {
                return ReservationStatus.REJECTED;
            }
            case "Accepted"-> {
                return ReservationStatus.ACCEPTED;
            }
            default -> {
                return ReservationStatus.PENDING;
            }
        }
    }

    public static String statusToString(ReservationStatus status){
        switch (status){
            case CONFIRMED->{
                return "Confirmed";
            }
            case FAILED-> {
                return "Failed";
            }
            case CANCELLED-> {
                return "Cancelled";
            }
            case REJECTED-> {
                return "Rejected";
            }
            case ACCEPTED-> {
                return "Accepted";
            }
            default -> {
                return "Pending";
            }
        }
    }
}