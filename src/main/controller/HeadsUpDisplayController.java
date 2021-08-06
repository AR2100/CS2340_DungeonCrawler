package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.PlayerModel;
import model.PotionModel;
import view.HeadsUpDisplay;

import java.util.ArrayList;


public class HeadsUpDisplayController {
    private HeadsUpDisplay headsUpDisplay;
    private PlayerModel playerModel;
    private Controller controller;

    public HeadsUpDisplayController(Controller controller) {
        headsUpDisplay = new HeadsUpDisplay();
        this.controller = controller;
    }

    public void updateHeadsUpDisplay() {
        this.playerModel = controller.getPlayerController().getPlayerModel();

        String name = playerModel.getPlayerName();
        String weapon = playerModel.getEquippedWeapon().toString();
        String armor = playerModel.getPlayerArmor().toString();
        int numHealing = 0;
        int numAttack = 0;
        ArrayList<PotionModel> playerPotions = playerModel.getPlayerPotions();
        for (int i = 0; i < playerPotions.size(); i++) {
            if(playerPotions.get(i).getPotionType().equals("health")) {
                numHealing++;
            }
            if(playerPotions.get(i).getPotionType().equals("attack")) {
                numAttack++;
            }
        }

        int rubies = playerModel.getRuby();
        double hearts = playerModel.getHearts();

        if (hearts == 0) {
            controller.initGameOverScreen();
        }

        HeadsUpDisplay.updatePlayerInformation(name);
        HeadsUpDisplay.updateWeapon(weapon);
        HeadsUpDisplay.updateHealingPotionCount(numHealing);
        HeadsUpDisplay.updateAttackPotionCount(numAttack);
        HeadsUpDisplay.updateRubies(rubies);
        HeadsUpDisplay.updateArmor(armor);
        HeadsUpDisplay.updateHearts(hearts);


        (HeadsUpDisplay.getInventory().getButton()).setOnAction(e -> {
            controller.initInventoryScreen();
        });

        (HeadsUpDisplay.getShop().getButton()).setOnAction(e -> {
            controller.initShopScreen();
        });

    }
}
