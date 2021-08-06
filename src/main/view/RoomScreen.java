package view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Button;

import model.GameLayer;
import model.Sprite;
import model.TriggerZone;
import resources.LayerEnum;
import resources.Direction;

public class RoomScreen {

    private StackPane backgroundStackPane;

    private int width;
    private int height;

    private Duration backgroundAnimationDuration;

    private HeadsUpDisplay defaultHeadsUpDisplay;
    private Walls defaultWalls;
    private Background defaultBackground;

    private Sprite roomDescriptionSprite;

    private TriggerZone northExit;
    private TriggerZone southExit;
    private TriggerZone eastExit;
    private TriggerZone westExit;

    public RoomScreen(int width, int height, String description) {
        this.width = width;
        this.height = height;

        backgroundAnimationDuration = Duration.millis(1500);

        setupRoomDescription(description);
        setupExitTriggerZones();

        defaultHeadsUpDisplay = new HeadsUpDisplay();

        defaultWalls = new Walls();
        defaultBackground = new Background();

        setupBackgroundStackPane();
    }

    // Setup room
    private void setupExitTriggerZones() {
        northExit = new TriggerZone(0, 200, 100, 20, Direction.NORTH);
        southExit = new TriggerZone(0, -200, 100, 20, Direction.SOUTH);
        eastExit = new TriggerZone(400, 0, 20, 100,Direction.EAST);
        westExit = new TriggerZone(-400, 0, 20, 100, Direction.WEST);

        northExit.addToLayer(LayerEnum.GAME_LAYER);
        southExit.addToLayer(LayerEnum.GAME_LAYER);
        eastExit.addToLayer(LayerEnum.GAME_LAYER);
        westExit.addToLayer(LayerEnum.GAME_LAYER);
    }
    private void setupBackgroundStackPane() {
        backgroundStackPane = new StackPane();
        backgroundStackPane.setAlignment(Pos.BOTTOM_CENTER);
        backgroundStackPane.setMinSize(800, 400);

        backgroundStackPane.getChildren().addAll(defaultHeadsUpDisplay.getHeadsUpDisplay(),
                defaultBackground.getBackgroundGridPane(),
                GameLayer.getLayerPane());
    }
    private void setupRoomDescription(String description) {

        roomDescriptionSprite = new Sprite();
        roomDescriptionSprite.addToLayer(LayerEnum.GAME_LAYER);
        Text roomDes = new Text(description);
        roomDes.setId("roomDes");
        // roomDescriptionSprite.addText(roomDes, -30, 10);
    }

    // Update Methods
    public void changeRoomDescription(String description) {
        ObservableList<Node> children = roomDescriptionSprite.getSpriteNode().getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof Text) {
                ((Text) children.get(i)).setText(description);
            }
        }
    }

    // Getters
    public Scene getScene() {
        return new Scene(backgroundStackPane, width, height);
    }
    public Text getRoomDescription() {
        ObservableList<Node> children = roomDescriptionSprite.getSpriteNode().getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof Text) {
                return ((Text) children.get(i));
            }
        }
        return null;
    }

    public Walls getDefaultWalls() {
        return defaultWalls;
    }
    public TriggerZone getTriggerZone(Direction direction) {
        if (direction == Direction.NORTH) {
            return northExit;
        } else if (direction == Direction.SOUTH) {
            return southExit;
        } else if (direction == Direction.EAST) {
            return eastExit;
        } else if  (direction == Direction.WEST) {
            return westExit;
        } else {
            return null;
        }
    }
}
