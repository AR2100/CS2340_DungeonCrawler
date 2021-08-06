package controller;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import view.ChallengeRoomPromptScreen;

import java.util.ArrayList;



public class ChallengeRoomPromptController {

    private Controller controller;
    private ChallengeRoomPromptScreen screen;
    private PlayerController playerController;

    public ChallengeRoomPromptController(int screenWidth, int screenHeight, Controller controller) {
        this.controller = controller;
        screen = new ChallengeRoomPromptScreen(screenWidth, screenHeight);

        screen.getStartChallenge().setOnAction(e -> handleAccept());
        screen.getDeclineChallenge().setOnAction(e -> handleDecline());
    }

    public void handleUseItem() {


        // close inventory screen
        controller.hideInventory();
    }

    private void handleAccept() {
        controller.getRoomController().toggleChallengeRoom(false);
        controller.getRoomController().setChallengeRoomComplete();
        controller.getRoomController().setChallengeActive(true);
        controller.getRoomController().beginChallenge();

        controller.getMonsterController().createChallenge();

        controller.hideChallengeRoomPrompt();
    }

    private void handleDecline() {
        controller.getRoomController().setChallengeDeclined(true);
        controller.hideChallengeRoomPrompt();
    }

    public Scene getScene() {
        return screen.getScene();
    }
}
