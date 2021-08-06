import controller.Controller;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.BossMonsterModel;
import model.FreezeDartModel;
import model.MonsterModel;
import model.Stats;
import controller.Controller;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class M6Test extends ApplicationTest {
    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new Controller();
        controller.isTesting(true);
        controller.start(stage);
    }

    @Test
    public void testBossMonsterInRoom() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        boolean isBossMonster = false;
        ArrayList<MonsterModel> monsterList = controller.getMonsterController()
                .getMonsterList().get(12);
        MonsterModel bossMonster = monsterList.get(0);
        if (bossMonster instanceof BossMonsterModel) {
            isBossMonster = true;
        }
        assertEquals(true, isBossMonster);
    }

    @Test
    public void testCharacterFreezes() {
        clickOn("#debugMenu");
        clickOn("BOSS_ROOM");
        press(KeyCode.A);
        sleep(100);
        assertEquals(true, controller.getPlayerController().getPlayerModel().isFrozen());
    }

    @Test
    public void testStopGameWhenChallengePrompt() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        moveTo(700, 400);

        press(KeyCode.S);
        sleep(1000);
        release(KeyCode.S);

        press(MouseButton.PRIMARY);
        sleep(5000);

        press(KeyCode.S);
        sleep(1000);
        release(KeyCode.S);

        assertEquals(controller.isPaused(), true);
    }

    @Test
    public void testChestSpawnInChallengeRoom() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        moveTo(700, 400);
        controller.getPlayerController().setPermanentInvincible(true);
        controller.getPlayerController()
                .getWeaponController().getEquippedWeaponModel().setDamage(10);

        press(KeyCode.S);
        sleep(1000);
        release(KeyCode.S);

        press(MouseButton.PRIMARY);
        sleep(5000);
        release(MouseButton.PRIMARY);

        press(KeyCode.S);
        sleep(1000);
        release(KeyCode.S);

        assertEquals(controller.getRoomController().
                getChestController().getChestModels().isEmpty(), true);

        moveTo(700, 400);
        clickOn("Begin Challenge!");

        press(KeyCode.W);
        press(MouseButton.PRIMARY);
        sleep(1000);
        release(KeyCode.W);
        sleep(5000);
        release(MouseButton.PRIMARY);

        assertEquals(controller.getRoomController().
                getChestController().getChestModels().isEmpty(), false);
    }

    @Test
    public void testTimeTaken() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        press(KeyCode.W);
        sleep(1500);
        release(KeyCode.W);
        sleep(8000);
        assertNotEquals(0, (int)Stats.getTimeTaken());
    }

    @Test
    public void testPlayAgain() {
        clickOn("#debugMenu");
        clickOn("WIN_SCREEN");
        clickOn("#playAgain");
        verifyThat("Potions & Rubies", NodeMatchers.isNotNull());
    }
}
