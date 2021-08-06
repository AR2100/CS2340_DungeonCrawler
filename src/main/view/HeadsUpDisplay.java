package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Sprite;
import resources.LayerEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class HeadsUpDisplay {
    private static HBox headsUpDisplay;

    private static Label playerName;
    private static Label gameDifficultyChosen;
    private static Label numRuby;
    private static Label score;

    private static VBox playerNameContainer;
    private static VBox itemContainer;
    private static HBox weaponPotionArmorHBox;
    private static VBox rubyContainer;
    private static VBox heartsContainer;
    private static VBox inventoryButtonContainer;
    private static VBox shopButtonContainer;

    private static Image weaponImage;
    private static ImageView weaponImageView;

    /*
    private static Image weaponImage2;
    private static ImageView weaponImageView2;
    private static Image weaponImage3;
    private static ImageView weaponImageView3;
    */

    private static Image armorImage;
    private static ImageView armorImageView;

    private static Image potionHealing;
    private static ImageView potionHealingImageView;
    private static Label potionHealingCount;

    private static Image potionAttack;
    private static ImageView potionAttackImageView;
    private static Label potionAttackCount;

    private static Image heartImage;
    private static Image quarterHeartImage;
    private static Image halfHeartImage;
    private static Image threeQuarterHeartImage;

    private static Button inventoryButton;
    private static Sprite inventory;
    private static Button shopButton;
    private static Sprite shop;

    private static ArrayList<ImageView> heartsList;

    public HeadsUpDisplay() {
        initializeHeadsUpDisplay();
        headsUpDisplay.getChildren().addAll(playerNameContainer,
            itemContainer, heartsContainer, rubyContainer, inventoryButtonContainer, shopButtonContainer);
        headsUpDisplay.setAlignment(Pos.TOP_CENTER);
        headsUpDisplay.setId("headsUpDisplay");
        headsUpDisplay.setSpacing(15);
        headsUpDisplay.getStylesheets().add("resources/css/HeadsUpDisplay.css");
    }

    // setup HUD
    private void initializeHeadsUpDisplay() {
        headsUpDisplay = new HBox();

        playerNameContainer = new VBox();
        Label playerLabel = new Label("Player");
        playerName = new Label();
        playerNameContainer.setAlignment(Pos.CENTER_LEFT);
        playerNameContainer.setMinWidth(150);
        playerNameContainer.setMaxWidth(150);
        playerNameContainer.getChildren().addAll(playerLabel, playerName);

        itemContainer = new VBox();
        weaponPotionArmorHBox = new HBox();
        Label itemLabel = new Label("Items");
        weaponImage = new Image("sprites/sword.png", 30, 30, true, true);
        weaponImageView = new ImageView(weaponImage);
        // weaponImage2 = new Image("sprites/dagger.png", 30, 30, true, true);
        // weaponImageView2 = new ImageView(weaponImage2);
        // weaponImage3 = new Image("sprites/mace.png", 30, 30, true, true);
        // weaponImageView3 = new ImageView(weaponImage3);
        potionHealing = new Image("sprites/HealthPotion.png", 30, 30, false, false);
        potionHealingImageView = new ImageView(potionHealing);
        potionHealingCount = new Label("x0");
        potionAttack = new Image("sprites/AttackPotion.png", 30, 30, false, false);
        potionAttackImageView = new ImageView(potionAttack);
        potionAttackCount = new Label("x0");
        armorImage = new Image("sprites/armorLeather.png", 30, 30, true, true);
        armorImageView = new ImageView(armorImage);
        weaponPotionArmorHBox.getChildren().addAll(weaponImageView, potionHealingImageView,
                potionHealingCount, potionAttackImageView, potionAttackCount, armorImageView);
        weaponPotionArmorHBox.setAlignment(Pos.CENTER);
        weaponPotionArmorHBox.setStyle("-fx-background-color: #333");
        itemContainer.setAlignment(Pos.CENTER_LEFT);
        // itemContainer.setSpacing(-3);
        itemContainer.getChildren().addAll(itemLabel, weaponPotionArmorHBox);

        heartsContainer = new VBox();
        Label heartLabel = new Label("Hearts");
        heartImage = new Image("sprites/heartFull.png", 30, 30, true, true);
        heartsList = new ArrayList<ImageView>();
        for (int i = 0; i < 5; i++) {
            heartsList.add(new ImageView(heartImage));
        }
        HBox fiveHearts = new HBox();
        fiveHearts.setSpacing(-8);
        fiveHearts.getChildren().addAll(heartsList);
        heartsContainer.setAlignment(Pos.CENTER_LEFT);
        heartsContainer.getChildren().addAll(heartLabel, fiveHearts);

        rubyContainer = new VBox();
        Label rubyLabel = new Label("Rubies");
        // rubyLabel.setId("rubyLabel");
        Image ruby = new Image("sprites/ruby.png", 30, 30, true, true);
        ImageView rubyImageView = new ImageView(ruby);
        numRuby = new Label();
        HBox rubyImageAndValue = new HBox();
        rubyImageAndValue.getChildren().addAll(rubyImageView, numRuby);
        rubyImageAndValue.setAlignment(Pos.CENTER_LEFT);

        rubyContainer.getChildren().addAll(rubyLabel, rubyImageAndValue);
        rubyContainer.setAlignment(Pos.CENTER_LEFT);

        inventoryButtonContainer = new VBox();
        inventoryButtonContainer.setVisible(false);
        inventoryButtonContainer.setMinWidth(100);
        inventory = new Sprite();
        inventory.addToLayer(LayerEnum.GAME_LAYER);
        inventoryButton = new Button("Inventory");
        inventory.addButton(inventoryButton, 205, 238);

        shopButtonContainer = new VBox();
        shopButtonContainer.setVisible(false);
        shop = new Sprite();
        shop.addToLayer(LayerEnum.GAME_LAYER);
        shopButton = new Button("Shop");
        shop.addButton(shopButton, 320, 238);
        shopButtonContainer.setAlignment(Pos.CENTER);
        shopButtonContainer.setMinWidth(75);


    }

    // update HUD information
    public static void updatePlayerInformation(String name) {
        playerName.setText(name);
    }

    public static void updateWeapon(String weapon) {
        if (weapon.equals("BROADSWORD")) {
            weaponImage = new Image("sprites/sword.png");
        } else if (weapon.equals("MACE")) {
            weaponImage = new Image("sprites/mace.png");
        } else {
            weaponImage = new Image("sprites/dagger.png");
        }
        weaponImageView.setImage(weaponImage);
    }

    public static void updateArmor(String armor) {
        if (armor.equals("CHAINMAIL")) {
            armorImage = new Image("sprites/armorChainmail.png");
        } else if (armor.equals("RUBY")) {
            armorImage = new Image("sprites/armorRuby.png");
        } else {
            armorImage = new Image("sprites/armorLeather.png");
        }
        armorImageView.setImage(armorImage);
    }

    public static void updateHealingPotionCount(int numPotions) {
        potionHealingCount.setText("x" + numPotions);
    }

    public static void updateAttackPotionCount(int numPotions) {
        potionAttackCount.setText("x" + numPotions);
    }

    public static void updateRubies(int numRubies) {
        numRuby.setText("x" + numRubies);
    }

    public static void updateHearts(double hearts) {
        int wholeHearts = (int) hearts;
        double partialHearts = hearts - wholeHearts;

        int i;
        for (i = 0; i < wholeHearts; i++) {
            heartsList.get(i).setImage(heartImage);
        }

        if (partialHearts != 0) {
            if (partialHearts > 0.50) {
                threeQuarterHeartImage = new Image("sprites/heartThreeFourthFull.png",
                        30, 30, true, true);
                heartsList.get(i).setImage(threeQuarterHeartImage);
            } else if (partialHearts > 0.25) {
                halfHeartImage = new Image("sprites/heartHalfFull.png", 30, 30, true, true);
                heartsList.get(i).setImage(halfHeartImage);
            } else if (partialHearts > 0) {
                quarterHeartImage = new Image("sprites/heartOneFourthFull.png", 30, 30, true, true);
                heartsList.get(i).setImage(quarterHeartImage);
            }
            i++;
        }
        while (i < 5) {
            Image emptyHeartImage = new Image("sprites/heartEmpty.png", 30, 30, true, true);
            heartsList.get(i).setImage(emptyHeartImage);
            i++;
        }
    }

    public static Sprite getInventory() {
        return inventory;
    }

    public static Sprite getShop() {
        return shop;
    }

    // Getters
    public HBox getHeadsUpDisplay() {
        return headsUpDisplay;
    }

}
