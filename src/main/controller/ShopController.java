package controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.PlayerModel;
import model.PotionModel;
import resources.WeaponEnum;
import view.ShopScreen;

import java.util.ArrayList;

public class ShopController {
    private Controller controller;
    private PlayerModel playerModel;
    private ShopScreen screen;
    private Label weapon1Label;
    private Label weapon2Label;
    private Label errorLabel;
    private HBox weapon1HBox;
    private HBox weapon2HBox;
    private Button healthPotionButton;
    private Button attackPotionButton;
    private Button weapon1Button;
    private Button weapon2Button;

    public ShopController(int screenWidth, int screenHeight, Controller controller) {
        this.controller = controller;
        this.playerModel = controller.getPlayerController().getPlayerModel();

        screen = new ShopScreen(screenWidth, screenHeight);
        updateWeaponLabels();

        healthPotionButton = screen.getHealthPotionButton();
        attackPotionButton = screen.getAttackPotionButton();
        weapon1Button = screen.getWeapon1Button();
        weapon2Button = screen.getWeapon2Button();
        weapon1Label = screen.getWeapon1Label();
        weapon2Label = screen.getWeapon2Label();
        weapon1HBox = screen.getWeapon1HBox();
        weapon2HBox = screen.getWeapon2HBox();
        errorLabel = screen.getErrorLabel();

        healthPotionButton.setOnAction(e -> {
            if(buyHealthPotion()) {
                playerModel.setRuby(playerModel.getRuby() - 10);
            } else {
                errorLabel.setText("Not enough rubies!");
            }
        });
        attackPotionButton.setOnAction(e -> {
            if(buyAttackPotion()) {
                playerModel.setRuby(playerModel.getRuby() - 10);
            } else {
                errorLabel.setText("Not enough rubies!");
            }
        });
        weapon1Button.setOnAction(e -> {
            if(buyWeapon1()) {
                playerModel.setRuby(playerModel.getRuby() - 30);
                weapon1Label.setText("Removed");
                weapon1HBox.setVisible(false);
                // controller.hideShop();
            } else {
                errorLabel.setText("Not enough rubies!");
            }
        });
        weapon2Button.setOnAction(e -> {
            if(buyWeapon2()) {
                playerModel.setRuby(playerModel.getRuby() - 30);
                weapon2Label.setText("Removed");
                weapon2HBox.setVisible(false);
                // controller.hideShop();
            } else {
                errorLabel.setText("Not enough rubies!");
            }
        });
    }

    public void updateWeaponLabels() {
        ArrayList<WeaponEnum> availableWeapons = new ArrayList<>();
        availableWeapons.add(WeaponEnum.MACE);
        availableWeapons.add(WeaponEnum.BROADSWORD);
        availableWeapons.add(WeaponEnum.DAGGER);

        ArrayList<WeaponEnum> playerWeapons = playerModel.getPlayerWeapons();

        WeaponEnum equippedWeapon = playerModel.getEquippedWeapon();
        for (WeaponEnum weapons : playerWeapons) {
            if (availableWeapons.contains(weapons)) {
                availableWeapons.remove(weapons);
            }
        }

        // playerModel.addWeapon(equippedWeapon);
        System.out.println("current equipped weapon " + equippedWeapon);
        System.out.println("list of needed weapons " + availableWeapons);
        if(availableWeapons.size() == 2) {
            if (equippedWeapon.equals(WeaponEnum.DAGGER)) {
                screen.updateWeapon1Label("Broadsword");
                screen.updateWeapon2Label("Mace");
            } else if(equippedWeapon.equals(WeaponEnum.MACE)) {
                screen.updateWeapon1Label("Broadsword");
                screen.updateWeapon2Label("Dagger");
            } else {
                screen.updateWeapon1Label("Dagger");
                screen.updateWeapon2Label("Mace");
            }
        } else if(availableWeapons.size() == 1) {
            if (availableWeapons.contains(WeaponEnum.MACE)) {
                screen.updateWeapon1Label("Mace");
                screen.updateWeapon2Label("Removed");
            } else if(availableWeapons.contains(WeaponEnum.DAGGER)) {
                screen.updateWeapon1Label("Dagger");
                screen.updateWeapon2Label("Removed");
            } else {
                screen.updateWeapon1Label("Broadsword");
                screen.updateWeapon2Label("Removed");
            }
        } else {
            screen.updateWeapon1Label("Removed");
            screen.updateWeapon2Label("Removed");
        }
    }

    public boolean buyHealthPotion() {
        if(playerModel.getRuby() >= 10) {
            PotionModel potion = new PotionModel("health", 1);
            playerModel.addPotion(potion);
            potion.setActive(false);
            return true;
        }
        return false;
    }

    public boolean buyAttackPotion() {
        if(playerModel.getRuby() >= 10) {
            PotionModel potion = new PotionModel("attack", 1);
            playerModel.addPotion(potion);
            potion.setActive(false);
            return true;
        }
        return false;
    }

    private boolean buyWeapon1() {
        if(playerModel.getRuby() >= 30) {
            if(weapon1Label.getText().equals("Dagger") &&
                    !playerModel.getPlayerWeapons().contains(WeaponEnum.DAGGER)) {
                playerModel.addWeapon(WeaponEnum.DAGGER);
                System.out.println("Bought Dagger");
            } else if(weapon1Label.getText().equals("Broadsword") &&
                    !playerModel.getPlayerWeapons().contains(WeaponEnum.BROADSWORD)) {
                playerModel.addWeapon(WeaponEnum.BROADSWORD);
                System.out.println("Bought Broadsword");
            } else if(weapon1Label.getText().equals("Mace") &&
                    !playerModel.getPlayerWeapons().contains(WeaponEnum.MACE)) {
                playerModel.addWeapon(WeaponEnum.MACE);
                System.out.println("Bought Mace");
            }
            return true;
        }
        return false;
    }

    private boolean buyWeapon2() {
        if(playerModel.getRuby() >= 30) {
            if(weapon2Label.getText().equals("Dagger") &&
                    !playerModel.getPlayerWeapons().contains(WeaponEnum.DAGGER)) {
                playerModel.addWeapon(WeaponEnum.DAGGER);
                System.out.println("Bought Dagger");
            } else if(weapon2Label.getText().equals("Broadsword") &&
                    !playerModel.getPlayerWeapons().contains(WeaponEnum.BROADSWORD)) {
                playerModel.addWeapon(WeaponEnum.BROADSWORD);
                System.out.println("Bought Broadsword");
            } else if(weapon2Label.getText().equals("Mace") &&
                    !playerModel.getPlayerWeapons().contains(WeaponEnum.MACE)) {
                playerModel.addWeapon(WeaponEnum.MACE);
                System.out.println("Bought Mace");
            }
            return true;
        }
        return false;
    }


    public Scene getScene() {
        return screen.getScene();
    }

}
