package view;

import javafx.scene.image.Image;
import javafx.util.Duration;
import model.Sprite;
import resources.Direction;
import resources.LayerEnum;
import resources.SpriteType;


public class Walls {

    private Image straightWallImage;
    private Image cornerWallImage;

    private final int gridWidth = 16;
    private final int gridHeight = 8;

    private final int spriteWidth = 32;
    private final int spriteHeight = 32;

    private Duration backgroundAnimationDuration;
    private Sprite[][] wallSprites;

    public Walls() {
        backgroundAnimationDuration = Duration.millis(1500);

        setupWallGridPane();
    }

    // setup wall elements
    private void setupWallGridPane() {

        wallSprites = new Sprite[gridWidth][gridHeight];

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Sprite currentSprite = new Sprite();
                currentSprite.addToLayer(LayerEnum.GAME_LAYER);
                currentSprite.addSpriteType(SpriteType.WALL);

                double xPos = 50 * i - 400 + 25;
                double yPos = 200 - 50 * j - 25;

                if (i == 0 || i == gridWidth - 1) {
                    //first or last column
                    if (i == 0 && j == 0) {
                        //top left
                        currentSprite.addImage(0);
                        // currentSprite.setImageRotation(180);
                    } else if (i == 0 && j == gridHeight - 1) {
                        //bottom left
                        currentSprite.addImage(0);
                        // currentSprite.setImageRotation(-90);
                    } else if (i == gridWidth - 1 && j == 0) {
                        //top right
                        currentSprite.addImage(0);
                        // currentSprite.setImageRotation(90);
                    } else if (i == gridWidth - 1 && j == gridHeight - 1) {
                        //bottom right
                        currentSprite.addImage(0);
                        // currentSprite.setImageRotation(0);
                    } else if (i == 0) {
                        //vertical wall
                        if (j == 1 || j == gridHeight - 2) {
                            currentSprite.addImage(1);
                        } else {
                            currentSprite.addImage(0);
                        }

                        currentSprite.setImageRotation(-90);
                    } else if (i == gridWidth - 1) {
                        if (j == 1 || j == gridHeight - 2) {
                            currentSprite.addImage(1);
                        } else {
                            currentSprite.addImage(0);
                        }

                        currentSprite.setImageRotation(90);
                    }
                } else if (j == 0) {
                    //top row; horizontal wall
                    if (i == 1 || i == gridWidth - 2
                            || i == (gridWidth / 2 - 2) || i == (gridWidth / 2 + 1)) {
                        currentSprite.addImage(1);
                    } else {
                        currentSprite.addImage(0);
                    }

                    currentSprite.setImageRotation(180);
                } else if (j == gridHeight - 1) {
                    //bottom row; horizontal wall
                    if (i == 1 || i == gridWidth - 2
                            || i == (gridWidth / 2 - 2) || i == (gridWidth / 2 + 1)) {
                        currentSprite.addImage(1);
                    } else {
                        currentSprite.addImage(0);
                    }
                    currentSprite.setImageRotation(0);
                }

                currentSprite.setPosition(xPos, yPos);

                if (currentSprite.hasImage()) {
                    currentSprite.setCenter(currentSprite.getScaledWidth(),
                            currentSprite.getScaledHeight(),
                            0.5, 0.5, true);
                    currentSprite.setSpriteVisibility(true);
                    currentSprite.addBoxCollider(currentSprite.getScaledWidth(),
                            currentSprite.getScaledHeight());
                }
                if (currentSprite.hasAnimation()) {
                    currentSprite.playAnimation();
                }

                if (isWallAnExit(i, j) && currentSprite.hasImage()) {
                    currentSprite.setSpriteVisibility(false);
                    currentSprite.getBoxCollider().setEnabled(false);
                }

                wallSprites[i][j] = currentSprite;
            }
        }
    }

    // Exit visibility and handling
    public void setExitVisibility(Direction dir, boolean isVisible) {
        Sprite[] exits = getExitSprites(dir);
        exits[0].setSpriteVisibility(isVisible);
        exits[0].getBoxCollider().setEnabled(isVisible);
        exits[1].setSpriteVisibility(isVisible);
        exits[1].getBoxCollider().setEnabled(isVisible);
    }
    public boolean isExitVisible(Direction dir) {
        Sprite[] exits = getExitSprites(dir);
        return exits[0].isSpriteVisible() && exits[1].isSpriteVisible();
    }
    private boolean isWallAnExit(int i, int j) {
        boolean middleXaxis = (i == gridWidth / 2 || i == gridWidth / 2 - 1);
        boolean middleYaxis = (j == gridHeight / 2 || j == gridHeight / 2 - 1);
        boolean onTopOrBottom = (j == 0 || j == gridHeight - 1);
        boolean onLeftOrRight = (i == 0 || i == gridWidth - 1);

        return (middleXaxis && onTopOrBottom) || (middleYaxis && onLeftOrRight);
    }

    // Getter
    public Sprite[] getExitSprites(Direction dir) {
        Sprite[] arr = new Sprite[2];
        if (dir == Direction.NORTH) {
            arr[0] = wallSprites[gridWidth / 2 - 1][0];
            arr[1] = wallSprites[gridWidth / 2][0];
        } else if (dir == Direction.SOUTH) {
            arr[0] = wallSprites[gridWidth / 2 - 1][gridHeight - 1];
            arr[1] = wallSprites[gridWidth / 2][gridHeight - 1];
        } else if (dir == Direction.EAST) {
            arr[0] = wallSprites[gridWidth - 1][gridHeight / 2 - 1];
            arr[1] = wallSprites[gridWidth - 1][gridHeight / 2];
        } else if (dir == Direction.WEST) {
            arr[0] = wallSprites[0][gridHeight / 2 - 1];
            arr[1] = wallSprites[0][gridHeight / 2];
        }

        return arr;
    }
}
