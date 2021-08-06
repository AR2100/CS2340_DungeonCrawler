package model;

import resources.LayerEnum;
import resources.SpriteType;

public class MonsterModel {
    private double health;
    private double attack;
    private double xSpeed;
    private double ySpeed;
    private int money;
    private boolean playerPresent;
    private boolean isDead;

    private Sprite monsterSprite;

    private double currentKnockback = 0;

    public MonsterModel(double health, double attack, double xSpeed, double ySpeed, int id) {
        this.health = health;
        this.attack = attack;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        playerPresent = false;
        isDead = false;

        createSprite(id);
    }

    private void createSprite(int id) {
        monsterSprite = new Sprite();
        monsterSprite.addImage(id);
        monsterSprite.setCenter(monsterSprite.getScaledWidth(),
                monsterSprite.getScaledHeight(), 0.5, 0.5, true);
        monsterSprite.playAnimation();

        monsterSprite.addBoxCollider(20, 20);

        setActive(false);

        monsterSprite.addSpriteType(SpriteType.HOSTILE);
        monsterSprite.addToLayer(LayerEnum.GAME_LAYER);
    }

    public void setActive(boolean isActive) {
        monsterSprite.getBoxCollider().setEnabled(isActive);
        monsterSprite.setSpriteVisibility(isActive);
    }

    public double updateKnockback() {
        if (currentKnockback > 0) {
            currentKnockback -= 0.05;
        } else {
            currentKnockback = 0;
        }

        return currentKnockback;
    }

    public void adjustHealth(double amount) {
        health += amount;
    }

    public double getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public double getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public double getXSpeed() {
        return xSpeed;
    }
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    public double getYSpeed() {
        return ySpeed;
    }
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    public boolean isPlayerPresent() {
        return playerPresent;
    }
    public void setPlayerPresent(boolean playerPresent) {
        this.playerPresent = playerPresent;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public boolean isDead() {
        return isDead;
    }
    public void setDead(boolean dead) {
        isDead = dead;
    }
    public Sprite getMonsterSprite() {
        return monsterSprite;
    }
    public double getCurrentKnockback() {
        return currentKnockback;
    }
    public void setCurrentKnockback(double currentKnockback) {
        this.currentKnockback = currentKnockback;
    }
}
