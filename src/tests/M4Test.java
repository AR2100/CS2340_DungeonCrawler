import controller.Controller;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.MonsterModel;
import model.Sprite;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import view.Walls;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class M4Test extends ApplicationTest {
    private Controller controller;
    /**wa
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

    /**
     * verifies that player's health decreases when taking damage
     */
    @Test
    public void testDamageToPlayer() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        moveTo(700, 400);

        press(KeyCode.D);
        sleep(2500);
        release(KeyCode.D);

        assertNotEquals(5.0, controller.getPlayerController().getPlayerModel().getHearts(),
                0.1);
    }

    /**
     * verifies that monster's health decreases when taking damage
     */
    /*@Test
    public void testDamageToEnemy() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        moveTo(700, 400);

        press(KeyCode.W);
        sleep(1000);
        release(KeyCode.W);

        ArrayList<MonsterModel> monsters1 =
                controller.getMonsterController().getCurrentMonsters();
        ArrayList<Double> healthList1 = new ArrayList<>();
        for (MonsterModel monster: monsters1) {
            healthList1.add(monster.getHealth());
        }

        controller.getPlayerController()
                .getWeaponController().getEquippedWeaponModel().setDamage(10);

        press(MouseButton.PRIMARY);
        sleep(1000);
        release(MouseButton.PRIMARY);

        ArrayList<MonsterModel> monsters2 =
                controller.getMonsterController().getCurrentMonsters();
        ArrayList<Double> healthList2 = new ArrayList<>();


        for (MonsterModel monster: monsters2) {
            healthList2.add(monster.getHealth());
        }
        assertNotEquals(healthList1, healthList2);
    }*/
//    @Test
//    public void testDamageToEnemy() {
//        clickOn("#debugMenu");
//        clickOn("ROOM");
//        moveTo(700, 400);
//
//        press(KeyCode.W);
//        sleep(1000);
//        release(KeyCode.W);
//
//        ArrayList<MonsterModel> monsters1 =
//                controller.getMonsterController().getCurrentMonsters();
//        ArrayList<Double> healthList1 = new ArrayList<>();
//        for (MonsterModel monster: monsters1) {
//            healthList1.add(monster.getHealth());
//        }
//
//        controller.getPlayerController()
//                .getWeaponController().getEquippedWeaponModel().setDamage(10);
//
//        press(MouseButton.PRIMARY);
//        sleep(1000);
//        release(MouseButton.PRIMARY);
//
//        ArrayList<MonsterModel> monsters2 =
//                controller.getMonsterController().getCurrentMonsters();
//        ArrayList<Double> healthList2 = new ArrayList<>();
//
//
//        for (MonsterModel monster: monsters2) {
//            healthList2.add(monster.getHealth());
//        }
//        assertNotEquals(healthList1, healthList2);
//    }

    /**
     * verifies that monster's die when their HP reaches 0
     */
    /*@Test
    public void testMonsterIsKilled() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        moveTo(700, 400);

        press(KeyCode.W);
        sleep(1000);
        release(KeyCode.W);

        ArrayList<MonsterModel> monsters1 =
                controller.getMonsterController().getCurrentMonsters();
        ArrayList<Boolean> deadList1 = new ArrayList<>();
        for (MonsterModel monster: monsters1) {
            deadList1.add(monster.isDead());
        }
        press(KeyCode.D);
        press(MouseButton.PRIMARY);
        sleep(7000);
        release(KeyCode.D);
        release(MouseButton.PRIMARY);

        ArrayList<MonsterModel> monsters2 =
                controller.getMonsterController().getCurrentMonsters();
        ArrayList<Boolean> deadList2 = new ArrayList<>();
        for (MonsterModel monster: monsters2) {
            deadList2.add(monster.isDead());
        }
        assertNotEquals(deadList1, deadList2);
    }*/

//    @Test
//    public void testMonsterIsKilled() {
//        clickOn("#debugMenu");
//        clickOn("ROOM");
//        moveTo(700, 400);
//
//        press(KeyCode.W);
//        sleep(1000);
//        release(KeyCode.W);
//
//        ArrayList<MonsterModel> monsters1 =
//                controller.getMonsterController().getCurrentMonsters();
//        ArrayList<Boolean> deadList1 = new ArrayList<>();
//        for (MonsterModel monster: monsters1) {
//            deadList1.add(monster.isDead());
//        }
//        press(KeyCode.D);
//        press(MouseButton.PRIMARY);
//        sleep(7000);
//        release(KeyCode.D);
//        release(MouseButton.PRIMARY);
//
//        ArrayList<MonsterModel> monsters2 =
//                controller.getMonsterController().getCurrentMonsters();
//        ArrayList<Boolean> deadList2 = new ArrayList<>();
//        for (MonsterModel monster: monsters2) {
//            deadList2.add(monster.isDead());
//        }
//        assertNotEquals(deadList1, deadList2);
//    }
    /**
     * verifies that game components resets
     */
    @Test
    public void testRestartGame() {
        clickOn("#debugMenu");
        clickOn("GAME_OVER");
        clickOn("#playAgain");
        assertEquals(5.0, controller.getPlayerController().getPlayerModel().getHearts(), 0.1);
        verifyThat("Starting Room", NodeMatchers.isNotNull());
    }

    @Test
    public void testOnlyRetreatAvailable() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        moveTo(700, 400);

        press(KeyCode.W);
        sleep(1000);
        release(KeyCode.W);

        //checks that only entrance is valid
        /*
        verifyThat("South Exit", NodeMatchers.isVisible());
        verifyThat("North Exit", NodeMatchers.isInvisible());
        verifyThat("East Exit", NodeMatchers.isInvisible());
        verifyThat("West Exit", NodeMatchers.isInvisible());

         */

        Walls walls = controller.getRoomController().getRoomScreen().getDefaultWalls();

        //verifyThat(walls.isExitVisible(Direction.WEST), true);

        //verifyThat();

    }
    @Test
    public void testDoorOpenAfterMonsterDeath() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        moveTo(700, 400);

        press(KeyCode.W);
        sleep(2000);
        release(KeyCode.W);

        press(KeyCode.D);
        press(MouseButton.PRIMARY);
        sleep(7000);
        release(KeyCode.D);
        release(MouseButton.PRIMARY);

        verifyThat("South Exit", NodeMatchers.isVisible());
        verifyThat("North Exit", NodeMatchers.isVisible());
        verifyThat("East Exit", NodeMatchers.isInvisible());
        verifyThat("West Exit", NodeMatchers.isInvisible());
    }

    @Test
    public void testCollision() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        moveTo(700, 400);

        press(KeyCode.S);
        sleep(1000);
        release(KeyCode.S);

        press(KeyCode.D);
        sleep(1000);
        release(KeyCode.D);

        press(KeyCode.W);
        sleep(1000);
        release(KeyCode.W);

        Sprite sprite = controller.getPlayerController().getPlayerSprite();

        assertEquals(sprite.getPosition().getXPos() <= 350, true);
        assertEquals(sprite.getPosition().getYPos() <= 150, true);
    }

    @Test
    public void testKillPlayer() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        moveTo(700, 400);

        press(KeyCode.A);
        sleep(2000);
        release(KeyCode.A);

        press(KeyCode.D);
        sleep(10000);
        release(KeyCode.D);

        verifyThat("Game Over", NodeMatchers.isNotNull());
    }

    /*
    @Test
    public void testTeleport() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        Sprite sprite = controller.getPlayerController().getPlayerSprite();
        RoomScreen roomScreen = controller.getRoomController().getRoomScreen();
    }
    */

}