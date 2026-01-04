package it.ispw.unilife.bean;

public class PaymentBean {
    private String cardHolder; // Nome titolare
    private String cardNumber; // Numero carta
    private String expiryDate; // Scadenza (es. "12/26")
    private String cvv;        // Codice sicurezza

    // Costruttore vuoto
    public PaymentBean() {}

    // Costruttore completo
    public PaymentBean(String cardHolder, String cardNumber, String expiryDate, String cvv) {
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    // --- GETTERS & SETTERS ---

    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}