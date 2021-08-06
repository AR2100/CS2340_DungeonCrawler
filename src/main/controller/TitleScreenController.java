package controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import resources.Screen;
import view.WelcomeScreen;

/** This class is the controller for all of the GUI
 * objects to be run in the title screen.
 *
 * @version 1.0
 * @author Andrew Roach, Rishi Chillara, Amit Kulkarni, Joe Wilmot
 */
public class TitleScreenController {

    private WelcomeScreen screen;

    private Button startButton;
    private ChoiceBox debugMenu;

    private Controller controller;

    /**
     * This is a constructor that sets up the screen
     * for the title to be set up in.
     *
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     * @param controller the controller to control the screen
     */
    public TitleScreenController(int screenWidth, int screenHeight,
                                 Controller controller, boolean testMode) {
        this.controller = controller;
        screen = new WelcomeScreen(screenWidth, screenHeight);

        startButton = screen.getStartButton();
        debugMenu = screen.getDebugMenu();
        debugMenu.getItems().addAll(Screen.TITLE,
                                    Screen.CUSTOMIZATION,
                                    Screen.INITIAL,
                                    Screen.ROOM,
                                    Screen.BOSS_ROOM,
                                    Screen.GAME_OVER,
                                    Screen.WIN_SCREEN);

        startButton.setOnAction(e -> controller.initCustomizationScreen());
        debugMenu.setOnAction(e -> selectDebugScreen());

        if (testMode == true) {
            debugMenu.setVisible(true);
        } else {
            debugMenu.setVisible(false);
        }
    }

    // setup handling for debug dropdown
    private void selectDebugScreen() {
        Screen screenType = (Screen) debugMenu.getValue();

        if (screenType == Screen.TITLE) {
            controller.initWelcomeScreen();
        } else if (screenType == Screen.CUSTOMIZATION) {
            controller.initCustomizationScreen();
        } else if (screenType == Screen.INITIAL) {
            controller.initFirstScreen();
        } else if (screenType == Screen.ROOM) {
            controller.initRoom("Starting Room");
        } else if (screenType == Screen.BOSS_ROOM) {
            controller.initRoom("Starting Room");
            controller.getPlayerController().getPlayerModel().setCurrentRoom(12);
        } else if (screenType == Screen.GAME_OVER) {
            controller.initGameOverScreen();
        } else if (screenType == Screen.WIN_SCREEN) {
            controller.initWinScreen();
        }
    }

    // Getters
    public Scene getScene() {
        return screen.getScene();
    }
}
