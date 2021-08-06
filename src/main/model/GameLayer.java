package model;

import javafx.scene.layout.Pane;
import resources.SpriteType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GameLayer {

    private static Pane layerPane = new Pane();
    private static ArrayList<Sprite> spriteList = new ArrayList<>();

    private static boolean boxCollidersVisible;
    private static boolean spriteCentersVisible;
    private static SpriteType[] boxColliderBlacklist;
    private static SpriteType[] spriteCenterBlacklist;

    static {
        boxCollidersVisible = false;
        spriteCentersVisible = false;

        boxColliderBlacklist = new SpriteType[] {SpriteType.WALL, SpriteType.UNDEFINED};
        spriteCenterBlacklist = new SpriteType[] {SpriteType.WALL, SpriteType.UNDEFINED};
    }


    // Manage sprites on the layer pane
    public static void addSprite(Sprite sprite) {
        spriteList.add(sprite);
        layerPane.getChildren().add(sprite.getSpriteNode());

        if (Arrays.binarySearch(boxColliderBlacklist, sprite.getType()) < 0) {
            toggleBoxColliderOutline(sprite, boxCollidersVisible);
        }

        if (Arrays.binarySearch(spriteCenterBlacklist, sprite.getType()) < 0) {
            toggleBoxColliderCenter(sprite, spriteCentersVisible);
        }
    }

    public static void removeSprite(Sprite sprite) {
        Sprite storedSprite = null;
        if (spriteList.contains(sprite)) {
            storedSprite = spriteList.get(spriteList.indexOf(sprite));

            if (Arrays.binarySearch(boxColliderBlacklist, sprite.getType()) == -1) {
                toggleBoxColliderOutline(sprite, false);
            }

            if (Arrays.binarySearch(spriteCenterBlacklist, sprite.getType()) == -1) {
                toggleBoxColliderCenter(sprite, false);
            }

            spriteList.remove(storedSprite);
        }

        layerPane.getChildren().remove(storedSprite);
    }
    public static void removeAllSprites() {
        layerPane.getChildren().clear();
        spriteList.clear();
    }

    public static void showSpriteCenters() {
        for (Sprite sprite : spriteList) {
            if (sprite.getType() != SpriteType.WALL && sprite.getType() != SpriteType.UNDEFINED
                    && sprite.getSpriteNode() != null) {
                sprite.getCenterPoint().setVisible(true);
            }
        }
    }

    public static void showBoxColliderOutline() {
        for (Sprite sprite : spriteList) {
            if (sprite.getType() != SpriteType.UNDEFINED && sprite.getBoxCollider() != null) {
                BoxCollider box = sprite.getBoxCollider();
                box.getBoxColliderOutline().setVisible(true);
                box.getBoxColliderCenter().setVisible(true);
            }
        }
    }

    private static void toggleBoxColliderOutline(Sprite sprite, boolean isEnabled) {
        if (sprite.getBoxCollider() != null && sprite.getBoxColliderOutline() != null) {
            sprite.getBoxColliderOutline().setVisible(isEnabled);
        }
    }

    private static void toggleBoxColliderCenter(Sprite sprite, boolean isEnabled) {
        if (sprite.getBoxCollider() != null
                && sprite.getBoxCollider().getBoxColliderCenter() != null) {
            sprite.getBoxCollider().getBoxColliderCenter().setVisible(isEnabled);
        }
    }

    // Manage collisions
    public static Iterator<Sprite> checkForCollisions(Sprite sprite) {

        ArrayList<Sprite> returnSprites = new ArrayList<>();

        for (Sprite secondarySprite : spriteList) {
            if (!secondarySprite.equals(sprite)
                    && checkBoxCollider(secondarySprite)
                    && checkCollision(sprite, secondarySprite)) {
                returnSprites.add(secondarySprite);
            }
        }

        return returnSprites.iterator();
    }
    private static boolean checkBoxCollider(Sprite sprite) {
        return (sprite.getBoxCollider() != null) && sprite.getBoxCollider().isEnabled();
    }
    private static boolean checkCollision(Sprite primarySprite, Sprite secondarySprite) {
        BoxCollider primaryBox = primarySprite.getBoxCollider();
        BoxCollider secondaryBox = secondarySprite.getBoxCollider();

        if (primaryBox == null || secondaryBox == null) {
            return false;
        }

        boolean outsideBottom = primaryBox.getBottom() > secondaryBox.getTop();
        boolean outsideTop = primaryBox.getTop() < secondaryBox.getBottom();
        boolean outsideLeft = primaryBox.getLeft() > secondaryBox.getRight();
        boolean outsideRight = primaryBox.getRight() < secondaryBox.getLeft();

        return !(outsideBottom || outsideTop || outsideLeft || outsideRight);
    }

    // Manage scaling
    public static void scaleSpritesToScreen(int screenWidth, int screenHeight) {
        for (Sprite sprite : spriteList) {
            if (sprite.hasImage()) {
                sprite.scaleSpriteToScreen(screenWidth, screenHeight);
            }
        }
    }

    // Getters
    public static Pane getLayerPane() {
        return layerPane;
    }
}
