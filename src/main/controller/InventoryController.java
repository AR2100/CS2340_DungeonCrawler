package controller;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import model.PotionModel;
import model.Stats;
import resources.WeaponEnum;
import view.InventoryScreen;
import java.util.ArrayList;

public class InventoryController {
    private Controller controller;
    private InventoryScreen screen;
    private PlayerController playerController;


    public InventoryController(int screenWidth, int screenHeight, Controller controller) {
        this.controller = controller;
        screen = new InventoryScreen(screenWidth, screenHeight);

        screen.getUseItem().setOnAction(e -> handleUseItem());
        playerController = controller.getPlayerController();

        populateInventory();
    }

    public void populateInventory() {

        ComboBox playerItems = screen.getPlayerItems();

        ArrayList<WeaponEnum> playerWeapons = playerController.getPlayerModel().getPlayerWeapons();
        ArrayList<PotionModel> playerPotions = playerController.getPlayerModel().getPlayerPotions();

        for (int i = 0; i < playerWeapons.size(); i++) {
            if (!playerWeapons.get(i).equals(playerController
                    .getPlayerModel().getEquippedWeapon())) {
                playerItems.getItems().add(playerWeapons.get(i).name());
            }
        }
        for (int i = 0; i < playerPotions.size(); i++) {
            playerItems.getItems().add(playerPotions.get(i).getPotionType() + " potion");
        }

        screen.setPlayerItems(playerItems);
    }

    public void handleUseItem() {
        String itemSelected = (String) screen.getPlayerItems().getValue();
        if (itemSelected == null) {
            controller.hideInventory();
            return;
        }
        for (WeaponEnum weapon : WeaponEnum.values()) {
            if (weapon.name().equals(itemSelected)) {
                playerController.getPlayerModel().setEquippedWeapon(weapon);
                playerController.getWeaponController().setupWeaponSprite();
                controller.hideInventory();
                return;
            }
        }

        itemSelected = itemSelected.substring(0, itemSelected.indexOf(" "));
        if (itemSelected.equals("health")) {
            playerController.getPlayerModel().addHearts(2);
            playerController.getPlayerModel().removePotion("health");
        } else {
            playerController.getPlayerModel().setPowerSwingsLeft(3);
            playerController.getPlayerModel().setDamageAddition(2);
            playerController.getPlayerModel().removePotion("attack");
        }

        // close inventory screen
        controller.hideInventory();
    }


    public Scene getScene() {
        return screen.getScene();
    }


}
