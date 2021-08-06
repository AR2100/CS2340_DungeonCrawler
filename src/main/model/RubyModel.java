package model;

import resources.LayerEnum;
import resources.SpriteType;

public class RubyModel {
    private int value;
    private Sprite rubySprite;

    public RubyModel() {
        this(5);
    }

    public RubyModel(int value) {
        this.value = value;
        createSprite();
    }

    private void createSprite() {
        rubySprite = new Sprite();
        rubySprite.addImage(13);

        rubySprite.setCenter(rubySprite.getScaledWidth(),
                rubySprite.getScaledHeight(), 0.5, 0.5, true);

        rubySprite.addBoxCollider(15, 15);

        rubySprite.addSpriteType(SpriteType.RUBY);
        rubySprite.addToLayer(LayerEnum.GAME_LAYER);
    }

    public void setActive(boolean isActive) {
        rubySprite.getBoxCollider().setEnabled(isActive);
        rubySprite.setSpriteVisibility(isActive);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Sprite getRubySprite() {
        return rubySprite;
    }
}
