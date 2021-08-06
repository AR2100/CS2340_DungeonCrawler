package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WinScreen {
    private VBox screenVBox;
    private int width;
    private int height;
    private Text title;
    private Font oldLondon;
    private HBox buttonHBox;
    private Button playAgain;
    private Button closeGame;

    private HBox outerStatHBox;
    private VBox statVBox;
    private Label monstersKilled = new Label("");
    private Label damageDealt= new Label("");
    private Label timeTaken = new Label("");


    public WinScreen(int width, int height) {
        this.width = width;
        this.height = height;

        setupTitle();
        setupStats();
        setupButtons();
        setupTitleScreenPane();
    }

    // Setup win screen
    private void setupTitle() {
        oldLondon = Font.loadFont(getClass().
                getResourceAsStream("/resources/fonts/oldLondon.ttf"), 80);
        title = new Text("You Escaped!");
        title.setId("winText");
        title.setFont(oldLondon);
        title.setFill(Color.GREEN);
    }
    private void setupStats() {
        outerStatHBox = new HBox();
        statVBox = new VBox();
        statVBox.setSpacing(20);
        monstersKilled.setFont(new Font(15));
        damageDealt.setFont(new Font(15));
        timeTaken.setFont(new Font(15));
        statVBox.getChildren().addAll(monstersKilled, damageDealt, timeTaken);

        outerStatHBox.getChildren().add(statVBox);
        outerStatHBox.setAlignment(Pos.CENTER);
    }
    private void setupButtons() {
        buttonHBox = new HBox();
        playAgain = new Button("Start New Game?");
        playAgain.setId("playAgain");
        closeGame = new Button("Exit Game");
        closeGame.setId("closeGame");
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(40);
        buttonHBox.getChildren().addAll(playAgain, closeGame);
    }

    private void setupTitleScreenPane() {
        screenVBox = new VBox();
        screenVBox.setAlignment(Pos.CENTER);
        screenVBox.setSpacing(40);
        screenVBox.getChildren().addAll(title, outerStatHBox, buttonHBox);
    }

    public void updateTitle(String name) {
        String temp = name;
        if(temp.length() > 11) {
            temp = temp.substring(0, 11) + "...";
        }
        title.setText(temp + " Escaped!");
    }


    public Label getMonstersKilledLabel() {
        return monstersKilled;
    }

    public Label getDamageDealtLabel() {
        return damageDealt;
    }

    public Label getTimeTakenLabel() {
        return timeTaken;
    }
    // Getters
    public Scene getScene() {
        return new Scene(screenVBox, width, height);
    }
    public Button getPlayAgainButton() {
        return playAgain;
    }
    public Button getExitGameButton() {
        return closeGame;
    }
}
