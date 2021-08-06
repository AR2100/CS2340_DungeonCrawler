package model;

import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class EffectLayer {

    private static Pane layerPane = new Pane();
    private static ArrayList<Sprite> spriteList = new ArrayList<>();

    public EffectLayer() {
        super();
    }

    public static void addSprite(Sprite sprite) {
        spriteList.add(sprite);
        layerPane.getChildren().add(sprite.getSpriteNode());
    }
    public static void removeSprite(Sprite sprite) {
        Sprite storedSprite = null;
        if (spriteList.contains(sprite)) {
            storedSprite = spriteList.get(spriteList.indexOf(sprite));
            spriteList.remove(storedSprite);
        }

        layerPane.getChildren().remove(storedSprite);
    }

    // Getters
    public static Pane getLayerPane() {
        return layerPane;
    }
}
