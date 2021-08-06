package model;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import resources.GamePosition;
import resources.LayerEnum;
import resources.Position;
import javafx.scene.Node;
import resources.SpriteType;

import java.util.ArrayList;
import java.util.Iterator;

public class Sprite {

    private SpriteAnimation spriteAnimation;
    private BoxCollider boxCollider;

    private Group spriteNode;

    private Position position;
    private Circle centerPoint;
    private Rectangle boxColliderOutline;

    private Rotate rotate;

    private LayerEnum currentLayer;

    private SpriteType type;

    public Sprite() {
        spriteNode = new Group();

        currentLayer = LayerEnum.GAME_LAYER;

        position = new Position(0, 0, 0, 0);
        this.setPosition(0, 0);
        rotate = new Rotate();
        spriteNode.getTransforms().add(rotate);

        this.type = SpriteType.UNDEFINED;
    }

    public Sprite(double x, double y) {
        super();

        this.setPosition(x, y);
    }

    private void setupSpriteCenterCircle() {
        centerPoint = new Circle();

        centerPoint.setFill(Color.RED);
        centerPoint.setRadius(3);
        centerPoint.setVisible(false);
        spriteNode.getChildren().add(centerPoint);

        centerPoint.setLayoutX(position.getXOffset());
        centerPoint.setLayoutY(position.getYOffset());
    }

    private void setupSpriteBoxColliderOutline(Position pos) {
        boxColliderOutline = new Rectangle();

        Color transparent = new Color(0, 0, 0, 0.0);

        double width = boxCollider.getBoxColliderWidth();
        double height = boxCollider.getBoxColliderHeight();

        boxColliderOutline.setStrokeWidth(1);
        boxColliderOutline.setStroke(Color.RED);
        boxColliderOutline.setFill(transparent);
        boxColliderOutline.setWidth(width);
        boxColliderOutline.setHeight(height);
        boxColliderOutline.setVisible(false);
        spriteNode.getChildren().add(boxColliderOutline);

        boxColliderOutline.setLayoutX(pos.getXOffset() - width / 2);
        boxColliderOutline.setLayoutY(pos.getYOffset() - height / 2);
    }

    // Layer methods
    public void addToLayer(LayerEnum layer) {

        this.currentLayer = layer;

        if (currentLayer == LayerEnum.GAME_LAYER) {
            GameLayer.addSprite(this);
        } else if (currentLayer == LayerEnum.EFFECT_LAYER) {
            EffectLayer.addSprite(this);
        }
    }
    public void setLayer(LayerEnum newLayer) {
        if (currentLayer == LayerEnum.GAME_LAYER) {
            GameLayer.removeSprite(this);
        } else if (currentLayer == LayerEnum.EFFECT_LAYER) {
            EffectLayer.removeSprite(this);
        }

        currentLayer = newLayer;

        if (currentLayer == LayerEnum.GAME_LAYER) {
            GameLayer.addSprite(this);
        } else if (currentLayer == LayerEnum.EFFECT_LAYER) {
            EffectLayer.addSprite(this);
        }

        GamePosition.setPosition(position, spriteNode);
    }

    // Position methods
    public void setPosition(double x, double y) {
        position.setXPos(x);
        position.setYPos(y);

        GamePosition.setPosition(position, spriteNode);
    }
    public void setPosition(Position pos) {
        setPosition(pos.getXPos(), pos.getYPos());
    }
    public void setCenter(double objWidth, double objHeight,
                          double x, double y, boolean useScaled) {
        /* x and y are numbers between 0 and 1
         *  x = 0, y = 0 --> center is top left corner of sprite
         *  x = 1, y = 1 --> center is bottom right corner of sprite
         *  x = 0.5, y = 0.5 --> center is in middle of sprite
         */

        /*
        if (useScaled) {
            position.setCenter(getScaledWidth(), getScaledHeight(), x, y);
        } else {
            position.setCenter(spriteAnimation.getWidth(),
                    spriteAnimation.getHeight(), x, y);
        }
         */
        position.setCenter(objWidth, objHeight, x, y);
        setupSpriteCenterCircle();

        rotate.setPivotX(position.getXOffset());
        rotate.setPivotY(position.getYOffset());

        GamePosition.setPosition(position, spriteNode);
    }

