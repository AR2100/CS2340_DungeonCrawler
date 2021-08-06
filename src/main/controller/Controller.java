package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameLayer;
import model.Stats;

/** This class is the controller for all of the GUI
 * objects to be run in the dungeon crawler.
 *
 * @version 1.0
 * @author Andrew Roach, Rishi Chillara, Amit Kulkarni, Joe Wilmot
 */
public class Controller extends Application {

    @FXML
    private GridPane gameWindow;
    private int screenWidth = 800;
    private int screenHeight = 450;
    private Stage mainWindow;
    private Stage inventoryStage;
    private Stage shopStage;
    private Stage challengeRoomPromptStage;
    private static double frameMills = 16.6;

    private boolean testMode = false;

    private PlayerController playerController;
    private MonsterController monsterController;
    private HeadsUpDisplayController headsUpDisplayController;
    private RoomController screenController;

    private Timeline update;
    private Timeline updateSeconds;

    private boolean isPaused;

    @Override
    public void start(Stage stage) {
        mainWindow = stage;
        mainWindow.setTitle("Potions & Rubies");

        // isTesting(false);

        mainWindow.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number>
                      observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
            }
        });
        mainWindow.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number>
                      observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: " + newSceneHeight);
            }
        });

        playerController = new PlayerController(this);
        headsUpDisplayController = new HeadsUpDisplayController(this);
        monsterController = new MonsterController(this);

        initWelcomeScreen();

        GameLayer.removeAllSprites();
    }

    // Initialization methods
    public void initWelcomeScreen() {
        TitleScreenController screenController =
                new TitleScreenController(screenWidth, screenHeight, this, testMode);

        mainWindow.setScene(screenController.getScene());
        mainWindow.show();
    }
    public void initCustomizationScreen() {
        CustomizationScreenController screenController =
                new CustomizationScreenController(screenWidth, screenHeight, this);

        mainWindow.setScene(screenController.getScene());
        mainWindow.show();
    }
    public void initFirstScreen() {
        InitialGameScreenController screenController =
                new InitialGameScreenController(screenWidth, screenHeight, this);
        headsUpDisplayController.updateHeadsUpDisplay();
        mainWindow.setScene(screenController.getScene());
        mainWindow.show();
    }

    public void initRoom(String description) {

        startGameScreen(description);
    }
    public void initWinScreen() {
        WinScreenController screenController =
                new WinScreenController(screenWidth, screenHeight, this);
        mainWindow.setScene(screenController.getScene());
        mainWindow.show();

        if (update != null) {
            update.stop();
        }
        if (updateSeconds != null) {
            updateSeconds.stop();
        }
    }

    public void initGameOverScreen() {
        GameOverScreenController screenController =
                new GameOverScreenController(screenWidth, screenHeight, this);
        mainWindow.setScene(screenController.getScene());
        mainWindow.show();
        if (update != null) {
            update.stop();
        }
        if (updateSeconds != null) {
            updateSeconds.stop();
        }
    }

    public void initInventoryScreen() {
        InventoryController screenController =
                new InventoryController(400, 200, this);

        pauseGameScreen();

        inventoryStage = new Stage();
        inventoryStage.initModality(Modality.WINDOW_MODAL);
        inventoryStage.setTitle("INVENTORY");

        inventoryStage.setOnCloseRequest(e -> resumeGame());

        inventoryStage.setScene(screenController.getScene());
        inventoryStage.sizeToScene();
        inventoryStage.setResizable(false);
        inventoryStage.show();
    }

    public void isTesting(boolean enabled) {
        this.testMode = enabled;
    }

    public void initChallengeScreenPrompt() {
        ChallengeRoomPromptController screenController =
                new ChallengeRoomPromptController(400, 200, this);

        pauseGameScreen();

        challengeRoomPromptStage = new Stage();
        challengeRoomPromptStage.initModality(Modality.WINDOW_MODAL);
        challengeRoomPromptStage.setTitle("Challenge Room");

        challengeRoomPromptStage.setOnCloseRequest(e -> resumeGame());

        challengeRoomPromptStage.setScene(screenController.getScene());
        challengeRoomPromptStage.sizeToScene();
        challengeRoomPromptStage.setResizable(false);
        challengeRoomPromptStage.show();
    }

    public void hideInventory() {
        inventoryStage.hide();
        resumeGame();
    }
    public void initShopScreen() {
        ShopController screenController =
                new ShopController(400, 200, this);

        pauseGameScreen();

        shopStage = new Stage();
        shopStage.initModality(Modality.WINDOW_MODAL);
        shopStage.setTitle("SHOP");

        shopStage.setOnCloseRequest(e -> resumeGame());

        shopStage.setScene(screenController.getScene());
        shopStage.sizeToScene();
        shopStage.setResizable(false);
        shopStage.show();
    }

    public void hideShop() {
        shopStage.hide();
        resumeGame();
    }

    public void hideChallengeRoomPrompt() {
        challengeRoomPromptStage.hide();
        resumeGame();
    }

    // Getters (for important objects)
    public PlayerController getPlayerController() {
        return playerController;
    }
    public RoomController getRoomController() {
        return screenController;
    }
    public MonsterController getMonsterController() {
        return monsterController;
    }

    public static double getFrameMills() {
        return frameMills;
    }

    private void startGameScreen(String description) {
        /* This effectively resets the old GameLayer */
        GameLayer.removeAllSprites();

        screenController = new RoomController(screenWidth, screenHeight, this, description);
        screenController.populateRoom();

        headsUpDisplayController.updateHeadsUpDisplay();

        mainWindow.setScene(screenController.getScene());
        mainWindow.show();

        isPaused = false;

        update = new Timeline(
                new KeyFrame(Duration.millis(frameMills),
                        new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (!isPaused) {
                                playerController.updatePlayer();
                                monsterController.updateMonsters();
                                // headsUpDisplayController.updateHeadsUpDisplay();
                            }
                            headsUpDisplayController.updateHeadsUpDisplay();
                        }
                    })
        );

        updateSeconds = new Timeline(
            new KeyFrame(Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!isPaused) {
                            Stats.incrementTimeTaken();
                        }
                    }
                })
        );

        update.setCycleCount(Timeline.INDEFINITE);
        update.play();
        updateSeconds.setCycleCount(Timeline.INDEFINITE);
        updateSeconds.play();
    }

    private void pauseGameScreen() {
        isPaused = true;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void resumeGame() {
        isPaused = false;
        getPlayerController().clearPlayerInputData();
    }
}
