package it.ispw.unilife.boundary;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class StripeBoundary {

    public StripeBoundary(){
        Stripe.apiKey = "key";
    }

    public PaymentIntentCreateParams setUpPayment(long amount, String currency, String paymentMethod){
        return PaymentIntentCreateParams.builder().setAmount(amount).setConfirm(true).setCurrency(currency).setPaymentMethod(paymentMethod).setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER) // <--- QUESTO!
                        .build()
        ).build();

    }

    public boolean doPayment(PaymentIntentCreateParams params) throws StripeException {
        PaymentIntent payment = PaymentIntent.create(params);

        return switch (payment.getStatus()) {

            case "succeeded" -> {
                System.out.println("✅ STRIPE: Pagamento completato con successo!");
                System.out.println("   ID Transazione: " + payment.getId());
                yield true;
            }
            case "requires_payment_method" -> {
                System.err.println("❌ STRIPE: Pagamento fallito. La carta è stata rifiutata.");
                yield false;
            }
            case "requires_action" -> {
                System.err.println("⚠️ STRIPE: La banca richiede un'autenticazione extra (3D Secure).");
                System.err.println("   Impossibile procedere in questa demo.");
                yield false;
            }
            default -> {
                System.err.println("⁉️ STRIPE: Stato imprevisto del pagamento: " + payment.getStatus());
                yield false;
            }
        };
    }

}
