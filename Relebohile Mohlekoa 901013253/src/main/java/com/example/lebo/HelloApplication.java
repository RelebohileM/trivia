package com.example.lebo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class HelloApplication extends Application {

    private int currentQuestionIndex = 0;
    private int score = 0;

    private Label questionLabel;
    private Button[] optionButtons;

    private String[] questions = {
            "What is the capital city of Lesotho?",
            "Which mountain range covers a significant portion of Lesotho?",
            "What is the traditional Basotho blanket called?",
            "Which river forms part of Lesotho's border with South Africa?",
            "What is the name of Lesotho's currency?"
    };

    private String[][] options = {
            {"Maseru", "Manzini", "Maputo", "Mbabane"},
            {"Atlas Mountains", "Drakensberg Mountains", "Maloti Mountains", "Simien Mountains"},
            {"Lethoso", "Sesotho", "Lesotho", "Basotho"},
            {"Limpopo River", "Zambezi River", "Caledon River", "Orange River"},
            {"Rand", "Lilangeni", "Pula", "Loti"}
    };

    private int[] correctAnswers = {0, 2, 3, 2, 3};
    int count;
    Scene scene;
    Image[] image;
    Button button;
    ImageView imageView;
    BorderPane borderPane;
    private String css = getClass().getResource("style.css").toExternalForm().toString();
     String [] images = {getClass().getResource("1.jpeg").toExternalForm().toString(),
                                getClass().getResource("2.jpeg").toExternalForm().toString(),
                                getClass().getResource("3.jpeg").toExternalForm().toString(),
                                getClass().getResource("4.jpeg").toExternalForm().toString(),
                                getClass().getResource("5.jpeg").toExternalForm().toString(),
                                };
     String link = "/home/molemahang/IdeaProjects/lebo/src/main/resources/com/example/lebo/VID-20240415-WA0023";
    @Override
    public void start(Stage primaryStage) {

        File file = new File(link);
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(540);
        mediaView.setFitWidth(540);
        VBox vBox2 = new VBox();

        primaryStage.setTitle("Lesotho Trivia Game");

        questionLabel = new Label();
        questionLabel.setWrapText(true);
        optionButtons = new Button[4];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new Button();
            optionButtons[i].setPrefWidth(200);
            int finalI = i;
            optionButtons[i].setOnAction(event -> checkAnswer(finalI,primaryStage));
        }
        questionLabel.setTextFill(Color.WHEAT);
        borderPane = new BorderPane();
        HBox hBox = new HBox(10);
        hBox.getStyleClass().add("hb");
        VBox hBox1 = new VBox(10);
        hBox1.getStyleClass().add("hb1");
        hBox.getChildren().add(questionLabel);
        hBox1.getChildren().addAll(optionButtons);
        VBox vbox = new VBox(10);
        vBox2.setPadding(new Insets(40));
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(hBox,hBox1);

        borderPane.setTop(hBox);
        vBox2.getChildren().addAll(mediaView,hBox1);
        borderPane.setCenter(vBox2);


        image = new Image[images.length];
        for (int i = 0; i < images.length; i++)
        {
            image[i]= new Image(images[i]);

        }
        button =new Button("10");
        VBox vBox1 = new VBox(10);
        Label label1 = new Label("Timer");
        vBox1.getStyleClass().add("vbn");
        vBox1.getChildren().addAll(label1,button);
        button.getStyleClass().add("bton");

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int time = Integer.parseInt(button.getText());
            if (time>0) {

                button.setText(String.valueOf(--time));

                if (time == 0) {
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length - 1) {

                        updateQuestion(primaryStage);
                        imageView.setImage(image[currentQuestionIndex]);

                    }else {
                        showResult();



                    }
                }

            }
            else{
                button.setText("10"); // Reset the timer to 10

            }
        }));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.playFromStart();
        borderPane.setRight(vBox1);
        updateQuestion(primaryStage);
        borderPane.getStyleClass().add("pane");
        scene = new Scene(borderPane, 1400, 610);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(css);
        primaryStage.show();
    }

    private void updateQuestion(Stage stage) {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex]);
            imageView = new ImageView(image[count]);
            imageView.setX(1300);
            imageView.setY(700);
            imageView.setFitWidth(500);
            imageView.setFitHeight(300);
            VBox vBox  = new VBox();
            vBox.getChildren().add(imageView);
            vBox.setPadding(new Insets(40));
            borderPane.setLeft(vBox);
            imageView.getStyleClass().add("image");
            for (int i = 0; i < optionButtons.length; i++) {
                optionButtons[i].setText(options[currentQuestionIndex][i]);

            }
            count++;
        } else {
            showResult();
            stage.close();

        }
    }

    private void checkAnswer(int selectedOption, Stage stage) {
        if (selectedOption == correctAnswers[currentQuestionIndex]) {
            score++;
            button.setText("10");


        }
        currentQuestionIndex++;
        updateQuestion(stage);
        button.setText("10");

    }

    private void showResult() {
        Label resultLabel = new Label("Quiz Completed!\nYour Score: " + score + "/" + questions.length);
        VBox resultVBox = new VBox(10);
        resultVBox.setPadding(new Insets(20));
        resultVBox.getChildren().addAll(resultLabel);
        resultVBox.getStyleClass().add("results");
        resultLabel.getStyleClass().add("labelresult");
        Scene sceneR = new Scene(resultVBox, 1200, 600);
        Stage resultStage = new Stage();
        sceneR.getStylesheets().add(css);
        resultStage.setTitle("Quiz Result");
        resultStage.setScene(sceneR);
        resultStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
