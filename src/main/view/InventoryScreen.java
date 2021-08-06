package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InventoryScreen {
    private int width;
    private int height;
    private Font oldLondon;
    private VBox screenVBox;
    private Button useItem;
    private ComboBox playerItems;

    public InventoryScreen(int width, int height) {
        this.width = width;
        this.height = height;

        setUpInventory();

        //call setup code here;
    }

    public void setUpInventory() {
        screenVBox = new VBox();
        screenVBox.setAlignment(Pos.CENTER);
        screenVBox.setSpacing(20);

        playerItems = new ComboBox();
        useItem = new Button("Use Item");

        screenVBox.getChildren().addAll(playerItems, useItem);
    }

    public Scene getScene() {
        return new Scene(screenVBox, width, height);
    }

    public Button getUseItem() {
        return useItem;
    }

    public ComboBox getPlayerItems() {
        return playerItems;
    }

    public void setPlayerItems(ComboBox playerItems) {
        this.playerItems = playerItems;
    }


    //setup code here;


}
