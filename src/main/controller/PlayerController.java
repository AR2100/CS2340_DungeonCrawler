package controller;

import model.*;
import resources.LayerEnum;
import resources.Direction;
import resources.SpriteType;
import resources.DirectionUtils;
import java.util.ArrayList;
import java.util.Iterator;
import resources.Difficulty;

/** This class controls the player in the game environment.
 *
 * PlayerController manages the player's data (PlayerModel) and the
 *  player's Sprite.
 *
 */

public class PlayerController {

    private PlayerModel playerModel;
    private WeaponController weaponController;
    private PotionController potionController;
    private Sprite playerSprite;
    private Controller controller;

    private double rot = 0;

    private boolean permanentInvincible;

    public PlayerController(Controller controller) {
        this.controller = controller;
        playerModel = new PlayerModel();
        weaponController = new WeaponController(this.controller);

        permanentInvincible = false;
    }

    // Setup the player's sprite
    public void setupPlayerSprite() {
        playerSprite = new Sprite();
        playerSprite.addImage(21);
        playerSprite.setCenter(playerSprite.getScaledWidth(),
                playerSprite.getScaledHeight(), 0.5, 0.5, true);
        playerSprite.setPosition(0, 0);
        playerSprite.addBoxCollider(15, 30);
        playerSprite.addSpriteType(SpriteType.PLAYER);

        playerSprite.addToLayer(LayerEnum.GAME_LAYER);

        weaponController.setupWeaponSprite();
    }

    /* Update the player's position.
     *  Updated every frame.
     */
    public void updatePlayer() {
        double newXPos;
        double newYPos;

        newXPos = calculateNewX();
        newXPos = handleMovementCollisionX(newXPos, playerSprite.getPosition().getYPos());

        newYPos = calculateNewY();
        newYPos = handleMovementCollisionY(newXPos, newYPos);

        if (playerModel.isInvincible()) {
            playerModel.updateInvincibilityTimer();
        }

        if (playerModel.isFrozen()) {
            playerModel.updateFrozenTimer();
            newXPos = playerSprite.getPosition().getXPos();
            newYPos = playerSprite.getPosition().getYPos();
        }

        handleSpriteCollision();

        RoomController roomController = controller.getRoomController();
        int newRoom = playerModel.getCurrentRoom();

        if (newRoom != -1 && newRoom != roomController.getCurrPos()) {
            roomController.switchRooms(newRoom, playerModel.getEntranceDir(), playerSprite);
        } else {
            playerSprite.setPosition(newXPos, newYPos);
        }

        weaponController.updateWeapon();
    }

