package model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import resources.GamePosition;
import resources.Position;
import resources.SpriteType;

public class BoxCollider {

    private double width;
    private double height;

    private double xOffset;
    private double yOffset;

    private Rectangle boxColliderOutline;

    private boolean enabled = true;
    private boolean isTrigger = false;

    private Sprite sprite;
    private Position offsetPosition;

    private Circle boxNode;

    private double theta;
    private double length;
    private boolean usingOffset;

    public BoxCollider(double width, double height, Sprite sprite) {
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.offsetPosition = new Position();

        this.usingOffset = false;

        setupBoxColliderNode(offsetPosition);

        setupSpriteBoxColliderOutline();
    }

    public BoxCollider(double width, double height, Sprite sprite, Position offsetPosition) {
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.offsetPosition = offsetPosition;

        this.usingOffset = false;

        setupBoxColliderNode(this.offsetPosition);

        setupSpriteBoxColliderOutline();
    }

    public BoxCollider(double width, double height, Sprite sprite, double theta, double length) {
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.theta = theta;
        this.length = length;
        this.offsetPosition = new Position();

        this.usingOffset = true;

        setupBoxColliderNode(this.offsetPosition);

        setupSpriteBoxColliderOutline();
    }

    //private void setupSpriteBoxColliderOutline(Position pos)

    private void setupBoxColliderNode(Position offsetPosition) {
        boxNode = new Circle();

        boxNode.setFill(Color.RED);
        boxNode.setRadius(3);
        boxNode.setVisible(false);

        Position spritePos = sprite.getPosition();

        sprite.getSpriteNode().getChildren().add(boxNode);
        boxNode.setLayoutX(spritePos.getXOffset() + length * Math.cos(Math.toRadians(theta)));
        boxNode.setLayoutY(spritePos.getYOffset() - length * Math.sin(Math.toRadians(theta)));
    }

    private void setupSpriteBoxColliderOutline() {
        boxColliderOutline = new Rectangle();

        Color transparent = new Color(0, 0, 0, 0.0);

        double width = getBoxColliderWidth();
        double height = getBoxColliderHeight();

        boxColliderOutline.setStrokeWidth(1);
        boxColliderOutline.setStroke(Color.RED);
        boxColliderOutline.setFill(transparent);
        boxColliderOutline.setWidth(width);
        boxColliderOutline.setHeight(height);
        boxColliderOutline.setVisible(false);
        sprite.getSpriteNode().getChildren().add(boxColliderOutline);
        // boxColliderOutline.setRotate(sprite.getRotate().getAngle());

        Position pos = sprite.getPosition();

        boxColliderOutline.setLayoutX(pos.getXOffset() - width / 2 + length
                * Math.cos(Math.toRadians(theta)));
        boxColliderOutline.setLayoutY(pos.getYOffset() - height / 2 - length
                * Math.sin(Math.toRadians(theta)));
    }

    public Position getBoxColliderPosition() {
        Position position = new Position();

        if (sprite.getType() == SpriteType.WEAPON) {
            Point2D pt = boxNode.localToScene(0, 0);
            position = GamePosition.translateRawToRelative(pt.getX(), pt.getY());
        } else {
            position.setXPos(sprite.getPosition().getXPos());
            position.setYPos(sprite.getPosition().getYPos());
        }


        // position.setXPos(pt.getX());
        // position.setYPos(pt.getY());

        /*
        if (usingOffset) {
            position.setXPos(sprite.getPosition().getXPos() + getOffset().getXPos());
            position.setYPos(sprite.getPosition().getYPos() + getOffset().getYPos());
        } else {
            position.setXPos(sprite.getPosition().getXPos());
            position.setYPos(sprite.getPosition().getYPos());
        }
        */

        //position.setXPos(sprite.getPosition().getXPos() - width / 2);
        //position.setYPos(sprite.getPosition().getYPos() - height / 2);

        return position;
    }

    public Position getOffset() {
        Position position = new Position();

        /*
        double currentRot = Math.toDegrees(sprite.getImageView().getRotate());

        position.setXPos(length * Math.cos(Math.toRadians(theta + currentRot)));
        position.setYPos(-1 * length * Math.sin(Math.toRadians(theta + currentRot)));
        */

        return position;
    }

    public double getBoxColliderWidth() {
        return width;
    }
    public double getBoxColliderHeight() {
        return height;
    }
    public Rectangle getBoxColliderOutline() {
        return boxColliderOutline; }
    public Circle getBoxColliderCenter() {
        return boxNode;
    }
    public double getTop() {
        return getBoxColliderPosition().getYPos() + height / 2.0;
    }
    public double getBottom() {
        return getBoxColliderPosition().getYPos() - height / 2.0;
    }
    public double getLeft() {
        return getBoxColliderPosition().getXPos() - width / 2.0;
    }
    public double getRight() {
        return getBoxColliderPosition().getXPos() + width / 2.0;
    }
    public void setEnabled(boolean val) {
        this.enabled = val;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public boolean isTrigger() {
        return isTrigger;
    }
    public void setTrigger(boolean trigger) {
        isTrigger = trigger;
    }
}
