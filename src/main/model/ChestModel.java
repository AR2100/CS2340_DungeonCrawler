package model;

import resources.LayerEnum;
import resources.SpriteType;

public class ChestModel {
    private String treasure;
    private Sprite chestSprite;

    public ChestModel() {
        this("");
    }
    //To potentially customize specific chests
    public ChestModel(String treasureName) {
        this.treasure = treasureName;
        createSprite();
    }

    public void createSprite() {
        chestSprite = new Sprite();
        chestSprite.addImage(35);
        chestSprite.setCenter(chestSprite.getScaledWidth(),
                chestSprite.getScaledHeight(), 0.5, 0.5, true);
        chestSprite.addBoxCollider(35, 25);
        chestSprite.addSpriteType(SpriteType.CHEST);
        chestSprite.addToLayer(LayerEnum.GAME_LAYER);
    }

    public void setActive(boolean isActive) {
        chestSprite.getBoxCollider().setEnabled(isActive);
        chestSprite.setSpriteVisibility(isActive);
    }

    public Sprite getChestSprite() {
        return chestSprite;
    }
    public String getTreasure() {
        return treasure;
    }

}
