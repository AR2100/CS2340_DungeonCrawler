package controller;

import model.GameLayer;
import resources.Position;
import model.RubyModel;
import model.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class RubyController {
    private RoomController roomController;
    private HashMap<Integer, ArrayList<RubyModel>> rubyModels;

    public RubyController(RoomController roomController) {
        this.roomController = roomController;
        this.rubyModels = new HashMap<>();
    }

    public void addRuby(Position position) {
        RubyModel newRuby = new RubyModel((int)(Math.random() * 3 + 2));
        newRuby.getRubySprite().setPosition(position);
        newRuby.setActive(true);

        ArrayList<RubyModel> currRubies;

        if (!rubyModels.containsKey(roomController.getCurrPos())) {
            currRubies = new ArrayList<>();
        } else {
            currRubies = rubyModels.get(roomController.getCurrPos());
        }

        currRubies.add(newRuby);
        rubyModels.put(roomController.getCurrPos(), currRubies);
    }

    public void toggleCurrentRubies(boolean isActive) {
        for (RubyModel currRuby : getCurrentRubies()) {
            currRuby.setActive(isActive);
        }
    }

    public ArrayList<RubyModel> getCurrentRubies() {
        if (rubyModels.containsKey(roomController.getCurrPos())) {
            return rubyModels.get(roomController.getCurrPos());
        } else {
            return new ArrayList<>();
        }
    }

    public void removeRuby(Sprite rubySprite) {
        RubyModel rubyModel = findRuby(rubySprite);

        ArrayList<RubyModel> rubies = getCurrentRubies();

        rubies.remove(rubyModel);
        GameLayer.removeSprite(rubySprite);
    }

    public RubyModel findRuby(Sprite rubySprite) {
        for (RubyModel rubyModel : getCurrentRubies()) {
            Sprite currRubySprite = rubyModel.getRubySprite();

            if (currRubySprite.equals(rubySprite)) {
                return rubyModel;
            }
        }
        return null;
    }

}
