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

public class GameOverScreen {
    private VBox screenVBox;
    private int width;
    private int height;
    private Text title;
    private Font oldLondon;
    private Button playAgain;
    private Button exitGame;

    private HBox outerStatHBox;
    private VBox statVBox;
    private HBox buttonHBox;

    private Label monstersKilled = new Label("");
    private Label damageDealt= new Label("");
    private Label timeTaken = new Label("");

    public GameOverScreen(int width, int height) {
        this.width = width;
        this.height = height;

        setupTitle();
        setupStats();
        setupPlayAgain();
        setupTitleScreenPane();
    }

    // Setup game over screen
    private void setupTitle() {
        oldLondon = Font.loadFont(getClass().
                getResourceAsStream("/resources/fonts/oldLondon.ttf"), 80);

        title = new Text("Game Over");
        title.setId("gameOverText");
        title.setFont(oldLondon);
        title.setFill(Color.DARKRED);
    }
    private void setupStats() {
        outerStatHBox = new HBox();
        statVBox = new VBox();
        statVBox.setSpacing(20);

        monstersKilled.setFont(new Font(15));
        monstersKilled.setTextFill(Color.WHITE);
        damageDealt.setFont(new Font(15));
        damageDealt.setTextFill(Color.WHITE);
        timeTaken.setFont(new Font(15));
        timeTaken.setTextFill(Color.WHITE);
        statVBox.getChildren().addAll(monstersKilled, damageDealt, timeTaken);

        outerStatHBox.getChildren().add(statVBox);
        outerStatHBox.setAlignment(Pos.CENTER);
    }

    private void setupPlayAgain() {
        buttonHBox = new HBox();
        playAgain = new Button("Play Again?");
        playAgain.setId("playAgain");
        exitGame = new Button("Exit Game");
        exitGame.setId("exitGame");
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(40);
        buttonHBox.getChildren().addAll(playAgain, exitGame);
    }
    private void setupTitleScreenPane() {
        screenVBox = new VBox();
        screenVBox.setAlignment(Pos.CENTER);
        screenVBox.setSpacing(40);
        screenVBox.getChildren().addAll(title, outerStatHBox, buttonHBox);
        screenVBox.setStyle("-fx-background-color: #111");
    }

    public void updateTitle(String name) {
        String temp = name;
        if(temp.length() > 11) {
            temp = temp.substring(0, 11) + "...";
        }
        title.setText(temp + ", Game Over");
    }

    // Getters
    public Label getMonstersKilledLabel() {
        return monstersKilled;
    }

    public Label getDamageDealtLabel() {
        return damageDealt;
    }

    public Label getTimeTakenLabel() {
        return timeTaken;
    }

    public Scene getScene() {
        return new Scene(screenVBox, width, height);
    }
    public Button getPlayAgainButton() {
        return playAgain;
    }
    public Button getExitGameButton() {
        return exitGame;
    }

}