    private double calculateNewX() {
        if (playerModel.isRightPressed() && !playerModel.isLeftPressed()) {
            playerModel.setxVelocity(5);
        } else if (playerModel.isLeftPressed() && !playerModel.isRightPressed()) {
            playerModel.setxVelocity(-5);
        } else {
            playerModel.setxVelocity(0);
        }

        return playerSprite.getPosition().getXPos() + playerModel.getxVelocity();
    }
    private double calculateNewY() {
        if (playerModel.isUpPressed() && !playerModel.isDownPressed()) {
            playerModel.setyVelocity(5);
        } else if (playerModel.isDownPressed() && !playerModel.isUpPressed()) {
            playerModel.setyVelocity(-5);
        } else {
            playerModel.setyVelocity(0);
        }

        return playerSprite.getPosition().getYPos() + playerModel.getyVelocity();
    }
    private double handleMovementCollisionX(double newX, double oldY) {

        Iterator<Sprite> collisionIter = playerSprite.checkValidPosition(newX, oldY);

        while (collisionIter.hasNext()) {
            Sprite sprite = collisionIter.next();

            if (sprite != null) {
                BoxCollider spriteBox = playerSprite.getBoxCollider();
                BoxCollider colliderBox = sprite.getBoxCollider();

                if (sprite instanceof TriggerZone) {
                    TriggerZone triggerZone = (TriggerZone)(sprite);
                    playerModel.setCurrentRoom(triggerZone.getDestination());
                    playerModel.setEntranceDir(
                            DirectionUtils.getOppositeDirection(triggerZone.getExitDirection()));
                    return newX;
                } else if (sprite.getType() == SpriteType.WALL) {
                    if (playerModel.isLeftPressed()) {
                        return colliderBox.getRight()
                                + spriteBox.getBoxColliderWidth() / 2.0 + 0.2;
                    } else if (playerModel.isRightPressed()) {
                        return colliderBox.getLeft()
                                - spriteBox.getBoxColliderWidth() / 2.0 - 0.2;
                    }
                }
            }
        }

        return newX;
    }
    private double handleMovementCollisionY(double newX, double newY) {
        Iterator<Sprite> collisionIter = playerSprite.checkValidPosition(newX, newY);

        while (collisionIter.hasNext()) {
            Sprite sprite = collisionIter.next();

            if (sprite != null) {
                BoxCollider spriteBox = playerSprite.getBoxCollider();
                BoxCollider colliderBox = sprite.getBoxCollider();
                if (sprite instanceof TriggerZone) {
                    TriggerZone triggerZone = (TriggerZone)(sprite);
                    playerModel.setCurrentRoom(triggerZone.getDestination());
                    playerModel.setEntranceDir(
                            DirectionUtils.getOppositeDirection(triggerZone.getExitDirection()));
                    return newY;
                } else if (sprite.getType() == SpriteType.WALL) {
                    if (playerModel.isUpPressed()) {
                        return colliderBox.getBottom()
                                - spriteBox.getBoxColliderHeight() / 2.0 - 0.2;
                    } else if (playerModel.isDownPressed()) {
                        return colliderBox.getTop()
                                + spriteBox.getBoxColliderHeight() / 2.0 + 0.2;
                    }
                }
            }
        }

        return newY;
    }
    private void handleSpriteCollision() {
        Iterator<Sprite> collisionIter = GameLayer.checkForCollisions(playerSprite);
        controller.getRoomController().setBedTouched(false);

        while (collisionIter.hasNext()) {

            Sprite sprite = collisionIter.next();

            if (sprite != null && sprite.getType()
                    != SpriteType.WEAPON && sprite.getType() != SpriteType.UNDEFINED) {
                if (sprite.getType() == SpriteType.HOSTILE && !playerModel.isInvincible()) {
                    MonsterController monsterController = controller.getMonsterController();
                    MonsterModel monsterModel = monsterController.findMonster(sprite);
                    double damage = 0;


                    if (monsterModel != null && !permanentInvincible) {
                        damage = monsterController.findMonster(sprite).getAttack();
                    }

                    double difficultyAdjust = 1.0;

                    if (playerModel.getDifficulty() == Difficulty.EASY) {
                        difficultyAdjust = 0.8;
                    } else if (playerModel.getDifficulty() == Difficulty.HARD) {
                        difficultyAdjust = 1.5;
                    }

                    String armor = playerModel.getPlayerArmor().toString();
                    double protection;
                    if (armor.equals("CHAINMAIL")) {
                        protection = 0.50;
                    } else if (armor.equals("RUBY")) {
                        protection = 0.25;
                    } else {
                        protection = 1;
                    }

                    playerModel.removeHearts(damage * protection * difficultyAdjust);
                    playerModel.setInvincible(true);
                    break;
                } else if (sprite.getType() == SpriteType.POTION) {
                    System.out.println("Picked up a potion!");

                    RoomController roomController = controller.getRoomController();

                    sprite.setSpriteVisibility(false);
                    sprite.getBoxCollider().setEnabled(false);

                    playerModel.addPotion(roomController.getPotionController().findPotion(sprite));
                    roomController.getPotionController().removePotion(sprite);
                } else if (sprite.getType() == SpriteType.CHEST) {
                    RoomController roomController = controller.getRoomController();
                    System.out.println("added "
                            + roomController.getChestController().removeChest(sprite)
                            + " to your inventory");

                    sprite.setSpriteVisibility(false);
                    sprite.getBoxCollider().setEnabled(false);
                } else if (sprite.getType() == SpriteType.RUBY) {
                    RoomController roomController = controller.getRoomController();
                    System.out.println("added ruby to your inventory");
                    PlayerModel playerModel = roomController.getPlayerController().getPlayerModel();
                    playerModel.setRuby(playerModel.getRuby()
                            + roomController.getRubyController().findRuby(sprite).getValue());
                    sprite.setSpriteVisibility(false);
                    sprite.getBoxCollider().setEnabled(false);
                    roomController.getRubyController().removeRuby(sprite);
                } else if (sprite.getType() == SpriteType.DART
                        && controller.getMonsterController().getCurrentMonsters().hasNext()
                        && !controller.getMonsterController().getCurrentMonsters().next().isDead()) {
                    playerModel.setFrozen(true);
                    sprite.setSpriteVisibility(false);
                    sprite.getBoxCollider().setEnabled(false);
                } else if (sprite.getType() == SpriteType.BED) {
                    if (!controller.getRoomController().isChallengeDeclined()) {
                        controller.initChallengeScreenPrompt();
                    }
                    controller.getRoomController().setBedTouched(true);
                }
            }
        }

        if (controller.getRoomController().isBedTouched() == false) {
            controller.getRoomController().setChallengeDeclined(false);
        }
    }

    public void resetPlayerData() {
        playerModel.resetPlayer();
    }

    public void clearPlayerInputData() {
        playerModel.clearPlayerMovementInputData();
        getWeaponController().getEquippedWeaponModel().clearWeaponInputData();
    }

    // Getters
    public PlayerModel getPlayerModel() {
        return playerModel;
    }
    public WeaponController getWeaponController() {
        return weaponController;
    }
    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public boolean isPermanentInvincible() {
        return permanentInvincible;
    }

    public void setPermanentInvincible(boolean permanentInvincible) {
        this.permanentInvincible = permanentInvincible;
    }
}