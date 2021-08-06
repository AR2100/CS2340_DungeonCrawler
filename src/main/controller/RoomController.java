package controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.PlayerModel;
import model.TriggerZone;
import model.WeaponModel;
import resources.Direction;
import resources.GamePosition;
import resources.Position;
import resources.SpriteType;
import resources.WeaponEnum;
import resources.LayerEnum;
import resources.Difficulty;
import view.RoomScreen;
import model.Sprite;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.*;

/** This class is the controller for all of the GUI
 * objects to be run in the screen for the rooms.
 *
 * @version 1.0
 * @author Andrew Roach, Rishi Chillara, Amit Kulkarni, Joe Wilmot
 */
public class RoomController {

    private RoomScreen roomScreen;
    private Controller controller;

    private PotionController potionController;
    private ChestController chestController;
    private RubyController rubyController;
    private PlayerController playerController;

    private HashMap<Direction, Sprite> directionSpriteHashMap;

    private int currPos;
    private int finalRoom;
    private int numRooms;
    private ArrayList<String> roomDescriptions = new ArrayList<>();
    private HashMap<Integer, Direction[]> validExits;
    private HashMap<Integer, int[]> edges;
    private HashSet<Integer> visited;
    private HashMap<Integer, Direction> intToDirection;

    private Position northSpawnPosition;
    private Position southSpawnPosition;
    private Position eastSpawnPosition;
    private Position westSpawnPosition;
    private Position centerSpawnPosition;

    private ArrayList<Integer> challengeRooms;
    private Sprite challengeBed;
    private boolean challengeDeclined;
    private boolean bedTouched;

    private boolean challengeActive;

    /**
     * This is a constructor that sets up the screen
     * for the rooms to be set up in.
     *
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     * @param controller the controller to control the screen
     * @param description the description for the room
     */
    public RoomController(int screenWidth, int screenHeight,
                          Controller controller, String description) {
        this.controller = controller;
        this.roomScreen = new RoomScreen(screenWidth, screenHeight, description);

        currPos = 0;

        directionSpriteHashMap = new HashMap<>();

        roomDescriptions = new ArrayList<>();
        validExits = new HashMap<>();
        edges = new HashMap<>();
        visited = new HashSet<>();
        intToDirection = new HashMap<>();
        numRooms = 14;

        setupSpawnPositions();
        initAndRandomizeRooms();
        updateTriggerZoneDestinations();
        setExitVisibility();

        setupChallengeRoom();
    }

    public void populateRoom() {
        controller.getPlayerController().resetPlayerData();
        controller.getPlayerController().getWeaponController().resetWeapons();
        controller.getPlayerController().setupPlayerSprite();
        controller.getMonsterController().setupMonsterSprites();

        potionController = new PotionController(this);
        chestController = new ChestController(this);
        rubyController = new RubyController(this);
        playerController = controller.getPlayerController();

        //Uncomment to shows hitboxes
        // GameLayer.showSpriteCenters();
        // GameLayer.showBoxColliderOutline();
    }

