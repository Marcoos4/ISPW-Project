package it.ispw.unilife.view;

import it.ispw.unilife.model.Course;
import it.ispw.unilife.model.CourseType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import it.ispw.unilife.bean.CourseBean;
import it.ispw.unilife.bean.FilterSearchBean;
import it.ispw.unilife.controller.ExploreCoursesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ExplorePageView {

    @FXML private TextField searchBar;
    @FXML private ComboBox<String> universityCombo;
    @FXML private ComboBox<String> facultyCombo;
    @FXML private ComboBox<String> courseTypeCombo;
    @FXML private ComboBox<String> languageCombo;

    @FXML private TextField maxCostField;

    @FXML private VBox resultsContainer;

    private final ExploreCoursesController appController;

    public ExplorePageView() {
        this.appController = new ExploreCoursesController();
    }

    @FXML
    public void initialize() {
        findFilters();
    }

    @FXML
    public void onHomeClick(ActionEvent event) {
        Navigator.getNavigatorInstance().goToHome(event);
    }

    @FXML
    public void onSearchClick() {
        CourseBean courseBean = new CourseBean();
        courseBean.setName(searchBar.getText());
        List<CourseBean> results = appController.searchCourseByName(courseBean);
        updateResultsView(results);

    }

    @FXML
    public void onApplyFiltersClick() {
        FilterSearchBean filterBean = convertFiltersToBean();
        List<CourseBean> results = appController.searchCoursesByFilters(filterBean);
        updateResultsView(results);
    }

    @FXML
    public void onResetFiltersClick() {

        resultsContainer.getChildren().clear();
        universityCombo.getSelectionModel().clearSelection();
        universityCombo.setValue(null);

        facultyCombo.getSelectionModel().clearSelection();
        facultyCombo.setValue(null);

        courseTypeCombo.getSelectionModel().clearSelection();
        courseTypeCombo.setValue(null);

        languageCombo.getSelectionModel().clearSelection();
        languageCombo.setValue(null);

        maxCostField.clear();
        maxCostField.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 5;");

    }

    private FilterSearchBean convertFiltersToBean() {
        String uni = universityCombo.getValue();
        String faculty = facultyCombo.getValue();
        String type = courseTypeCombo.getValue();
        String lang = languageCombo.getValue();
        String nation = null;

        int maxCost = -1;

        String costText = maxCostField.getText();
        if (costText != null && !costText.trim().isEmpty()) {
            try {
                maxCost = Integer.parseInt(costText.trim());

                if (maxCost < 0) maxCost = -1;

            } catch (NumberFormatException e) {
                System.err.println("Input costo non valido, filtro ignorato.");
                maxCost = -1;
                maxCostField.setStyle("-fx-border-color: red; -fx-background-color: #f4f4f4;");
            }
        } else {
            maxCostField.setStyle("-fx-background-color: #f4f4f4;");
        }

        return new FilterSearchBean(uni, lang, faculty, type, nation, maxCost);
    }

    private void updateResultsView(List<CourseBean> courses) {
        resultsContainer.getChildren().clear();

        if (courses.isEmpty()) {
            Label placeholder = new Label("No Course Found!");
            placeholder.setFont(Font.font(18));
            resultsContainer.getChildren().add(placeholder);
            return;
        }

        for (CourseBean course : courses) {
            HBox card = createCourseCard(course);
            resultsContainer.getChildren().add(card);
        }
    }

    private HBox createCourseCard(CourseBean course) {
        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");

        VBox infoBox = new VBox(5);
        Label title = new Label(course.getName());
        title.setFont(new Font("System Bold", 18));
        title.setTextFill(Color.web("#006680"));

        Label uni = new Label(course.getUniversity() + " - " + course.getFaculty());
        uni.setTextFill(Color.GRAY);

        Label details = new Label(CourseType.degreeTypeToString(course.getCourseType()) + " | " + course.getLanguage()
                + " | Costo ~€" + course.getAnnualCost());
        details.setFont(new Font(12));

        infoBox.getChildren().addAll(title, uni, details);

        card.getChildren().add(infoBox);

        card.setOnMouseClicked(event -> {
            System.out.println("Find Details : " + course.getName());
            // Navigator.getNavigatorInstance().goToCourseDetails(course);
        });

        return card;
    }

    private void findFilters() {
        // FACULTIES
        List<FilterSearchBean> facultyBeans = appController.getAvailableFaculties();
        List<String> facultyStrings = new ArrayList<>();

        for (FilterSearchBean bean : facultyBeans) {
            if (bean.getFaculty() != null) {
                facultyStrings.add(bean.getFaculty());
            }
        }
        facultyCombo.getItems().setAll(facultyStrings);

        // UNIVERSITIES
        List<FilterSearchBean> uniBeans = appController.getAvailableUniversities();
        List<String> uniStrings = new ArrayList<>();

        for (FilterSearchBean bean : uniBeans) {
            if (bean.getUniversityName() != null) {
                uniStrings.add(bean.getUniversityName());
            }
        }
        universityCombo.getItems().setAll(uniStrings);

        // COURSE TYPES
        List<FilterSearchBean> typeBeans = appController.getAvailableCourseTypes();
        List<String> typeStrings = new ArrayList<>();

        for (FilterSearchBean bean : typeBeans) {
            if (bean.getCourseType() != null) {
                typeStrings.add(bean.getCourseType());
            }
        }
        courseTypeCombo.getItems().setAll(typeStrings);


        List<FilterSearchBean> langBeans = appController.getAvailableLanguages();
        List<String> langStrings = new ArrayList<>();

        for (FilterSearchBean bean : langBeans) {
            if (bean.getLanguage() != null) {
                langStrings.add(bean.getLanguage());
            }
        }

        languageCombo.getItems().setAll(langStrings);
    }
}