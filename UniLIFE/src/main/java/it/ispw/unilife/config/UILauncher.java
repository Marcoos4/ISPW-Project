package it.ispw.unilife.config;

public abstract class UILauncher {

    public static UILauncher getLauncher(){
        Configuration config = Configuration.getInstance();
        UiMode uiMode = config.getUiMode();

        if (uiMode.equals(UiMode.JFX))
            return new JFXLauncher();
        else return new CLILauncher();
    }

    public abstract void startUI();
}
