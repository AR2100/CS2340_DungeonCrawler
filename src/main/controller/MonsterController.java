package controller;

import model.*;
import resources.SpriteType;
import resources.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import static java.util.Collections.emptyIterator;

public class MonsterController {

    private Controller controller;

    private FreezeDartModel fd = new FreezeDartModel(0, 0);
    double dartX = fd.getFreezeDart().getPosition().getXPos();
    double dartY = fd.getFreezeDart().getPosition().getYPos();
    ArrayList<FreezeDartModel> fds = new ArrayList<>();
    private boolean createFD = false;
    private double fdTimer = -1;
    private final double fdTime = 2;

    private HashMap<Integer, ArrayList<MonsterModel>> monsterList;

    public MonsterController(Controller controller) {
        this.controller = controller;

        fds.add(fd);

        monsterList = new HashMap<>();
        setupMonsterSprites();
    }

    public void updateFDTimer() {
        fdTimer -= Controller.getFrameMills() / 1000.0;

        if (fdTimer <= 0) {
            createFD = true;
        }
    }

    public void setupMonsterSprites() {
        for (int i = 1; i < 13; i++) { // num rooms
            Random random = new Random();
            int numEnemies = random.nextInt(2) + 1;

            ArrayList<MonsterModel> monsters = new ArrayList<>();

            for (int j = 0; j < numEnemies; j++) {
                int enemyType = random.nextInt(3);
                MonsterModel monster = setupMonster(enemyType);
                monsters.add(monster);
            }

            monsterList.put(i, monsters);
        }
        ArrayList<MonsterModel> monster = new ArrayList<>();
        monster.add(new BossMonsterModel());
        monsterList.put(12, monster);
    }

    public void createChallenge() {
        Random random = new Random();
        int numEnemies = random.nextInt(3) + 3;

        ArrayList<MonsterModel> moreMonsters = new ArrayList<MonsterModel>();

        for (int j = 0; j < numEnemies; j++) {
            int enemyType = random.nextInt(3);
            MonsterModel monster = setupMonster(enemyType);

            Sprite monsterSprite = monster.getMonsterSprite();

            int spawnNum = random.nextInt(4);
            RoomController roomController = controller.getRoomController();
            Position spawnPos;

            if (spawnNum == 0) {
                spawnPos = roomController.getNorthSpawnPosition();
            } else if (spawnNum == 1) {
                spawnPos = roomController.getEastSpawnPosition();
            } else if (spawnNum == 2) {
                spawnPos = roomController.getSouthSpawnPosition();
            } else {
                spawnPos = roomController.getWestSpawnPosition();
            }

            monsterSprite.setPosition(spawnPos);

            moreMonsters.add(monster);
        }

        int roomNum = controller.getRoomController().getCurrPos();
        monsterList.get((Integer) roomNum).addAll(moreMonsters);

        toggleMonsters(true);

    }

    private MonsterModel setupMonster(int num) {

        MonsterModel monster = null;
        // rat:
        if (num == 0) {
            monster = new MonsterModel(6, 0.75, 2.5, 2.5, 23);
        } else if (num == 1) { // spider
            monster = new MonsterModel(4, 1.5, 2, 2, 24);
        } else if (num == 2) { // jelly
            monster = new MonsterModel(8, 0.75, 1, 1, 22);
        }

        return monster;
    }

    public void toggleMonsters(boolean isEnabled) {

        Iterator<MonsterModel> monsterIter = getCurrentMonsters();

        while (monsterIter.hasNext()) {
            MonsterModel monsterModel = monsterIter.next();

            monsterModel.setPlayerPresent(isEnabled);

            if (!monsterModel.isDead()) {
                monsterModel.setActive(isEnabled);
            } else {
                monsterModel.setActive(false);
            }
        }
    }

