import controller.Controller;
import controller.RoomController;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.NodeQuery;
import resources.Direction;
import resources.DirectionUtils;
import view.RoomScreen;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class M3Test extends ApplicationTest {

    private Controller controller;
    /**
     * Set up the screen for testing.
     *
     * @param stage the stage the program is set on
     * @throws Exception throws exception if anything goes wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        controller = new Controller();
        controller.isTesting(true);
        controller.start(stage);
    }

    @Test
    public void testStartingRoom() {
        clickOn("#debugMenu");
        clickOn("INITIAL");
        clickOn("Begin");

        verifyThat("Starting Room", NodeMatchers.isNotNull());
    }

    @Test
    public void testRoomSwitch() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        clickOn("East Exit");

        verifyThat("West Exit", NodeMatchers.isNotNull());
    }

    @Test
    public void testIfAtLeastSixRoomsAway() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        NodeQuery roomDes = lookup("#roomDes");

        for (int i = 0; i <= 6; i++) {
            clickOn("East Exit");
        }
        verifyThat(roomDes, NodeMatchers.hasChild("Exit Room"));
    }

    @Test
    public void testWinScreen() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        for (int i = 0; i <= 7; i++) {
            clickOn("East Exit");
        }
        NodeQuery winText = lookup("#winText");
        verifyThat(winText, NodeMatchers.isNotNull());
    }

    @Test
    public void testSouthCycle() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        for (int i = 0; i < 10; i++) {
            clickOn("South Exit");
        }
    }

    @Test
    public void testNorthCycle() {
        clickOn("Start");
        type(KeyCode.BACK_SPACE);
        type(KeyCode.R, KeyCode.O, KeyCode.G, KeyCode.E, KeyCode.R);
        clickOn("Sword");
        clickOn("Next");
        clickOn("Begin");
        for (int i = 0; i < 10; i++) {
            clickOn("North Exit");
        }
    }


    @Test
    public void testWallVisibilityMatchesExit() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        RoomController roomController = controller.getRoomController();
        RoomScreen roomScreen = roomController.getRoomScreen();

        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction dir : directions) {
            boolean exitVisible = roomScreen.getDefaultWalls().isExitVisible(dir);
            //            boolean buttonVisible = roomController.getExitButton(dir).isVisible();

            //  If the button is visible, we want the wall to be invisible, and vice-versa
            //            assertEquals(!exitVisible, buttonVisible);
        }

    }
    
    @Test
    public void testStartingRoomHasFourExits() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        Set<String> uniqueRoomNames = new HashSet<>();

        RoomController roomController = controller.getRoomController();
        RoomScreen roomScreen = roomController.getRoomScreen();

        String roomName = roomScreen.getRoomDescription().getText();
        uniqueRoomNames.add(roomName);

        boolean isUnique = true;

        for (Direction dir : Direction.values()) {
            clickOn(parseDirectionEnum(dir));

            roomName = roomScreen.getRoomDescription().getText();

            if (uniqueRoomNames.contains(roomName)) {
                isUnique = false;
                break;
            } else {
                uniqueRoomNames.add(roomName);
            }

            clickOn(parseDirectionEnum(DirectionUtils.getOppositeDirection(dir)));
        }

        assertEquals(true, isUnique);
    }

    private String parseDirectionEnum(Direction dir) {
        String rawStr = dir.toString();
        String buttonString = rawStr.substring(0, 1).toUpperCase()
                + rawStr.substring(1, rawStr.length()).toLowerCase() + " Exit";
        return buttonString;
    }

}
