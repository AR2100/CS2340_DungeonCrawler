package controller;

import javafx.scene.Scene;
import view.InitialGameScreen;
import javafx.scene.control.Button;

/** This class is the controller for all of the GUI
 * objects to be run in the initial game screen.
 *
 * InitialGameScreenController fills out the HUD with the information from the PlayerModel.
 *  Then, InitialGameScreenController is responsible for moving into Room when the player
 *  clicks on the "Begin" button.
 *
 * @version 1.0
 * @author Andrew Roach, Rishi Chillara, Amit Kulkarni, Joe Wilmot
 */
public class InitialGameScreenController {

    private InitialGameScreen screen;
    private Button exit1Button;
    private Controller controller;

    public InitialGameScreenController(int screenWidth, int screenHeight, Controller controller) {
        this.controller = controller;
        screen = new InitialGameScreen(screenWidth, screenHeight);
        exit1Button = screen.getExit1Button();
        exit1Button.setOnAction(e -> handleExitButton());

    }


    // Handle clicking on the exit button
    private void handleExitButton() {
        controller.initRoom("Starting Room");
    }

    // Getters
    public Scene getScene() {
        return screen.getScene();
    }
}
