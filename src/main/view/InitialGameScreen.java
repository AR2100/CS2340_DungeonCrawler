package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class InitialGameScreen {

    private int width;
    private int height;
    private VBox titleContainer;
    private HBox playerObjects;

    private Button exit1;

    private HeadsUpDisplay defaultHeadsUpDisplay;

    public InitialGameScreen(int width, int height) {
        this.width = width;
        this.height = height;

        initializeTitleContainer();
        initializePlayerObjects();
        initializeHeadsUpDisplay();
    }

    // Setup initial game screen
    private void initializeHeadsUpDisplay() {
        defaultHeadsUpDisplay = new HeadsUpDisplay();
    }
    private void initializeTitleContainer() {
        titleContainer = new VBox();
        Text initialTitle = new Text("Welcome to the Initial Game Screen!\n");
        initialTitle.setFont(new Font(30));
        initialTitle.setFill(Color.BLACK);

        titleContainer.getChildren().add(initialTitle);
        titleContainer.setAlignment(Pos.TOP_CENTER);
    }
    private void initializePlayerObjects() {
        playerObjects = new HBox();
        exit1 = new Button("Begin");

        Image bedImage = new Image(String.valueOf(
                getClass().getClassLoader().getResource("sprites/Bed.png")));
        ImageView bed = new ImageView(bedImage);
        bed.setFitHeight(200);
        bed.setFitWidth(200);

        playerObjects.getChildren().addAll(exit1, bed);
        playerObjects.setAlignment(Pos.CENTER);
    }

    // Getters and Setters
    public Scene getScene() {
        VBox screenPane = new VBox();

        screenPane.getChildren().addAll(defaultHeadsUpDisplay.getHeadsUpDisplay(),
                titleContainer,
                playerObjects);

        Scene scene = new Scene(screenPane, width, height);
        return scene;
    }
    public Button getExit1Button() {
        return exit1;
    }
}
