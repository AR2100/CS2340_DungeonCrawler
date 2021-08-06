package model;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import resources.AnimationInfo;

public class SpriteAnimation extends Transition {

    private Image image;
    private ImageView imageView;
    private AnimationInfo animationInfo;

    private int width;
    private int height;

    private double scaleX;
    private double scaleY;

    public SpriteAnimation(int id) {

        this.width = 32;
        this.height = 32;
        this.scaleX = 1.0;
        this.scaleY = 1.0;

        if (id >= 0 && id <= 9) {
            loadBackgroundSprite(id);
        } else if (id >= 10 && id <= 19) {
            loadHUDSprite(id);
        } else if (id >= 20 && id <= 50) {
            loadGameplaySprite(id);
        }

        this.imageView = new ImageView(image);
        imageView.setViewport(new Rectangle2D(0, 0, this.width, this.height));
        imageView.setPreserveRatio(true);

        if (animationInfo != null) {
            setInterpolator(Interpolator.LINEAR);

            setCycleDuration(animationInfo.getDuration());
            setCycleCount(Animation.INDEFINITE);
        }
    }

    public void scaleImageToScreen(int screenWidth, int screenHeight) {
        double scaleFactor = screenWidth / 800.0;
        double newHeight = scaleFactor * (width * (25.0) / (16.0));
        imageView.setFitHeight(newHeight);
    }

    /* id = 0: Wall Corner
     * id = 1: Wall Torch
     * id = 2: Wall blank
     * id = 3: Floor 1
     * id = 4: Floor 2
     * id = 5: Floor 3
     */
    private void loadBackgroundSprite(int id) {
        // id = 0: Wall Corner
        if (id == 0) {
            this.image = new Image("sprites/DungeonWallCorner.png");
        } else if (id == 1) { // id = 1: Wall Torch
            this.image = new Image("sprites/DungeonWallTorch.png");
            animationInfo = new AnimationInfo(4, 2,
                    0, 0,
                    Duration.millis(750));
        } else if (id == 2) { // id = 2: Wall blank
            this.image = new Image("sprites/DungeonWallBlank.png");
        } else if (id == 3) { // id = 3: Floor 1
            this.image = new Image("sprites/DungeonTile1.png");
        } else if (id == 4) { // id = 4: Floor 2
            this.image = new Image("sprites/DungeonTile2.png");
        } else if (id == 5) { // id = 5: Floor 3
            this.image = new Image("sprites/DungeonTile3.png");
        }
    }

    /* id = 10: heartFull
     * id = 11: heartHalfFull
     * id = 12: heartEmpty
     * id = 13: ruby
     * id = 14: leatherArmor
     * id = 15: knife
     * id = 16: sword
     * id = 17: mace
     * id = 18: dagger
     */
    private void loadHUDSprite(int id) {
        // id = 10: heartFull
        if (id == 10) {
            this.image = new Image("sprites/heartFull.png");
        } else if (id == 11) { // id = 11: heartHalfFull
            this.image = new Image("sprites/heartHalfFull.png");
        } else if (id == 12) { // id = 12: heartEmpty
            this.image = new Image("sprites/heartEmpty.png");
        } else if (id == 13) { // id = 13: ruby
            this.image = new Image("sprites/rubyAnimated.png");
        } else if (id == 14) { // id = 14: leatherArmor
            this.image = new Image("sprites/leatherArmor.png");
        } else if (id == 15) { //id = 15: knife
            this.image = new Image("sprites/knife.png");
        } else if (id == 16) {
            this.image = new Image("sprites/sword.png");
        } else if (id == 17) {
            this.image = new Image("sprites/mace.png");
        } else if (id == 18) {
            this.image = new Image("sprites/dagger.png");
        }
    }

    /* id = 20: bed
     * id = 21: Player
     * id = 22: Jelly
     * id = 23: Rat
     * id = 24: Spider
     * id = 25: HealthPotion
     * id = 26: AttackPotion
     *
     * id = 30: sword placeholder
     */
    private void loadGameplaySprite(int id) {
        if (id == 20) {
            this.image = new Image("sprites/Bed.png");
            width = 64;
            height = 64;
        } else if (id == 21) {
            this.image = new Image("sprites/Player.png");
            animationInfo = new AnimationInfo(4, 2,
                    0, 0, Duration.millis(300));
        } else if (id == 22) {
            this.image = new Image("sprites/Jelly.png");
            animationInfo = new AnimationInfo(5, 2,
                    0, 0, Duration.millis(1500));
        } else if (id == 23) {
            this.image = new Image("sprites/Rat.png");
            animationInfo = new AnimationInfo(2, 1,
                    0, 0, Duration.millis(1500));
        } else if (id == 24) {
            this.image = new Image("sprites/Spider.png");
            animationInfo = new AnimationInfo(2, 1,
                    0, 0, Duration.millis(1500));
        } else if (id == 25) {
            this.image = new Image("sprites/HealthPotion.png");
        } else if (id == 26) {
            this.image = new Image("sprites/AttackPotion.png");
        } else if (id == 27) {
            this.image = new Image("sprites/DarkKnight.png");
            animationInfo = new AnimationInfo(4, 2,
                    0, 0, Duration.millis(1500));
            width = 64;
            height = 64;
        } else if (id == 28) {
            this.image = new Image("sprites/FreezeDart.png");
        } else if (id == 30) {
            this.image = new Image("sprites/sword-placeholder.png");
        } else if (id == 35) {
            this.image = new Image("sprites/chest.png");
        } else if (id == 36) {
            this.image = new Image("sprites/chestArmor.png");
        }
    }

    @Override
    protected void interpolate(double v) {

        int count  = animationInfo.getCount();
        int lastIndex = animationInfo.getLastIndex();
        int columns = animationInfo.getColumns();
        int offsetX = animationInfo.getSpritesheetOffsetX();
        int offsetY = animationInfo.getSpritesheetOffsetY();

        final int index = Math.min((int) Math.floor(v * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            animationInfo.setLastIndex(index);
        }

    }

    // Getters
    public AnimationInfo getAnimationInfo() {
        return animationInfo;
    }
    public ImageView getImageView() {
        return imageView;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
