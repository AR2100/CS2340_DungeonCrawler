package controller;

import model.GameLayer;
import model.PotionModel;
import model.Sprite;
import resources.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PotionController {

    private RoomController roomController;
    private HashMap<Integer, ArrayList<PotionModel>> potionModels;

    private PotionModel potion;
    private Sprite potionSprite;

    public PotionController(RoomController roomController) {

        this.roomController = roomController;
        this.potionModels = new HashMap<>();
    }

    public void addPotion(Position position) {
        Random rand = new Random();
        PotionModel newPotion;

        double randVal = rand.nextDouble();
        if (randVal < 0.5) {
            newPotion = new PotionModel("health", 1);
        } else {
            newPotion = new PotionModel("attack", 1);
        }

        randVal = rand.nextDouble();
        newPotion.setValue((randVal * 10) + 1);
        newPotion.getPotionSprite().setPosition(position);

        ArrayList<PotionModel> currPotions;

        if (!potionModels.containsKey(roomController.getCurrPos())) {
            currPotions = new ArrayList<>();
        } else {
            currPotions = potionModels.get(roomController.getCurrPos());
        }

        currPotions.add(newPotion);
        potionModels.put(roomController.getCurrPos(), currPotions);
    }

    public void toggleCurrentPotions(boolean isActive) {
        for (PotionModel currPotion : getCurrentPotions()) {
            currPotion.setActive(isActive);
        }
    }

    public ArrayList<PotionModel> getCurrentPotions() {
        if (potionModels.containsKey(roomController.getCurrPos())) {
            return potionModels.get(roomController.getCurrPos());
        } else {
            return new ArrayList<>();
        }
    }

    public void removePotion(Sprite potionSprite) {
        PotionModel potionModel = findPotion(potionSprite);

        ArrayList<PotionModel> potions = getCurrentPotions();

        potions.remove(potionModel);
        GameLayer.removeSprite(potionSprite);
    }

    public PotionModel findPotion(Sprite potionSprite) {
        for (PotionModel potionModel : getCurrentPotions()) {

            Sprite currPotionSprite = potionModel.getPotionSprite();

            if (currPotionSprite.equals(potionSprite)) {
                return potionModel;
            }
        }

        return null;
    }

    public PotionModel getPotion() {
        return potion;
    }

    public Sprite getPotionSprite() {
        return potionSprite;
    }
}
