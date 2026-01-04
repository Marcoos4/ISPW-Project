package it.ispw.unilife.controller;

import it.ispw.unilife.bean.TutorBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingCtrl {

    // Metodo Simulato aggiornato per gestire List<String> subjects
    public List<TutorBean> getAvailableTutors(String query) {
        List<TutorBean> dummyList = new ArrayList<>();

        // --- CREAZIONE DATI FINTI ---

        TutorBean t1 = new TutorBean();
        t1.setId("1");
        t1.setName("Mario");
        t1.setSurname("Rossi");
        // Ora passiamo una lista di materie
        t1.setSubjects(Arrays.asList("Matematica", "Analisi 1", "Geometria"));
        t1.setHourlyRate(15.0);

        TutorBean t2 = new TutorBean();
        t2.setId("2");
        t2.setName("Luigi");
        t2.setSurname("Verdi");
        t2.setSubjects(Arrays.asList("Fisica", "Meccanica Razionale"));
        t2.setHourlyRate(20.0);

        TutorBean t3 = new TutorBean();
        t3.setId("3");
        t3.setName("Anna");
        t3.setSurname("Bianchi");
        t3.setSubjects(Arrays.asList("Informatica", "Java", "Basi di Dati", "Sistemi Operativi"));
        t3.setHourlyRate(18.0);

        // --- LOGICA DI FILTRO ---

        if (query == null || query.isEmpty()) {
            dummyList.add(t1);
            dummyList.add(t2);
            dummyList.add(t3);
        } else {
            String search = query.toLowerCase();

            // Controlliamo se la query è contenuta in una delle materie della lista
            if (hasSubject(t1, search)) dummyList.add(t1);
            if (hasSubject(t2, search)) dummyList.add(t2);
            if (hasSubject(t3, search)) dummyList.add(t3);
        }

        return dummyList;
    }

    // Helper per cercare nella lista delle materie
    private boolean hasSubject(TutorBean tutor, String query) {
        if (tutor.getSubjects() == null) return false;
        for (String sub : tutor.getSubjects()) {
            if (sub.toLowerCase().contains(query)) {
                return true;
            }
        }
        return false;
    }

    public void processSelection(TutorBean tutor) {
        System.out.println("TEST: Controller ha ricevuto la richiesta per " + tutor.getName());
    }
}