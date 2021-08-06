package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import resources.Difficulty;


/** This class details the visual aspects of the Customization Screen.
 *
 *  @author Rishi Chillara, Andrew Roach, Amit Kulkarni, Joe Wilmot
 *  @version 1.0
 */
public class CustomizationScreen {

    private VBox screenVBox;

    private HBox imageAndCustomizeHBox;

    private VBox customizeBox;
    private int width;
    private int height;

    private Text title;
    private Font oldLondon;

    private Image playerSprite;
    private ImageView playerSpriteView;

    private HBox characterNameHBox;
    private Label nameTitle;
    private TextField nameField;

    private HBox difficultyHBox;
    private Label difficultyTitle;

    private VBox weaponSelectVBox;
    private Label weaponTitle;
    private ToggleGroup weapons;
    private RadioButton weapon1;
    private RadioButton weapon2;
    private RadioButton weapon3;

    private ChoiceBox difficultyChoice;

    private Button startGameButton;
    private Button goBackButton;

    private HBox buttonHBox;

    private Text selectionIssue;
    private boolean hasSelectionIssue;

    /**
     * Constructor for the customization screen. Loads all of
     *  the visual aspects.
     *
     * @param width width of the JavaFX window
     * @param height height of the JavaFX window
     */
    public CustomizationScreen(int width, int height) {
        this.width = width;
        this.height = height;

        hasSelectionIssue = false;

        setupTitleScreenItem();
        setupPlayerImageSprite();
        setupNameFieldScreenItem();
        setupDifficultyScreenItem();
        setupWeaponSelectionScreenItem();
        setupFinishedButtonScreenItem();
        setupAlertScreenItem();
        setupTwoButtonHBox();

        setupCustomizationVBox();
        setupImageCustomizeBox();
        setupScreenBox();
    }

    /**
     * If the player inputs an invalid name or fails to make a weapon selection,
     *  an alert pops up informing the player to fill out the required fields.
     *
     * @param problem the String to display in the alert
     */
    public void showAlert(String problem) {
        selectionIssue.setText(problem);
        hasSelectionIssue = true;
    }

    // Setup screen elements
    private void setupTitleScreenItem() {
        oldLondon =
                Font.loadFont(getClass().getResourceAsStream("/resources/fonts/oldLondon.ttf"), 75);

        title = new Text("Customize Your Game");
        title.setFont(oldLondon);
        title.setFill(Color.DARKRED);
    }
    // Setup player image sprite
    private void setupPlayerImageSprite() {
        playerSprite = new Image("sprites/Player.png", 250, 250, true, true);
        playerSpriteView = new ImageView(playerSprite);
    }
    private void setupNameFieldScreenItem() {
        characterNameHBox = new HBox();
        characterNameHBox.setMinSize(200, 50);

        nameTitle = new Label("Character's name: ");

        nameField = new TextField();

        characterNameHBox.getChildren().addAll(nameTitle, nameField);
    }
    private void setupDifficultyScreenItem() {
        difficultyHBox = new HBox();
        difficultyHBox.setMinSize(200, 50);

        difficultyTitle = new Label("Game difficulty: ");

        difficultyChoice = new ChoiceBox();
        difficultyChoice.getItems().addAll(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD);
        difficultyChoice.setValue(Difficulty.EASY);

        difficultyHBox.getChildren().addAll(difficultyTitle, difficultyChoice);
    }
    private void setupWeaponSelectionScreenItem() {
        weaponSelectVBox = new VBox();
        weaponSelectVBox.setMinSize(200, 50);
        weaponSelectVBox.setSpacing(20);

        weaponTitle = new Label("Weapon choice: ");

        weapons = new ToggleGroup();
        weapon1 = new RadioButton("Sword");
        weapon2 = new RadioButton("Dagger");
        weapon3 = new RadioButton("Mace");
        weapon1.setToggleGroup(weapons);
        weapon2.setToggleGroup(weapons);
        weapon3.setToggleGroup(weapons);

        weaponSelectVBox.getChildren().addAll(weaponTitle, weapon1, weapon2, weapon3);
    }
    private void setupFinishedButtonScreenItem() {
        goBackButton = new Button("Back");
        startGameButton = new Button("Next");
    }
    private void setupAlertScreenItem() {
        selectionIssue = new Text("");
        selectionIssue.setFont(new Font(15));
        selectionIssue.setFill(Color.RED);
    }

    private void setupTwoButtonHBox() {
        buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(150);
        buttonHBox.getChildren().addAll(goBackButton, startGameButton);
    }

    /* Sets up the VBox that contains all of the screen elements on the
     *  customization screen.
     */
    private void setupCustomizationVBox() {
        customizeBox = new VBox();
        customizeBox.setSpacing(5);
        customizeBox.setId("customizationScreen");



        customizeBox.getChildren().addAll(characterNameHBox,
                                        difficultyHBox,
                                        weaponSelectVBox);
    }

    private void setupImageCustomizeBox() {
        imageAndCustomizeHBox = new HBox();
        imageAndCustomizeHBox.setAlignment(Pos.CENTER);
        imageAndCustomizeHBox.setSpacing(25);
        imageAndCustomizeHBox.setId("imageAndCustomizeHBox");


        imageAndCustomizeHBox.getChildren().addAll(playerSpriteView, customizeBox);
    }

    private void setupScreenBox() {
        screenVBox = new VBox();
        screenVBox.setMinSize(700, 700);
        screenVBox.setAlignment(Pos.TOP_CENTER);
        screenVBox.setSpacing(25);
        screenVBox.setId("screenVBox");
        screenVBox.getChildren().addAll(title, imageAndCustomizeHBox, buttonHBox, selectionIssue);
        screenVBox.getStylesheets().add("resources/css/CustomizationScreen.css");
    }

    // Getters and setters
    public Scene getScene() {
        return new Scene(screenVBox, width, height);
    }
    public TextField getNameField() {
        return nameField;
    }
    public ChoiceBox getDifficultyChoiceBox() {
        return difficultyChoice;
    }
    public ToggleGroup getWeaponsToggleGroup() {
        return weapons;
    }
    public Button getStartGameButton() {
        return startGameButton;
    }
    public Button getGoBackButton() {
        return goBackButton;
    }

}
