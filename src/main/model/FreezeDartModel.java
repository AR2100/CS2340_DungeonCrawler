package model;

import resources.LayerEnum;
import resources.SpriteType;

public class FreezeDartModel {
    private Sprite freezeDart;

    public FreezeDartModel(double x, double y) {
        freezeDart = new Sprite();
        freezeDart.addImage(28);
        freezeDart.setCenter(freezeDart.getScaledWidth(),
                freezeDart.getScaledHeight(), 0.5, 0.5, true);
        freezeDart.setPosition(x, y);

        freezeDart.addBoxCollider(20, 20);

        freezeDart.addSpriteType(SpriteType.DART);
        freezeDart.addToLayer(LayerEnum.GAME_LAYER);
    }

    public Sprite getFreezeDart() {
        return freezeDart;
    }
}
