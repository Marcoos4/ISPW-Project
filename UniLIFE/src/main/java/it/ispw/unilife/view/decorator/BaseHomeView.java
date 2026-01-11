package it.ispw.unilife.view.decorator;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class BaseHomeView extends ViewComponent {

    @Override
    public Pane draw() {
        try {
            // Carica lo scheletro grafico vuoto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/ispw/unilife/BaseHome.fxml"));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // O gestisci l'errore
        }
    }
}