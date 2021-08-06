import controller.Controller;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.ChestModel;
import model.MonsterModel;
import model.PotionModel;
import model.WeaponModel;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import resources.ArmorEnum;

import resources.Position;
import resources.WeaponEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class M5Test extends ApplicationTest {
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
    public void testMonsterDropsPotion() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        press(KeyCode.W);
        sleep(1000);
        moveTo(700, 400);
        ArrayList<PotionModel> potions1 =
                controller.getRoomController().getPotionController().getCurrentPotions();
        controller.getPlayerController()
                .getWeaponController().getEquippedWeaponModel().setDamage(10);
        press(KeyCode.D);
        press(MouseButton.PRIMARY);
        sleep(7000);
        ArrayList<PotionModel> potions2 =
                controller.getRoomController().getPotionController().getCurrentPotions();
        assertNotEquals(potions1, potions2);
    }

    /*
     * Tests potions can be picked up by a player.
     */
    @Test
    public void testPotionPickUp() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        press(KeyCode.W);
        sleep(1000);
        moveTo(700, 400);
        ArrayList<PotionModel> potions1 =
                controller.getRoomController().getPotionController().getCurrentPotions();
        controller.getPlayerController()
                .getWeaponController().getEquippedWeaponModel().setDamage(10);
        press(KeyCode.D);
        press(MouseButton.PRIMARY);
        sleep(7000);
        release(KeyCode.W);
        release(KeyCode.D);
        press(KeyCode.A);
        sleep(7000);
        ArrayList<PotionModel> potions2 =
                controller.getRoomController().getPotionController().getCurrentPotions();
        assertEquals(potions1, potions2);
    }

    /**
     * Makes sures that chest gives player an item
     */
    @Test
    public void testChestGivesItem() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        press(KeyCode.W);
        sleep(1000);
        moveTo(700, 400);
        press(KeyCode.D);
        press(MouseButton.PRIMARY);
        sleep(10000);
        release(KeyCode.W);
        release(KeyCode.A);
        release(KeyCode.D);
        release(MouseButton.PRIMARY);
        press(KeyCode.S);
        sleep(10);
        release(KeyCode.S);
        press(KeyCode.A);
        sleep(5000);

        HashMap<Integer, ArrayList<ChestModel>> chest1
                = new HashMap<Integer, ArrayList<ChestModel>>();
        HashMap<Integer, ArrayList<ChestModel>> chest2 =
                controller.getRoomController().getChestController().getChestModels();
        assertNotEquals(chest1, chest2);
    }

    @Test
    public void testArmorReducesDamage() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        controller.getPlayerController().getPlayerModel().setPlayerArmor(ArmorEnum.RUBY);
        press(KeyCode.W);
        sleep(3000);

        double heart1 = controller.getPlayerController().getPlayerModel().getHearts();

        controller.getPlayerController().getPlayerModel().setPlayerArmor(ArmorEnum.LEATHER);
        controller.getPlayerController().getPlayerModel().setHearts(5);
        release(KeyCode.W);
        press(KeyCode.D);
        sleep(3000);

        double heart2 = controller.getPlayerController().getPlayerModel().getHearts();

        assertNotEquals(heart1, heart2);

    }
    @Test
    public void testLoadWeaponData() {
        WeaponModel model = new WeaponModel();
        try {
            model.getWeaponData(WeaponEnum.MACE);
            model.getWeaponData(WeaponEnum.DAGGER);
            model.getWeaponData(WeaponEnum.BROADSWORD);
        } catch (IOException e) {
            assertEquals(true, false);
        }
    }

    /*@Test
    public void testStopGameWhenOpenInventory() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        press(KeyCode.W);
        sleep(1000);

        clickOn("Inventory");

        ArrayList<MonsterModel> monsters = controller.getMonsterController().getCurrentMonsters();
        ArrayList<Position> monsterPositions = new ArrayList<>();

        for (MonsterModel monster : monsters) {
            monsterPositions.add(monster.getMonsterSprite().getPosition());
        }

        sleep(500);

        for (int i = 0; i < monsters.size(); i++) {
            MonsterModel monster = monsters.get(i);
            Position oldMonsterPos = monsterPositions.get(i);

            assertEquals(monster.getMonsterSprite()
                    .getPosition().getXPos(), oldMonsterPos.getXPos(), 0.1);
            assertEquals(monster.getMonsterSprite()
                    .getPosition().getYPos(), oldMonsterPos.getYPos(), 0.1);
        }
    }*/
    @Test
    public void testStopGameWhenOpenInventory() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        press(KeyCode.W);
        sleep(1000);

        clickOn("Inventory");

        Iterator<MonsterModel> monstersIter = controller.getMonsterController().getCurrentMonsters();

        ArrayList<MonsterModel> monsters = new ArrayList<MonsterModel>();
        ArrayList<Position> monsterPositions = new ArrayList<>();

        while (monstersIter.hasNext()){
            MonsterModel currMonster = monstersIter.next();
            monsters.add(currMonster);
            monsterPositions.add(currMonster.getMonsterSprite().getPosition());
        }

        sleep(500);

        for (int i = 0; i < monsters.size(); i++) {
            MonsterModel monster = monsters.get(i);
            Position oldMonsterPos = monsterPositions.get(i);

            assertEquals(monster.getMonsterSprite()
                    .getPosition().getXPos(), oldMonsterPos.getXPos(), 0.1);
            assertEquals(monster.getMonsterSprite()
                    .getPosition().getYPos(), oldMonsterPos.getYPos(), 0.1);
        }
    }

    @Test
    public void testStartingInventory() {
        clickOn("#debugMenu");
        clickOn("ROOM");

        press(KeyCode.W);
        sleep(1000);

        clickOn("Inventory");
        ArrayList<WeaponEnum> weapons = controller
                .getPlayerController().getPlayerModel().getPlayerWeapons();
        assertEquals(weapons.size(), 1, 0);

    }

    @Test
    public void testInventoryUpdates() {
        clickOn("#debugMenu");
        clickOn("ROOM");
        press(KeyCode.W);
        sleep(1000);
        moveTo(700, 400);
        int totalItems = controller.getPlayerController().getPlayerModel().getPlayerWeapons().size()
                + controller.getPlayerController().getPlayerModel().getPlayerPotions().size();
        controller.getPlayerController().
                getWeaponController().getEquippedWeaponModel().setDamage(10);
        press(KeyCode.D);
        press(MouseButton.PRIMARY);
        sleep(7000);
        release(KeyCode.W);
        release(KeyCode.D);
        press(KeyCode.A);
        sleep(7000);

        int afterItems = controller.getPlayerController().getPlayerModel().getPlayerWeapons().size()
                + controller.getPlayerController().getPlayerModel().getPlayerPotions().size();
        assertEquals(totalItems < afterItems, true);



    }





}
