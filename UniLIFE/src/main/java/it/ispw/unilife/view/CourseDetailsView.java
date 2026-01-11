package it.ispw.unilife.view;

import it.ispw.unilife.bean.CourseBean;
import it.ispw.unilife.model.CourseType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class CourseDetailsView {

    @FXML
    private Label courseNameLabel;
    @FXML private Label universityLabel;
    @FXML private Label facultyLabel;
    @FXML private Label typeLabel;
    @FXML private Label languageLabel;
    @FXML private Label costLabel;
    @FXML private FlowPane tagsContainer;

    private CourseBean selectedCourse;

    public void initData(CourseBean courseBean) {
        this.selectedCourse = courseBean;

        courseNameLabel.setText(courseBean.getName());
        universityLabel.setText(courseBean.getUniversity());
        facultyLabel.setText(courseBean.getFaculty());
        typeLabel.setText(CourseType.courseTypeToString(courseBean.getCourseType()));
        languageLabel.setText(courseBean.getLanguage());

        String beanCost = courseBean.getAnnualCost();
        String cleanCost = beanCost.replaceAll("[^0-9]", "");

        try {
            if (!cleanCost.isEmpty() && Integer.parseInt(cleanCost) > 0) {
                costLabel.setText("€ " + cleanCost);
            } else {
                costLabel.setText("N/A");
            }
        } catch (NumberFormatException e) {
            costLabel.setText("N/A");
        }

        populateTags(courseBean);

    }

    @FXML
    public void onBackClick(ActionEvent event) {
        Navigator.getNavigatorInstance().goToExplorePage(event);
    }

    private void populateTags(CourseBean course) {
        tagsContainer.getChildren().clear();

        if (course.getTags() == null || course.getTags().isEmpty()) {
            Label noTags = new Label("No Tags");
            noTags.setTextFill(Color.GRAY);
            tagsContainer.getChildren().add(noTags);
            return;
        }


        for (String tag : course.getTags()) {
            Label tagLabel = new Label("#" + tag);
            tagLabel.setStyle(
                    "-fx-background-color: #e3f2fd;" +
                            "-fx-text-fill: #006680;" +
                            "-fx-background-radius: 15;" +
                            "-fx-padding: 5 12 5 12;" +
                            "-fx-font-weight: bold;"
            );

            tagLabel.setFont(new Font(13));
            tagsContainer.getChildren().add(tagLabel);
        }
    }
}

