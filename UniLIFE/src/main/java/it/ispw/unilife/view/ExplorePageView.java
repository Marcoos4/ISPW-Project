package it.ispw.unilife.view;

import it.ispw.unilife.bean.CourseBean;
import it.ispw.unilife.bean.FilterSearchBean;
import it.ispw.unilife.bean.InterestSearchBean;
import it.ispw.unilife.controller.ExploreCoursesController;
import it.ispw.unilife.view.Navigator;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.sql.rowset.spi.SyncFactory;
import java.util.List;

public class ExplorePageView {

    @FXML private TextField searchBar;
    @FXML private ComboBox<String> universityCombo;
    @FXML private ComboBox<String> courseTypeCombo;
    @FXML private ComboBox<String> facultyCombo;
    @FXML private VBox resultsContainer;

    private final ExploreCoursesController appController;

    public ExplorePageView() {
        this.appController = new ExploreCoursesController();
    }

    @FXML
    public void initialize() {

        universityCombo.getItems().addAll("Sapienza", "Tor Vergata", "Roma Tre");
        courseTypeCombo.getItems().addAll("Laurea Triennale", "Laurea Magistrale");

    }

    @FXML
    public void onHomeClick() {
        Navigator.getNavigatorInstance().goToHome();
    }

    @FXML
    public void onSearchClick() {
//        String query = searchBar.getText();
//        InterestSearchBean bean = new InterestSearchBean(query);
//        List<CourseBean> results = appController.searchByInterest(bean);
//
//        updateResultsView(results);
    }

    @FXML
    public void onApplyFiltersClick() {
        String uni = universityCombo.getValue();
        String type = courseTypeCombo.getValue();
        String faculty = facultyCombo.getValue();

        FilterSearchBean bean = new FilterSearchBean(uni, null, faculty, type, null, 0);

        List<CourseBean> results = appController.searchCoursesByFilters(bean);

        updateResultsView(results);
    }

    private void updateResultsView(List<CourseBean> courses) {
        resultsContainer.getChildren().clear();

        if (courses.isEmpty()) {
            Label placeholder = new Label("Nessun corso trovato.");
            placeholder.setFont(Font.font(18));
            resultsContainer.getChildren().add(placeholder);
            return;
        }

        for (CourseBean course : courses) {
            HBox card = createCourseCard(course);
            resultsContainer.getChildren().add(card);
        }
    }

    private HBox createCourseCard(CourseBean courseBean) {
        HBox card = new HBox();
        card.setSpacing(20);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER_LEFT);

        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");


        card.setCursor(javafx.scene.Cursor.HAND);


        VBox infoBox = new VBox(5);
        Label nameLbl = new Label(courseBean.getName());
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 18));
        nameLbl.setTextFill(javafx.scene.paint.Color.valueOf("#1a1a1a"));

        Label uniLbl = new Label(courseBean.getUniversity() + " - " + courseBean.getFaculty());
        uniLbl.setTextFill(javafx.scene.paint.Color.GRAY);

        infoBox.getChildren().addAll(nameLbl, uniLbl);
        HBox.setHgrow(infoBox, Priority.ALWAYS); // Spinge il bottone a destra

        Button viewBtn = new Button("Vedi Dettagli");
        viewBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff914d; -fx-border-color: #ff914d; -fx-border-radius: 20;");

        card.getChildren().addAll(infoBox, viewBtn);

        card.setOnMouseClicked(event -> {
            Navigator.getNavigatorInstance().goToCourseDetails(courseBean);
        });

        return card;
    }
}