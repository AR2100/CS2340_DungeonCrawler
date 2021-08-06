package controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import model.PlayerModel;
import resources.Difficulty;
import resources.DifficultyConstants;
import resources.WeaponEnum;
import view.CustomizationScreen;

/** This class is the controller for all of the GUI
 * objects to be run in the customization screen.
 *
 * CustomizationScreenController is responsible for filling out the player's information when
 *  the player has filled out all of the appropriate fields. When finished customizing,
 *  CustomizationScreenController is also responsible for moving on to the initial game screen.
 *
 * @version 1.0
 * @author Andrew Roach, Rishi Chillara, Amit Kulkarni, Joe Wilmot
 */
public class CustomizationScreenController {

    private  CustomizationScreen screen;
    private TextField nameField;
    private ChoiceBox difficultyChoiceBox;
    private ToggleGroup weaponsToggleGroup;
    private Button goBackButton;
    private Button startButton;
    private Controller controller;

    public CustomizationScreenController(int screenWidth, int screenHeight, Controller controller) {
        this.controller = controller;
        screen = new CustomizationScreen(screenWidth, screenHeight);
        nameField = screen.getNameField();
        difficultyChoiceBox = screen.getDifficultyChoiceBox();
        weaponsToggleGroup = screen.getWeaponsToggleGroup();

        goBackButton = screen.getGoBackButton();
        startButton = screen.getStartGameButton();

        goBackButton.setOnAction(e -> {
            controller.initWelcomeScreen();
        });

        startButton.setOnAction(e -> {
            handleFinishedCustomizingButton();
        });
    }

    // Handle clicking on the done button
    private void handleFinishedCustomizingButton() {
        boolean invalidName = false;
        boolean noDifficultySelected = false;
        boolean noWeaponSelected = false;

        if (nameField.getText().length() == 0) {
            invalidName = true;
        } else {
            String spaces = new String();
            for (int i = 0; i < nameField.getText().length(); i++) {
                spaces += " ";
            }
            if (nameField.getText().equals(spaces)) {
                invalidName = true;
            }
        }

        if (difficultyChoiceBox.getValue() == null) {
            noDifficultySelected = true;
        }

        if (weaponsToggleGroup.getSelectedToggle() == null) {
            noWeaponSelected = true;
        }

        if (invalidName) {
            screen.showAlert("The character's name cannot be empty or only whitespace.");
        } else if (noDifficultySelected) {
            screen.showAlert("Please select a difficulty.");
        } else if (noWeaponSelected) {
            screen.showAlert("Please select a weapon.");
        } else {
            PlayerModel playerModel = controller.getPlayerController().getPlayerModel();
            playerModel.setPlayerName(nameField.getText());

            //playerModel.setWeapon(((RadioButton) weaponsToggleGroup.
            // getSelectedToggle()).getText());
            String weapon = ((RadioButton) weaponsToggleGroup.getSelectedToggle()).getText();
            WeaponEnum weaponEnum = WeaponEnum.NULL;

            if (weapon.equalsIgnoreCase("sword")) {
                weaponEnum = WeaponEnum.BROADSWORD;
            } else if (weapon.equalsIgnoreCase("dagger")) {
                weaponEnum = WeaponEnum.DAGGER;
            } else if (weapon.equalsIgnoreCase("mace")) {
                weaponEnum = WeaponEnum.MACE;
            } else {
                System.out.println("No weapon selected!");
            }

            playerModel.clearPlayerWeapons();

            playerModel.addWeapon(weaponEnum);
            playerModel.setEquippedWeapon(weaponEnum);

            Difficulty difficulty =  (Difficulty) difficultyChoiceBox.getValue();
            playerModel.setDifficulty(difficulty);

            int startingAmount = DifficultyConstants.getStartingRuby(difficulty);
            playerModel.setRuby(startingAmount);

            controller.initRoom("Starting Room");
        }
    }

    // Getters
    public Scene getScene() {
        return screen.getScene();
    }
}