    // Box collider methods
    public Iterator<Sprite> checkValidPosition(double x, double y) {

        double oldX = position.getXPos();
        double oldY = position.getYPos();

        position.setXPos(x);
        position.setYPos(y);

        Iterator<Sprite> collisionIter = GameLayer.checkForCollisions(this);

        position.setXPos(oldX);
        position.setYPos(oldY);

        return collisionIter;
    }
    public void addBoxCollider(double width, double height) {
        // Position boxColliderPosition = position;
        // boxColliderPosition.setCenter(width, height, 0.5, 0.5);

        boxCollider = new BoxCollider(width, height, this);
        setupSpriteBoxColliderOutline(position);
    }

    public void addBoxCollider(double width, double height, Position pos) {

        boxCollider = new BoxCollider(width, height, this);
        setupSpriteBoxColliderOutline(pos);
    }

    public void addBoxCollider(double width, double height, double theta, double length) {

        boxCollider = new BoxCollider(width, height, this, theta, length);
    }

    // Sprite Type
    public void addSpriteType(SpriteType type) {
        this.type = type;
    }

    // Image methods
    public void addImage(int id) {
        this.spriteAnimation = new SpriteAnimation(id);
        scaleImageView();

        spriteNode.getChildren().add(spriteAnimation.getImageView());
    }
    public void playAnimation() {
        if (spriteAnimation != null) {
            spriteAnimation.play();
        }
    }
    public void stopAnimation() {
        if (spriteAnimation != null) {
            spriteAnimation.stop();
        }
    }
    public boolean hasAnimation() {
        if (hasImage()) {
            return spriteAnimation.getAnimationInfo() != null;
        } else {
            return false;
        }
    }
    public boolean hasImage() {
        return spriteAnimation != null;
    }

    public void setImageRotation(double rot) {
        if (hasImage()) {
            rotate.setAngle(-1 * rot);
        }
    }
    public void scaleImageView() {
        if (hasImage()) {
            /* Desired gameplay screen area: 800 x 400 (leave 50 for HUD)
             *  800 / (width * gridWidth) = 800 / 512 = 25 /16
             *  32 * (25 / 16) = 50
             */
            spriteAnimation.getImageView().setFitHeight(50);
        }
    }
    public void scaleSpriteToScreen(int screenWidth, int screenHeight) {
        spriteAnimation.scaleImageToScreen(screenWidth, screenHeight);
    }
    public void setSpriteVisibility(boolean isVisible) {
        spriteAnimation.getImageView().setVisible(isVisible);
    }
    public boolean isSpriteVisible() {
        return spriteAnimation.getImageView().isVisible();
    }

    // Other sprite components
    public void addText(Text text, double xPos, double yPos) {
        spriteNode.getChildren().add(text);

        setPosition(xPos, yPos);
        // to-do: change so text fits properly
        setCenter(20, 5, 0.5, 0.5, false);

        GamePosition.setPosition(position, spriteNode);
    }
    public void addButton(Button button, double xPos, double yPos) {
        spriteNode.getChildren().add(button);

        //setDisplayDimensions(button.getMinWidth(), button.getMinHeight());
        setPosition(xPos, yPos);
        // to-do:change so button fits properly
        setCenter(button.getMinWidth(), button.getMinHeight(), 0.5, 0.5, false);

        GamePosition.setPosition(position, spriteNode);
    }

    // Getters
    public double getScaledWidth() {
        return spriteAnimation.getWidth() * (25.0 / 16.0);
    }
    public double getScaledHeight() {
        return spriteAnimation.getHeight() * (25.0 / 16.0);
    }
    public ImageView getImageView() {
        return spriteAnimation.getImageView();
    }
    public Position getPosition() {
        return position;
    }
    public Group getSpriteNode() {
        return this.spriteNode;
    }
    public BoxCollider getBoxCollider() {
        return boxCollider;
    }
    public Button getButton() {
        ObservableList<Node> children = spriteNode.getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof Button) {
                return (Button) children.get(i);
            }
        }
        return null;
    }
    public SpriteType getType() {
        return type;
    }
    public Circle getCenterPoint() {
        return centerPoint;
    }
    public Rectangle getBoxColliderOutline() {
        return boxColliderOutline;
    }
}
