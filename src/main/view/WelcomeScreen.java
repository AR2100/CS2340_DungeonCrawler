package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



public class WelcomeScreen {

    private VBox screenVBox;
    private int width;
    private int height;

    private Label title;
    private Font oldLondon;

    private VBox buttonVBox;
    private Button startButton;
    private ChoiceBox debugMenu;

    public WelcomeScreen(int width, int height) {
        this.width = width;
        this.height = height;

        setupTitle();
        setupButtons();
        setupTitleScreenPane();

        screenVBox.getStylesheets().add("resources/css/WelcomeScreen.css");
    }

    // Setup welcome screen
    private void setupTitle() {
        oldLondon = Font.loadFont(getClass().
                getResourceAsStream("/resources/fonts/oldLondon.ttf"), 100);
        title = new Label("Potions & Rubies\n\n");
        title.setFont(oldLondon);
        title.setTextFill(Color.DARKRED);
        title.setId("title");

    }
    private void setupButtons() {
        buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.setMinSize(200, 50);
        buttonVBox.setSpacing(20);

        startButton = new Button("Start");

        debugMenu = new ChoiceBox();
        debugMenu.setId("debugMenu");

        buttonVBox.getChildren().addAll(startButton, debugMenu);
    }
    private void setupTitleScreenPane() {
        screenVBox = new VBox();
        screenVBox.setAlignment(Pos.CENTER);
        screenVBox.setSpacing(20);
        screenVBox.setId("screen");

        screenVBox.getChildren().addAll(title, buttonVBox);
    }

    // Getters
    public Scene getScene() {
        return new Scene(screenVBox, width, height);
    }
    public Button getStartButton() {
        return startButton;
    }
    public ChoiceBox getDebugMenu() {
        return debugMenu;
    }
}
