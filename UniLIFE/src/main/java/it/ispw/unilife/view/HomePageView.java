package it.ispw.unilife.view;

import it.ispw.unilife.bean.UserBean;
import it.ispw.unilife.view.decorator.*;
import javafx.event.ActionEvent; // <--- CORRETTO (Era java.awt.event.ActionEvent)
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomePageView {

    private Pane createDecoratedHome(UserBean userBean) {
        ViewComponent home = new BaseHomeView();

        if (userBean == null) {
            home = new GuestHomeDecorator(home);
        } else {
            // Switch sul ruolo usando l'enum Role
            switch (userBean.getRole()) {
                case STUDENT:
                    home = new StudentHomeDecorator(home);
                    break;
                case TUTOR:
                    home = new TutorHomeDecorator(home);
                    break;
                // case EMPLOYEE: ...
                default:
                    home = new GuestHomeDecorator(home);
                    break;
            }
        }
        return home.draw();
    }

    // Overload 1: Per lo start dell'app (riceve Stage)
    public void displayHome(UserBean userBean, Stage stage) {
        Pane finalView = createDecoratedHome(userBean);
        Navigator.getNavigatorInstance().show(stage, finalView);
    }

    // Overload 2: Per i click sui bottoni (riceve Event)
    public void displayHome(UserBean userBean, ActionEvent event) {
        Pane finalView = createDecoratedHome(userBean);
        Navigator.getNavigatorInstance().show(event, finalView);
    }
}