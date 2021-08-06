package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Stats;
import view.GameOverScreen;

import java.text.DecimalFormat;

public class GameOverScreenController {
    private GameOverScreen screen;
    private Controller controller;
    private Button playAgain;
    private Button exitGame;
    private Label monstersKilled;
    private Label damageDealt;
    private Label timeTaken;

    public GameOverScreenController(int screenWidth, int screenHeight, Controller controller) {
        this.controller = controller;
        screen = new GameOverScreen(screenWidth, screenHeight);
        screen.updateTitle(controller.getPlayerController().getPlayerModel().getPlayerName());
        monstersKilled = screen.getMonstersKilledLabel();
        damageDealt = screen.getDamageDealtLabel();
        timeTaken = screen.getTimeTakenLabel();
        DecimalFormat format = new DecimalFormat("0.#");

        monstersKilled.setText("Monsters Killed: " + Stats.getMonsterKilled() + " monsters");
        damageDealt.setText("Damage Dealt: " + format.format(Stats.getDamageDealt()) + " hearts");

        timeTaken.setText("Time Taken: " + format.format(Stats.getTimeTaken()) + " seconds");

        playAgain = screen.getPlayAgainButton();
        playAgain.setOnAction(e -> handlePlayAgainButton());
        exitGame = screen.getExitGameButton();
        exitGame.setOnAction(e -> handleExitGameButton());
    }

    // Handle clicking on the play again button
    private void handlePlayAgainButton() {
        try {
            controller.getRoomController().getPlayerController().resetPlayerData();
        } catch(NullPointerException e) {
            System.out.println("Started screen from debug menu");
        }
        Stats.resetStats();
        controller.initWelcomeScreen();
    }

    public void handleExitGameButton() {
        Platform.exit();
    }

    public Scene getScene() {
        return screen.getScene();
    }
}