    // Initialize button input on the game screen
    private void setEvents(Scene scene) {
        PlayerModel playerModel = controller.getPlayerController().getPlayerModel();
        Sprite playerSprite = controller.getPlayerController().getPlayerSprite();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                KeyCode keyCode = ke.getCode();

                /* if (keyCode == KeyCode.DIGIT1) {
                    System.out.println("1 pressed");
                    if(playerController.getPlayerModel().getPlayerWeapons().size() != 0) {
                        System.out.println(playerController.getPlayerModel().getPlayerWeapons().get(0));
                        playerController.getPlayerModel().setEquippedWeapon(playerController.getPlayerModel().getPlayerWeapons().get(0));
                        playerController.getWeaponController().setupWeaponSprite();
                    }
                } else if (keyCode == KeyCode.DIGIT2) {
                    System.out.println("2 pressed");
                    playerController.getPlayerModel().setEquippedWeapon(playerController.getPlayerModel().getPlayerWeapons().get(1));
                    playerController.getWeaponController().setupWeaponSprite();
                } else if (keyCode == KeyCode.DIGIT3) {
                    System.out.println("3 pressed");
                    playerController.getPlayerModel().setEquippedWeapon(playerController.getPlayerModel().getPlayerWeapons().get(2));
                    playerController.getWeaponController().setupWeaponSprite();
                } else if (keyCode == KeyCode.DIGIT4) {
                    System.out.println("4 pressed");
                    playerController.getPlayerModel().addHearts(2);
                    playerController.getPlayerModel().removePotion("health");
                } else if (keyCode == KeyCode.DIGIT5) {
                    System.out.println("5 pressed");
                    playerController.getPlayerModel().setPowerSwingsLeft(3);
                    playerController.getPlayerModel().setDamageAddition(2);
                    playerController.getPlayerModel().removePotion("attack");
                } */

                if (keyCode == KeyCode.W) {
                    playerSprite.playAnimation();
                    playerModel.setUpPressed(true);
                } else if (keyCode == KeyCode.S) {
                    playerSprite.playAnimation();
                    playerModel.setDownPressed(true);
                } else if (keyCode == KeyCode.D) {
                    playerSprite.playAnimation();
                    playerModel.setRightPressed(true);
                } else if (keyCode == KeyCode.A) {
                    playerSprite.playAnimation();
                    playerModel.setLeftPressed(true);
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                KeyCode keyCode = ke.getCode();

                playerSprite.stopAnimation();
                if (keyCode == KeyCode.W) {
                    playerModel.setUpPressed(false);
                } else if (keyCode == KeyCode.S) {
                    playerModel.setDownPressed(false);
                } else if (keyCode == KeyCode.D) {
                    playerModel.setRightPressed(false);
                } else if (keyCode == KeyCode.A) {
                    playerModel.setLeftPressed(false);
                }
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                WeaponController weaponController = controller
                        .getPlayerController().getWeaponController();
                WeaponModel weaponModel = weaponController.getEquippedWeaponModel();

                weaponModel.setUseWeapon(true);

                double mouseX = mouseEvent.getSceneX();
                double mouseY = mouseEvent.getSceneY();

                weaponModel.setMousePos(GamePosition.translateRawToRelative(mouseX, mouseY));
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                WeaponController weaponController = controller
                        .getPlayerController().getWeaponController();
                WeaponModel weaponModel = weaponController.getEquippedWeaponModel();
                weaponModel.setUseWeapon(false);
            }
        });
    }

    private int checkOpenRandomEdges(int[] arr, HashSet<Direction> avaliable) {
        ArrayList<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == -1) {
                indexs.add(i);
            }
        }
        Collections.shuffle(indexs);

        for (int i = 0; i < indexs.size(); i++) {
            if (avaliable.contains(intToDirection.get(indexs.get(i)))) {
                avaliable.remove(intToDirection.get(indexs.get(i)));
                return indexs.get(i);
            }
        }
        return -1;
    }

    private int numNegOnes(int[] arr) {
        int count = 0;
        for (int value: arr) {
            if (value == -1) {
                count++;
            }
        }
        return count;
    }

    // Setup rooms, randomize room locations
    private void initAndRandomizeRooms() {

        intToDirection.put(0, Direction.NORTH);
        intToDirection.put(1, Direction.EAST);
        intToDirection.put(2, Direction.SOUTH);
        intToDirection.put(3, Direction.WEST);

        // key : room index in room descriptions
        // edges : [room index to go to if north, room index if east,
        // room index if south, room index if west]
        // -1 if there is no valid room

// #TODO: Generate Graph
// #TODO: Generate Valid Directions Based on Edges

        //init starting pos of 4 rooms
        edges.put(0, new int[]{4, 1 ,3, 2});
        edges.put(5, new int[]{-1,-1,-1,-1});


        // set rooms 1-4 edges back to room 0 to ensure bidirectionality

        HashSet<Direction> availableDirections = new HashSet<>();
        availableDirections.add(Direction.SOUTH);
        availableDirections.add(Direction.WEST);
        availableDirections.add(Direction.EAST);


        for (int i = 1; i <= 3; i++) {
            edges.put(i, new int[]{-1, -1, -1, -1});
            int indexLeadingToRoom = -1;
            for (int j = 0; j < edges.get(0).length; j++) {
                if (edges.get(0)[j] == i) {
                    indexLeadingToRoom = j;
                    break;
                }
            }
            indexLeadingToRoom = (indexLeadingToRoom + 2) % 4;
            edges.get(i)[indexLeadingToRoom] = 0;
            System.out.println(i);
            int openEdge = checkOpenRandomEdges(edges.get(i), availableDirections);
            edges.get(i)[openEdge] = 5;
            edges.get(5)[(openEdge + 2) % 4] = i;

        }

        edges.put(4, new int[]{-1 ,-1, 0, -1});
        edges.get(5)[2] = 6; //set 5 room north to 6

        edges.put(6, new int[]{5, 8, 9, 7});
        edges.put(7, new int[]{-1, 6, -1, 9});
        edges.put(8, new int[]{-1, 9, 11, 6});
        edges.put(9, new int[]{6, 7, 10, 8});
        edges.put(10, new int[]{9, 11, -1, 12});
        edges.put(11, new int[]{8, -1, -1, 10});
        edges.put(12, new int[]{-1, 10, 13, -1});
        edges.put(13, new int[]{12, -1, 14, -1});

        for (int i = 0; i < numRooms - 3; i++) {
            roomDescriptions.add("Monster Fight" + i);
        }

        Collections.shuffle(roomDescriptions);
        roomDescriptions.add(0, "Starting Room");
        roomDescriptions.add(roomDescriptions.size(), "Final Boss");
        roomDescriptions.add(roomDescriptions.size(), "Exit Room");

        for (int i = 0; i < edges.size(); i++) {
            Direction[] toInsert = new Direction[4 - numNegOnes(edges.get(i))];
            int lastIndex = 0;
            for (int j = 0; j < edges.get(i).length; j++) {
                if (edges.get(i)[j] != -1) {
                    toInsert[lastIndex] = intToDirection.get(j);
                    lastIndex++;
                }
            }
            validExits.put(i, toInsert);
//            System.out.println(i + " " + Arrays.toString(edges.get(i)));
        }
        visited.add(0);
    }

    private void updateTriggerZoneDestinations() {
        for (Direction dir : Direction.values()) {
            TriggerZone triggerZone = roomScreen.getTriggerZone(dir);

            if (triggerZone != null) {
                Direction triggerZoneDirection = triggerZone.getExitDirection();
                int destination = -1;

                if (triggerZoneDirection.value != -1) {
                    destination = edges.get(currPos)[triggerZoneDirection.value];
                }

                triggerZone.setDestination(destination);
            }
        }
    }

    // Handle player spawn position and switching rooms
    // newPos CANNOT be negative!
    public void switchRooms(int newPos, Direction entranceDirection, Sprite player) {
        if (currPos == roomDescriptions.size() - 1) {
            controller.initWinScreen();
            return;
        }

        roomScreen.changeRoomDescription(roomDescriptions.get(newPos));

        MonsterController monsterController = controller.getMonsterController();
        monsterController.toggleMonsters(false);
        potionController.toggleCurrentPotions(false);
        chestController.toggleCurrentChests(false);
        rubyController.toggleCurrentRubies(false);

        currPos = newPos;
        visited.add(currPos);
        System.out.println(currPos);

        monsterController.toggleMonsters(true);
        potionController.toggleCurrentPotions(true);
        chestController.toggleCurrentChests(true);
        rubyController.toggleCurrentRubies(true);

        updateTriggerZoneDestinations();

        spawnPlayer(player, entranceDirection);

        if (monsterController.checkRoomMonsterCount()) { //if all monsters are dead set visibility
            setExitVisibility();
            if (isChallengeRoom()) {
                toggleChallengeRoom(true);
            } else {
                toggleChallengeRoom(false);
            }
        } else {
            int[] neighbors = edges.get(currPos);
            for (int i = 0; i < neighbors.length; i++) {
                setExitVisibilityHelper(intToDirection.get(i), visited.contains(neighbors[i]));
                //since i already visited the room, visited.contains(neighbors[i]) should tell me
                // if it is a visible door or not
            }

            toggleChallengeRoom(false);
        }
    }

    private void spawnPlayer(Sprite player, Direction entranceDirection) {

        if (entranceDirection == Direction.NORTH) {
            player.setPosition(northSpawnPosition);
        } else if (entranceDirection == Direction.SOUTH) {
            player.setPosition(southSpawnPosition);
        } else if (entranceDirection == Direction.EAST) {
            player.setPosition(eastSpawnPosition);
        } else if (entranceDirection == Direction.WEST) {
            player.setPosition(westSpawnPosition);
        }
    }

    private void setupSpawnPositions() {
        northSpawnPosition = new Position(0, 160);
        southSpawnPosition = new Position(0, -160);
        eastSpawnPosition = new Position(360, 0);
        westSpawnPosition = new Position(-360, 0);
        centerSpawnPosition = new Position(0, 0);
    }

    public boolean isChallengeRoom() {
        if (challengeRooms.contains((Integer) currPos)) {
            return true;
        } else {
            return false;
        }
    }

    public void toggleChallengeRoom(boolean isEnabled) {
        challengeBed.setSpriteVisibility(isEnabled);
        challengeBed.getBoxCollider().setEnabled(isEnabled);
    }

    private void setupChallengeRoom() {
        setupBedSprite();

        challengeRooms = new ArrayList<>();
        challengeRooms.add(3);
        challengeRooms.add(5);
        challengeRooms.add(9);

        challengeDeclined = false;
        bedTouched = false;
    }

    private void setupBedSprite() {
        challengeBed = new Sprite();
        challengeBed.addImage(35);
        challengeBed.setCenter(challengeBed.getScaledWidth(),
                challengeBed.getScaledHeight(), 0.5, 0.5, true);
        challengeBed.setPosition(0, 0);
        challengeBed.addBoxCollider(32, 32);
        challengeBed.addSpriteType(SpriteType.BED);
        challengeBed.setSpriteVisibility(false);
        challengeBed.getBoxCollider().setEnabled(false);

        challengeBed.addToLayer(LayerEnum.GAME_LAYER);
    }

    public void setChallengeRoomComplete() {
        if (isChallengeRoom()) {
            challengeRooms.remove((Integer) currPos);
        }
    }

    public void beginChallenge() {
        for (Direction dir : Direction.values()) {
            if (dir != Direction.NONE) {
                setExitVisibilityHelper(dir, false);
            }
        }
    }



    // Getters and Setters
    public Scene getScene() {
        Scene scene = roomScreen.getScene();
        setEvents(scene);

        return scene;
    }

    public RoomScreen getRoomScreen() {
        return roomScreen;
    }

    public void setExitVisibility() {

        for (Direction dir : Direction.values()) {
            if (dir != Direction.NONE) {
                setExitVisibilityHelper(dir, false);
            }
        }

        Direction[] exitDirs = validExits.get(currPos);
        for (Direction exit : exitDirs) {
            setExitVisibilityHelper(exit, true);
        }

    }
    private void setExitVisibilityHelper(Direction dir, boolean isVisible) {
        roomScreen.getDefaultWalls().setExitVisibility(dir, !isVisible);
    }
    public int getCurrPos() {
        return currPos;
    }
    public PotionController getPotionController() {
        return potionController;
    }
    public ChestController getChestController() {
        return chestController;
    }
    public PlayerController getPlayerController() {
        return controller.getPlayerController();
    }
    public RubyController getRubyController() { return rubyController; }
    public boolean isChallengeDeclined() {
        return challengeDeclined;
    }
    public void setChallengeDeclined(boolean challengeDeclined) {
        this.challengeDeclined = challengeDeclined;
    }
    public boolean isBedTouched() {
        return bedTouched;
    }
    public void setBedTouched(boolean bedTouched) {
        this.bedTouched = bedTouched;
    }
    public Position getNorthSpawnPosition() {
        return northSpawnPosition;
    }
    public Position getSouthSpawnPosition() {
        return southSpawnPosition;
    }
    public Position getEastSpawnPosition() {
        return eastSpawnPosition;
    }
    public Position getWestSpawnPosition() {
        return westSpawnPosition;
    }
    public boolean isChallengeActive() {
        return challengeActive;
    }
    public void setChallengeActive(boolean challengeActive) {
        this.challengeActive = challengeActive;
    }
}
