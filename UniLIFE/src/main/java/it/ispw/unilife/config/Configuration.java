package it.ispw.unilife.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration{

    private static Configuration instance;

    private static final String CONFIG_FILE_PATH = "res/config.properties";
    private static final String CONFIG_UI_MODE_PROP = "ui_mode";
    private static final String CONFIG_PERSISTENCY_MODE_PROP = "persistency_mode";

    private  UiMode uiMode = UiMode.JFX;
    private  PersistencyMode persistencyMode = PersistencyMode.JSON;
    private boolean loaded = false;  // Config can be loaded only once

    private Configuration() {}

    public static synchronized Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public void loadConfiguration(String[] args) throws IllegalStateException {

        if(loaded)
            throw new IllegalStateException("Config already loaded!");

        try (InputStream confIn = new FileInputStream(CONFIG_FILE_PATH)) {

            Properties props = new Properties();
            props.load(confIn);

            String configUiMode = props.getProperty(CONFIG_UI_MODE_PROP);
            if (configUiMode != null) {
                this.uiMode = UiMode.valueOf(configUiMode.toUpperCase());
            }

            String configPersMode = props.getProperty(CONFIG_PERSISTENCY_MODE_PROP);
            if (configPersMode != null) {
                this.persistencyMode = PersistencyMode.valueOf(configPersMode.toUpperCase());
            }

            System.out.println("Loaded config from " + CONFIG_FILE_PATH);

        } catch (IOException e) {
            System.out.println("Couldn't find " + CONFIG_FILE_PATH + ".\nUsing Default config!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid values passed inside config files.\nUsing Default config!");
        }

        for (String arg : args){
            switch (arg.toLowerCase()) {
                case "--cli":
                    this.uiMode = UiMode.CLI;
                    break;
                case "--gui", "--jfx":
                    this.uiMode = UiMode.JFX;
                    break;
                case "--json", "--fs":
                    this.persistencyMode = PersistencyMode.JSON;
                    break;
                case "--jdbc", "--db":
                    this.persistencyMode = PersistencyMode.JDBC;
                    break;
                default:
                    break;
            }
        }

        loaded = true;
    }

    public PersistencyMode getPersistencyMode() {
        return persistencyMode;
    }

    public UiMode getUiMode() {
        return uiMode;
    }
}
