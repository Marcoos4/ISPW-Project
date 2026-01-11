package it.ispw.unilife.model;

public class Payment {
    private String cardHolder; // Nome titolare
    private String cardNumber; // Numero carta
    private String expiryDate; // Scadenza (es. "12/26")
    private String cvv;        // Codice sicurezza
    private String code;

    public String getCardHolder() {return cardHolder;}
    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}

    public String getCardNumber() {return cardNumber;}
    public void setCardNumber(String cardNumber) {this.cardNumber = cardNumber;}

    public String getExpiryDate() {return expiryDate;}
    public void setExpiryDate(String expiryDate) {this.expiryDate = expiryDate;}

    public String getCvv() {return cvv;}
    public void setCvv(String cvv) {this.cvv = cvv;}

    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}
}
