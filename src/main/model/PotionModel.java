package model;

import resources.SpriteType;
import resources.LayerEnum;

public class PotionModel {
    private String potionType;
    private double value;
    private Sprite potionSprite;

    public PotionModel() {
        this("health", 1);
    }

    public PotionModel(String potionType, double value) {
        this.potionType = potionType;
        this.value = value;

        createSprite();
    }

    private void createSprite() {
        potionSprite = new Sprite();
        if (potionType.equals("health")) {
            potionSprite.addImage(25);
        } else if (potionType.equals("attack")) {
            potionSprite.addImage(26);
        }

        potionSprite.setCenter(potionSprite.getScaledWidth(),
                potionSprite.getScaledHeight(), 0.5, 0.5, true);

        potionSprite.addBoxCollider(15, 15);

        potionSprite.addSpriteType(SpriteType.POTION);
        potionSprite.addToLayer(LayerEnum.GAME_LAYER);
    }

    public void setActive(boolean isActive) {
        potionSprite.getBoxCollider().setEnabled(isActive);
        potionSprite.setSpriteVisibility(isActive);
    }

    public String getPotionType() {
        return potionType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Sprite getPotionSprite() {
        return potionSprite;
    }
}
