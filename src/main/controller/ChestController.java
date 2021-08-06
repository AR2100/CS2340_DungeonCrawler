package controller;

import model.ChestModel;
import model.GameLayer;
import model.PlayerModel;
import model.Sprite;
import resources.ArmorEnum;
import resources.WeaponEnum;
import java.util.ArrayList;
import java.util.HashMap;

public class ChestController {
    private PlayerModel playerModel;

    private RoomController roomController;
    private HashMap<Integer, ArrayList<ChestModel>> chestModels;

    private ChestModel newChest;
    private WeaponEnum newWeapon;
    private ArmorEnum newArmor;


    public ChestController(RoomController roomController) {
        this.roomController = roomController;
        this.playerModel = roomController.getPlayerController().getPlayerModel();

        this.chestModels = new HashMap<>();

        //ChestModel newChest = new ChestModel();
        //newChest.getChestSprite().setPosition(0, 100);

        //chestModel.setActive(true);
    }

    /**
     * Generates a chest with a random weapon that player doesn't have
     */
    public void addChest() {
        // newWeapon = getWeaponToAdd();
        newArmor = getArmorToAdd();
        // double rand = Math.random();
        // if (newWeapon != null && rand <= 0.50) {
        //     newChest = new ChestModel("weapon");
        //     newChest.getChestSprite().setPosition(-30, 100);
        //     newChest.setActive(true);
        //
        //     ArrayList<ChestModel> currChests;
        //
        //     if (!chestModels.containsKey(roomController.getCurrPos())) {
        //         currChests = new ArrayList<>();
        //     } else {
        //         currChests = chestModels.get(roomController.getCurrPos());
        //     }
        //
        //     currChests.add(newChest);
        //     chestModels.put(roomController.getCurrPos(), currChests);
        // }
        if (newArmor != null) {
            newChest = new ChestModel("armor");
            newChest.getChestSprite().setPosition(0, 0);
            newChest.setActive(true);

            ArrayList<ChestModel> currChests;

            if (!chestModels.containsKey(roomController.getCurrPos())) {
                currChests = new ArrayList<>();
            } else {
                currChests = chestModels.get(roomController.getCurrPos());
            }

            currChests.add(newChest);
            chestModels.put(roomController.getCurrPos(), currChests);
        }
    }

    public void toggleCurrentChests(boolean isActive) {
        for (ChestModel currChest : getCurrentChests()) {
            currChest.setActive(isActive);
        }
    }

    public ArrayList<ChestModel> getCurrentChests() {
        if (chestModels.containsKey(roomController.getCurrPos())) {
            return chestModels.get(roomController.getCurrPos());
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * Add weapon to inventory when player touches chest and remove chest
     * @return the name of the weapon that was added
     * @param chestSprite the sprite to remove
     */
    public String removeChest(Sprite chestSprite) {
        ChestModel chestModel = findChest(chestSprite);

        ArrayList<ChestModel> chests = getCurrentChests();

        chests.remove(chestModel);
        GameLayer.removeSprite(chestSprite);

/*        if (chestModel.getTreasure().equals("weapon")) {
            playerModel.addWeapon(newWeapon);
            newChest.setActive(false);
            return getWeaponName(newWeapon);
        } */
        if (chestModel.getTreasure().equals("armor")) {
            playerModel.setPlayerArmor(newArmor);
            newChest.setActive(false);
            return getArmorName(newArmor);
        }

        return "";

    }

    public ChestModel findChest(Sprite chestSprite) {
        for (ChestModel chestModel : getCurrentChests()) {

            Sprite currChestSprite = chestModel.getChestSprite();

            if (currChestSprite.equals(chestSprite)) {
                return chestModel;
            }
        }

        return null;
    }

/*    *//**
     * Helper method that returns weapon inside the chest
     * @return retuns weaponenum
     *//*
    private WeaponEnum getWeaponToAdd() {
        ArrayList<WeaponEnum> availableWeapons = new ArrayList<>();
        availableWeapons.add(WeaponEnum.MACE);
        availableWeapons.add(WeaponEnum.BROADSWORD);
        availableWeapons.add(WeaponEnum.DAGGER);

        ArrayList<WeaponEnum> playerWeapons = playerModel.getPlayerWeapons();
        System.out.println(playerWeapons);

        for (WeaponEnum weapons:playerWeapons) {
            if (availableWeapons.contains(weapons)) {
                availableWeapons.remove(weapons);
            }
        }

        if (availableWeapons.size() == 0) {
            return null;
        }
        int randomItem = (int) (Math.random() * availableWeapons.size());
        WeaponEnum weaponToAdd = availableWeapons.get(randomItem);
        if (weaponToAdd.equals(WeaponEnum.MACE)) {
            return WeaponEnum.MACE;
        } else if (weaponToAdd.equals(WeaponEnum.BROADSWORD)) {
            return WeaponEnum.BROADSWORD;
        } else {
            return WeaponEnum.DAGGER;
        }
    }

    *//**
     * Returns weapon name based on the weaponEnum
     * @param weaponEnum weaponenum
     * @return returns weapon name
     *//*
    private String getWeaponName(WeaponEnum weaponEnum) {
        if (weaponEnum.equals(null)) {
            return "";
        } else if (weaponEnum.equals(WeaponEnum.MACE)) {
            return "Mace";
        } else if (weaponEnum.equals(WeaponEnum.BROADSWORD)) {
            return "Broadsword";
        } else {
            return "Dagger";
        }
    }*/

    private ArmorEnum getArmorToAdd() {
        ArmorEnum currentArmor = playerModel.getPlayerArmor();
        if (currentArmor == ArmorEnum.LEATHER) {
            return ArmorEnum.CHAINMAIL;
        } else if (currentArmor == ArmorEnum.CHAINMAIL) {
            return ArmorEnum.RUBY;
        } else {
            // throw new RuntimeException("Already Have Ruby Armor");
            return null;
        }
    }

    private String getArmorName(ArmorEnum armorEnum) {
        if (armorEnum.equals(null)) {
            return "";
        } else if (armorEnum.equals(ArmorEnum.LEATHER)) {
            return "leather armor";
        } else if (armorEnum.equals(ArmorEnum.CHAINMAIL)) {
            return "chainmail armor";
        } else {
            return "ruby armor";
        }
    }

    public HashMap<Integer, ArrayList<ChestModel>> getChestModels() {
        return chestModels;
    }


}
