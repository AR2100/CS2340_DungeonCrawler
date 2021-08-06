package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ChallengeRoomPromptScreen {
    private int width;
    private int height;
    private Font oldLondon;
    private VBox screenVBox;
    private Button startChallenge;
    private Button declineChallenge;

    public ChallengeRoomPromptScreen(int width, int height) {
        this.width = width;
        this.height = height;

        setUpChallengeRoomPrompt();
    }

    public void setUpChallengeRoomPrompt() {
        screenVBox = new VBox();
        screenVBox.setAlignment(Pos.CENTER);
        screenVBox.setSpacing(20);

        startChallenge = new Button("Begin Challenge!");
        declineChallenge = new Button("Decline Challenge.");

        screenVBox.getChildren().addAll(startChallenge, declineChallenge);
    }

    public Scene getScene() {
        return new Scene(screenVBox, width, height);
    }

    public Button getStartChallenge() {
        return startChallenge;
    }
    public Button getDeclineChallenge() {
        return declineChallenge;
    }
}
