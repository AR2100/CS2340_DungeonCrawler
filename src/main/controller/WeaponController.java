package controller;

import model.PlayerModel;
import model.Sprite;
import model.WeaponModel;
import resources.WeaponEnum;

import java.util.HashMap;

public class WeaponController {

    private Controller controller;
    private HashMap<WeaponEnum, WeaponModel> weapons;

    public WeaponController(Controller controller) {
        this.controller = controller;
        this.weapons = new HashMap<>();
    }

    public void setupWeaponSprite() {

        PlayerModel playerModel = controller.getPlayerController().getPlayerModel();
        PlayerController playerController = controller.getPlayerController();
        WeaponEnum currentWeapon = playerModel.getEquippedWeapon();

        WeaponModel model = new WeaponModel(currentWeapon);
        weapons.put(currentWeapon, model);
        model.createWeaponSprite();

        Sprite currentWeaponSprite = model.getWeaponSprite();
        Sprite playerSprite = playerController.getPlayerSprite();
        currentWeaponSprite.setPosition(playerSprite.getPosition());
        currentWeaponSprite.setSpriteVisibility(false);
    }

    public void updateWeapon() {

        PlayerController playerController = controller.getPlayerController();
        Sprite playerSprite = playerController.getPlayerSprite();
        WeaponEnum weapon = playerController.getPlayerModel().getEquippedWeapon();
        WeaponModel equippedWeaponModel = weapons.get(weapon);

        if (equippedWeaponModel.usingWeapon() && !equippedWeaponModel.isWeaponActive()) {
            setWeaponRotation();

            setWeaponEnabled(true);
            equippedWeaponModel.startWeaponTimer();
        }

        if (equippedWeaponModel.isWeaponActive()) {
            equippedWeaponModel.updateWeaponTimer();
            equippedWeaponModel.updateWeaponSpriteRotation();
        } else {
            setWeaponEnabled(false);
        }

        equippedWeaponModel.updateWeaponSpritePosition(playerSprite.getPosition());
    }

    private void setWeaponRotation() {

        Sprite playerSprite = controller.getPlayerController().getPlayerSprite();
        WeaponModel equippedWeaponModel = getEquippedWeaponModel();

        double mouseX = equippedWeaponModel.getMousePos().getXPos();
        double mouseY = equippedWeaponModel.getMousePos().getYPos();
        double playerX = playerSprite.getPosition().getXPos();
        double playerY = playerSprite.getPosition().getYPos();

        double rad;
        double angle = 0;

        double diffX = mouseX - playerX;
        double diffY = mouseY - playerY;
        double hyp = Math.hypot(Math.abs(diffX), Math.abs(diffY));

        rad = Math.acos(diffX / hyp);

        // if mouse is above player
        if (mouseY >= playerY) {
            angle = Math.toDegrees(rad);
        } else {
            angle = 360.0 - Math.toDegrees(rad);
        }

        // determine which direction to swing sword
        if (mouseX >= playerX) {
            equippedWeaponModel.setWeaponSwingDirection("right");
        } else {
            equippedWeaponModel.setWeaponSwingDirection("left");
        }

        equippedWeaponModel.setAngleToMouse(angle - 45);
        equippedWeaponModel.setupWeaponTrajectory();
    }

    public void setWeaponEnabled(boolean enabledStatus) {
        WeaponModel equippedWeaponModel = getEquippedWeaponModel();
        Sprite weaponSprite = equippedWeaponModel.getWeaponSprite();

        weaponSprite.setSpriteVisibility(enabledStatus);
        weaponSprite.getBoxCollider().setEnabled(enabledStatus);

        equippedWeaponModel.setWeaponActive(enabledStatus);
    }

    public Sprite getWeapon(WeaponEnum weapon) {
        return weapons.get(weapon).getWeaponSprite();
    }

    public WeaponModel getEquippedWeaponModel() {
        PlayerController playerController = controller.getPlayerController();
        WeaponEnum weapon = playerController.getPlayerModel().getEquippedWeapon();

        return weapons.get(weapon);
    }

    public void resetWeapons() {
        for (WeaponModel weapon : weapons.values()) {
            weapon.resetWeapon();
        }
    }
}
