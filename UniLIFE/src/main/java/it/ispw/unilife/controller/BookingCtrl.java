package it.ispw.unilife.controller;

import it.ispw.unilife.bean.TutorBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingCtrl { // <--- QUESTA RIGA MANCAVA!

    public List<TutorBean> getAvailableTutors(String query) {
        List<TutorBean> dummyList = new ArrayList<>();

        // --- CREAZIONE DATI FINTI (MOCK) ---

        // 1. Mario Rossi: Matematica - 3 Stelle (Media)
        TutorBean t1 = new TutorBean("1", "Mario", "Rossi",
                Arrays.asList("Matematica", "Analisi 1", "Geometria"), 15.0, 3);

        // 2. Luigi Verdi: Fisica - 5 Stelle (Top)
        TutorBean t2 = new TutorBean("2", "Luigi", "Verdi",
                Arrays.asList("Fisica", "Meccanica Razionale"), 20.0, 5);

        // 3. Anna Bianchi: Informatica - 4 Stelle (Buono)
        TutorBean t3 = new TutorBean("3", "Anna", "Bianchi",
                Arrays.asList("Informatica", "Java", "Basi di Dati"), 18.0, 4);

        // 4. Giulia Neri: Chimica - 2 Stelle (Basso)
        TutorBean t4 = new TutorBean("4", "Giulia", "Neri",
                Arrays.asList("Chimica", "Biologia"), 12.0, 2);

        // Aggiungiamo tutto alla lista
        dummyList.add(t1);
        dummyList.add(t2);
        dummyList.add(t3);
        dummyList.add(t4);

        // --- LOGICA DI RITORNO ---
        // Per ora restituiamo sempre tutto (Logica Dummy)
        return dummyList;
    }

    // Metodo per gestire la selezione (necessario perché la View lo chiama)
    public void processSelection(TutorBean tutor) {
        System.out.println("BookingCtrl: Richiesta prenotazione per " + tutor.getName());
    }

} // <--- NON DIMENTICARE QUESTA PARENTESI DI CHIUSURA