package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;

public class ShopScreen {
    private int width;
    private int height;
    private Font oldLondon;
    private VBox screenVBox;

    private VBox itemVBox;
    Label healthPotionLabel;
    Label healthPotionCost;
    Label attackPotionLabel;
    Label attackPotionCost;
    Label weapon1Label;
    Label weapon1Cost;
    Label weapon2Label;
    Label weapon2Cost;

    Label errorLabel;

    Button healthPotionButton;
    Button attackPotionButton;
    Button weapon1Button;
    Button weapon2Button;

    HBox healthPotionHBox;
    HBox attackPotionHBox;
    HBox weapon1HBox;
    HBox weapon2HBox;

    public ShopScreen(int width, int height) {
        this.width = width;
        this.height = height;

        setupShop();
        screenVBox.getChildren().addAll(itemVBox);
    }

    public void setupShop() {
        screenVBox = new VBox();
        screenVBox.setAlignment(Pos.CENTER);

        itemVBox = new VBox();
        healthPotionHBox = new HBox();
        healthPotionLabel = new Label("Health Potion");
        healthPotionCost = new Label("10");
        healthPotionButton = new Button("Buy");
        healthPotionHBox.setAlignment(Pos.CENTER);
        healthPotionHBox.setSpacing(20);
        healthPotionHBox.getChildren().addAll(healthPotionLabel, healthPotionCost, healthPotionButton);

        attackPotionHBox = new HBox();
        attackPotionLabel = new Label("Attack Potion");
        attackPotionCost = new Label("10");
        attackPotionButton = new Button("Buy");
        attackPotionHBox.setAlignment(Pos.CENTER);
        attackPotionHBox.setSpacing(20);
        attackPotionHBox.getChildren().addAll(attackPotionLabel, attackPotionCost, attackPotionButton);

        weapon1HBox = new HBox();
        weapon1Label = new Label("");
        weapon1Cost = new Label("30");
        weapon1Button = new Button("Buy");
        weapon1HBox.setAlignment(Pos.CENTER);
        weapon1HBox.setSpacing(20);
        weapon1HBox.getChildren().addAll(weapon1Label, weapon1Cost, weapon1Button);

        weapon2HBox = new HBox();
        weapon2Label = new Label("");
        weapon2Cost = new Label("30");
        weapon2Button = new Button("Buy");
        weapon2HBox.setAlignment(Pos.CENTER);
        weapon2HBox.setSpacing(20);
        weapon2HBox.getChildren().addAll(weapon2Label, weapon2Cost, weapon2Button);

        errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);

        itemVBox.setAlignment(Pos.CENTER);
        itemVBox.setSpacing(10);
        itemVBox.getChildren().addAll(healthPotionHBox, attackPotionHBox, weapon1HBox, weapon2HBox, errorLabel);

    }

    public void updateWeapon1Label(String weapon1) {
        weapon1Label.setText(weapon1);
        if(weapon1Label.getText().equals("Removed")) {
            weapon1HBox.setVisible(false);
        }
    }
    public void updateWeapon2Label(String weapon2) {
        weapon2Label.setText(weapon2);
        if(weapon2Label.getText().equals("Removed")) {
            weapon2HBox.setVisible(false);
        }
    }

    public Label getWeapon1Label() {
        return weapon1Label;
    }
    public Label getWeapon2Label() {
        return weapon2Label;
    }
    public Label getErrorLabel() {
        return errorLabel;
    }
    public HBox getWeapon1HBox() {
        return weapon1HBox;
    }
    public HBox getWeapon2HBox() {
        return weapon2HBox;
    }
    public Button getHealthPotionButton() {
        return healthPotionButton;
    }
    public Button getAttackPotionButton() {
        return attackPotionButton;
    }
    public Button getWeapon1Button() {
        return weapon1Button;
    }
    public Button getWeapon2Button() {
        return weapon2Button;
    }

    public Scene getScene() {
        return new Scene(screenVBox, width, height);
    }
}
