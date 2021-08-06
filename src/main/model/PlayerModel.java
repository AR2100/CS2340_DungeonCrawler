package model;

import controller.Controller;
import resources.ArmorEnum;
import resources.Difficulty;
import resources.DifficultyConstants;
import resources.Direction;
import resources.WeaponEnum;

import java.util.ArrayList;

public class PlayerModel {

    private String playerName;
    private Difficulty difficulty;

    private String weapon;
    private ArrayList<WeaponEnum> playerWeapons;
    private ArrayList<PotionModel> playerPotions;
    private ArmorEnum playerArmor;
    private WeaponEnum equippedWeapon;

    private int rubies;
    private int damageAddition;
    private int powerSwingsLeft;

    private double hearts;
    private static int maxHearts = 5;

    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private boolean isInvincible = false;
    private double invincibilityTimer = -1;
    private final double invincibilityMaxTime = 1;

    private boolean isFrozen = false;
    private double frozenTimer = -1;
    private final double frozenMaxTime = 1;

    private double xVelocity = 0;
    private double yVelocity = 0;

    // we always start in room 0
    private int currentRoom = 0;
    private Direction entranceDir = Direction.NONE;

    public PlayerModel() {
        this("placeholder", Difficulty.EASY, WeaponEnum.BROADSWORD, ArmorEnum.LEATHER, 0, 5);
    }

    public PlayerModel(String name, Difficulty difficulty, WeaponEnum weapon,
                       ArmorEnum armor, int rubies, double hearts) {
        this.playerName = name;
        this.difficulty = difficulty;
        playerWeapons = new ArrayList<>();
        playerPotions = new ArrayList<>();

        addWeapon(weapon);
        setEquippedWeapon(weapon);

        this.playerArmor = armor;
        resetPlayer();
    }

    public void resetPlayer() {
        // playerWeapons.clear();
        playerArmor = ArmorEnum.LEATHER;
        playerPotions.clear();
        hearts = maxHearts;
        rubies = DifficultyConstants.getStartingRuby(difficulty);

        rightPressed = false;
        leftPressed = false;
        upPressed = false;
        downPressed = false;

        isInvincible = false;
        invincibilityTimer = -1;

        isFrozen = false;
        frozenTimer = -1;

        xVelocity = 0;
        yVelocity = 0;

        currentRoom = 0;
        entranceDir = Direction.NONE;
    }

    public void addWeapon(WeaponEnum weapon) {
        for (int i = 0; i < playerWeapons.size(); i++) {
            if (weapon == playerWeapons.get(i)) {
                return;
            }
        }
        playerWeapons.add(weapon);
    }

    public void removeWeapon(WeaponEnum weapon) {
        for (int i = 0; i < playerWeapons.size(); i++) {
            if (weapon == playerWeapons.get(i)) {
                playerWeapons.remove(i);
                break;
            }
        }
    }

    public void addPotion(PotionModel potion) {
        playerPotions.add(potion);
    }

    public void removePotion(String name) {
        for (int i = 0; i < playerPotions.size(); i++) {
            if (name.equals(playerPotions.get(i).getPotionType())) {
                playerPotions.remove(i);
                break;
            }
        }
    }

    public void setEquippedWeapon(WeaponEnum weapon) {
        if (playerWeapons.contains(weapon)) {
            this.equippedWeapon = weapon;
        } else {
            throw new RuntimeException("Player does not have the " + weapon.toString() + "weapon.");
        }
    }

    public WeaponEnum getEquippedWeapon() {

        return equippedWeapon;
    }

    public ArmorEnum getPlayerArmor() {
        return playerArmor;
    }

    public void setPlayerArmor(ArmorEnum armor) {
        if (playerArmor != armor) {
            playerArmor = armor;
        }
    }

    /**
     * method that adds to player's hearts
     * @param numHearts the number of hearts to add
     * @return the total player's hearts
     */
    public double addHearts(double numHearts) {
        double heartsToAdd = numHearts;
        if (hearts + heartsToAdd > PlayerModel.maxHearts) {
            hearts = PlayerModel.maxHearts;
        } else {
            hearts += heartsToAdd;
        }
        return hearts;
    }

    /**
     * method that removes from player's hearts
     * @param numHearts the number of hearts to remove
     * @return the total player's hearts
     */
    public double removeHearts(double numHearts) {
        double heartsToRemove = numHearts;
        if (hearts - heartsToRemove < 0) {
            hearts = 0;
            System.out.println("Game Over");
        } else {
            hearts -= heartsToRemove;
        }
        return hearts;
    }

    public void updateInvincibilityTimer() {
        invincibilityTimer -= Controller.getFrameMills() / 1000.0;

        if (invincibilityTimer <= 0) {
            isInvincible = false;
        }
    }

    public void updateFrozenTimer() {
        frozenTimer -= Controller.getFrameMills() / 1000.0;

        if (frozenTimer <= 0) {
            isFrozen = false;
        }
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }
    public boolean isUpPressed() {
        return upPressed;
    }
    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }
    public boolean isDownPressed() {
        return downPressed;
    }
    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }
    public double getxVelocity() {
        return xVelocity;
    }
    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }
    public double getyVelocity() {
        return yVelocity;
    }
    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
    public boolean isInvincible() {
        return isInvincible;
    }
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
        invincibilityTimer = invincibilityMaxTime;
    }
    public boolean isFrozen() {
        return isFrozen;
    }
    public void setFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
        frozenTimer = frozenMaxTime;
    }
    public ArrayList<WeaponEnum> getPlayerWeapons() {
        return playerWeapons;
    }
    public ArrayList<PotionModel> getPlayerPotions() {
        return playerPotions;
    }

    //For testing purposes only
    public void setForeverInvincible() {
        this.isInvincible = true;
        invincibilityTimer = Integer.MAX_VALUE;
    }
    // Getters and setters
    public void setPlayerName(String name) {
        this.playerName = name;
    }
    public String getPlayerName() {
        return this.playerName;
    }
    public void setDifficulty(Difficulty diff) {
        this.difficulty = diff;
    }
    public Difficulty getDifficulty() {
        return this.difficulty;
    }
    public int getRuby() {
        return this.rubies;
    }
    public void setRuby(int rubies) {
        this.rubies = rubies;
    }
    public double getHearts() {
        return this.hearts;
    }
    public void setHearts(double hearts) {
        this.hearts = hearts;
    }

    public void setDamageAddition(int damageAddition) {
        this.damageAddition = damageAddition;
    }

    public void clearPlayerWeapons() {
        playerWeapons.clear();
    }

    public void clearPlayerMovementInputData() {
        rightPressed = false;
        leftPressed = false;
        upPressed = false;
        downPressed = false;
    }
    public void setPowerSwingsLeft(int powerSwingsLeft) {
        this.powerSwingsLeft = powerSwingsLeft;
        if (this.powerSwingsLeft < 0) {
            this.powerSwingsLeft = 0;
        }
    }

    public void subtractPowerSwing() {
        powerSwingsLeft--;
        if (powerSwingsLeft <= 0) {
            powerSwingsLeft = 0;
            setDamageAddition(0);
        }
    }
    public int getDamageAddition() {
        return damageAddition;
    }
    public int getCurrentRoom() {
        return currentRoom;
    }
    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }
    public Direction getEntranceDir() {
        return entranceDir;
    }
    public void setEntranceDir(Direction entranceDir) {
        this.entranceDir = entranceDir;
    }
}