    public void updateMonsters() {

        PlayerController playerController = controller.getPlayerController();

        double playerX = playerController.getPlayerSprite().getPosition().getXPos();
        double playerY = playerController.getPlayerSprite().getPosition().getYPos();

        Iterator<MonsterModel> monsterIter = getCurrentMonsters();

        while (monsterIter.hasNext()){
            MonsterModel monsterModel = monsterIter.next();
            Sprite monsterSprite = monsterModel.getMonsterSprite();

            if (monsterModel.isPlayerPresent() && !monsterModel.isDead()) {

                double monsterX = monsterSprite.getPosition().getXPos();
                double monsterY = monsterSprite.getPosition().getYPos();

                double speedX = monsterModel.getXSpeed();
                double speedY = monsterModel.getYSpeed();

                speedX -= monsterModel.updateKnockback();
                speedY -= monsterModel.updateKnockback();

                if (monsterX != playerX) {
                    if (monsterX > playerX) {
                        monsterSprite.getPosition().setXPos(monsterX - speedX);
                    } else {
                        monsterSprite.getPosition().setXPos(monsterX + speedX);
                    }
                }
                if (monsterY != playerY) {
                    if (monsterY > playerY) {
                        monsterSprite.getPosition().setYPos(monsterY - speedY);
                    } else {
                        monsterSprite.getPosition().setYPos(monsterY + speedY);
                    }
                }

                if (monsterModel instanceof BossMonsterModel) {
                    if (!createFD) {
                        updateFDTimer();
                        if (dartX != playerX) {
                            if (dartX > playerX) {
                                fd.getFreezeDart().getPosition().setXPos(dartX - 2);
                            } else {
                                fd.getFreezeDart().getPosition().setXPos(dartX + 2);
                            }
                        }
                        if (dartY != playerY) {
                            if (dartY > playerY) {
                                fd.getFreezeDart().getPosition().setYPos(dartY - 2);
                            } else {
                                fd.getFreezeDart().getPosition().setYPos(dartY + 2);
                            }
                        }
                    } else {
                        fd = new FreezeDartModel(monsterX, monsterY);
                        fds.add(fd);
                        fdTimer = fdTime;
                        dartX = fd.getFreezeDart().getPosition().getXPos();
                        dartY = fd.getFreezeDart().getPosition().getYPos();
                        createFD = false;
                    }
                }

                // ArrayList<Sprite> collisionSprites = GameLayer.checkForCollisions(monsterSprite);

                Iterator<Sprite> collisionIter = GameLayer.checkForCollisions(monsterSprite);

                PlayerModel playerModel = playerController.getPlayerModel();

                while (collisionIter.hasNext()) {
                    Sprite sprite = collisionIter.next();

                    if (sprite != null) {
                        WeaponController weaponController = controller
                                .getPlayerController().getWeaponController();
                        WeaponModel equippedWeaponModel = weaponController.getEquippedWeaponModel();

                        if (sprite.getType() == SpriteType.WEAPON
                                && !equippedWeaponModel.isWeaponHit()) {

                            double damageToMonster = -1 * equippedWeaponModel.getDamage()
                                    + (-1 * playerModel.getDamageAddition());
                            monsterModel.adjustHealth(damageToMonster);
                            Stats.incrementDamageDealt(damageToMonster * -1);

                            playerModel.subtractPowerSwing();

                            monsterModel.setCurrentKnockback(equippedWeaponModel.getKnockback());
                            equippedWeaponModel.setWeaponHit(true);
                        }
                    }
                }

                if (monsterModel.getHealth() <= 0) {
                    monsterModel.setDead(true);
                    for (FreezeDartModel fd_: fds) {
                        fd_.getFreezeDart().setSpriteVisibility(false);
                    }
                    Stats.incrementMonstersKilled();

                    // Rubies: 40% chance of ruby drop
                    // Potion: 10% chance of potion drop
                    Random rand = new Random();
                    Double randomNumber = rand.nextDouble();
                    if (randomNumber < 0.10) {
                        PotionController potionController = controller.getRoomController()
                                .getPotionController();
                        potionController.addPotion(monsterModel.getMonsterSprite().getPosition());
                    } else if (randomNumber < 0.50) {
                        RubyController rubyController = controller.getRoomController().getRubyController();
                        rubyController.addRuby(monsterModel.getMonsterSprite().getPosition());
                    }

                    if (controller.getRoomController().isChallengeActive()) {
                        ChestController chestController = controller.getRoomController()
                                 .getChestController();
                        chestController.addChest();
                    }

                    monsterSprite.setSpriteVisibility(false);
                    monsterSprite.getBoxCollider().setEnabled(false);

                    GameLayer.removeSprite(monsterSprite);

                    // check if all monsters in a room are dead and update exits accordingly
                    if (checkRoomMonsterCount()) {
                        RoomController roomController = controller.getRoomController();
                        roomController.setExitVisibility();

                        if (roomController.isChallengeRoom()) {
                            roomController.toggleChallengeRoom(true);
                        }

                        if (roomController.isChallengeActive()) {
                            roomController.setChallengeActive(false);
                        }
                    }
                }

                monsterSprite.setPosition(monsterSprite.getPosition().getXPos(),
                        monsterSprite.getPosition().getYPos());
            }
        }
    }

    public MonsterModel findMonster(Sprite targetSprite) {
        Iterator<MonsterModel> monsterIter = getCurrentMonsters();

        while (monsterIter.hasNext()){
            MonsterModel monsterModel = monsterIter.next();
            Sprite sprite = monsterModel.getMonsterSprite();

            if (targetSprite.equals(sprite)) {
                return monsterModel;
            }
        }

        return null;
    }

    public Iterator<MonsterModel> getCurrentMonsters() {

        RoomController roomController = controller.getRoomController();
        int currPos = roomController.getCurrPos();

        if (monsterList.containsKey(currPos)) {
            return monsterList.get(currPos).iterator();
        } else {
            return emptyIterator();
        }
    }

    //checks if all monsters in a room are dead, returns true if all monsters are dead
    //false otherwise
    public boolean checkRoomMonsterCount() {
        Iterator<MonsterModel> monsterIter = getCurrentMonsters();

         while (monsterIter.hasNext()){
             MonsterModel monsterModel = monsterIter.next();

            if (!monsterModel.isDead()) {
                return false;
            }
        }
        // update exits
        return true;
    }

    public HashMap<Integer, ArrayList<MonsterModel>> getMonsterList() {
        return monsterList;
    }
}
