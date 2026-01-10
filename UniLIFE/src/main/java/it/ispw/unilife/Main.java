package it.ispw.unilife;

import it.ispw.unilife.config.Configuration;
import it.ispw.unilife.config.UILauncher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main{

    public static void main(String[] args) {

        try {
            Configuration.getInstance().loadConfiguration(args);
        } catch (IllegalStateException e) {
            System.err.println("Tried to reload App config: " + e.getMessage());
            return;
        }

        UILauncher launcher = UILauncher.getLauncher();
        launcher.startUI();
    }
}