package model;

import controller.Controller;
import resources.Position;
import resources.WeaponEnum;
import resources.SpriteType;
import resources.LayerEnum;

import java.io.*;

public class WeaponModel {
    private String weapon;
    private Sprite weaponSprite;

    private Position mousePos;
    private boolean useWeapon = false;

    private boolean weaponActive = false;
    private boolean weaponHit = false;
    private double weaponTimer = -1;
    private String weaponSwingDirection;

    private double weaponUseTime = 0.25;
    private double angleToMouse;
    private double weaponArc = 150;
    private double startingAngle;

    private double knockback = 5;
    private double damage;
    private double aoeDamage;
    private int imageId = -1;

    private double spriteCenterX;
    private double spriteCenterY;
    private double boxColliderWidth;
    private double boxColliderHeight;
    private double boxColliderTheta;
    private double boxColliderR;

    private WeaponEnum weaponId;

    private BufferedReader in;

    public WeaponModel() {
        this("sword");
    }

    public WeaponModel(String weapon) {
        super();
        this.weapon = weapon;
        useWeapon = false;
    }

    public WeaponModel(WeaponEnum weaponEnum) {
        useWeapon = false;
        this.weaponId = weaponEnum;

        try {
            getWeaponData(weaponEnum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getWeaponData(WeaponEnum weaponEnum) throws IOException {
        String fileLoc = "";
        String os = System.getProperty("os.name");

        if (weaponEnum == WeaponEnum.BROADSWORD) {
            if (os.contains("Windows")) {
                fileLoc = "src\\main\\data\\broadsword\\";
            } else if (os.contains("Linux") || os.contains("Mac")) {
                fileLoc = "src/main/data/broadsword/";
            }
        } else if (weaponEnum == WeaponEnum.DAGGER) {
            if (os.contains("Windows")) {
                fileLoc = "src\\main\\data\\dagger\\";
            } else if (os.contains("Linux") || os.contains("Mac")) {
                fileLoc = "src/main/data/dagger/";
            }
        } else if (weaponEnum == WeaponEnum.MACE) {
            if (os.contains("Windows")) {
                fileLoc = "src\\main\\data\\mace\\";
            } else if (os.contains("Linux") || os.contains("Mac")) {
                fileLoc = "src/main/data/mace/";
            }
        }

        System.out.println(fileLoc);
        String file = fileLoc + "weaponData.txt";
        in = new BufferedReader(new FileReader(file));

        String line = in.readLine();
        weaponUseTime = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());

        line = in.readLine();
        damage = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());

        line = in.readLine();
        knockback = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());

        line = in.readLine();
        aoeDamage = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());

        line = in.readLine();
        imageId = Integer.parseInt(line.substring(line.indexOf(':') + 1).trim());

        line = in.readLine();
        spriteCenterX = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());
        line = in.readLine();
        spriteCenterY = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());
        line = in.readLine();
        boxColliderWidth = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());
        line = in.readLine();
        boxColliderHeight = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());
        line = in.readLine();
        boxColliderTheta = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());
        line = in.readLine();
        boxColliderR = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());
        in.close();
    }

    public void createWeaponSprite() {
        weaponSprite = new Sprite();

        weaponSprite.addImage(imageId);
        weaponSprite.setCenter(weaponSprite.getScaledWidth(),
                weaponSprite.getScaledHeight(), spriteCenterX, spriteCenterY, true);
        weaponSprite.addBoxCollider(boxColliderWidth, boxColliderHeight,
                boxColliderTheta, boxColliderR);
        weaponSprite.addSpriteType(SpriteType.WEAPON);
        weaponSprite.setSpriteVisibility(false);
        weaponSprite.addToLayer(LayerEnum.GAME_LAYER);
    }

    public void updateWeaponSpritePosition(Position position) {
        weaponSprite.setPosition(position);
    }

    public void updateWeaponSpriteRotation() {
        double rot;

        if (weaponSwingDirection.equalsIgnoreCase("right")) {
            rot = startingAngle - weaponArc * (getWeaponTimeElapsed() / weaponUseTime);
        } else {
            rot = startingAngle + weaponArc * (getWeaponTimeElapsed() / weaponUseTime);
        }

        weaponSprite.setImageRotation(rot);
    }

    public void startWeaponTimer() {
        this.weaponTimer = weaponUseTime;
    }

    public void updateWeaponTimer() {
        this.weaponTimer -= Controller.getFrameMills() / 1000.0;

        if (weaponTimer < 0) {
            weaponActive = false;
            setWeaponHit(false);

            // turns off autoswing
            // setUseWeapon(false);
        }
    }

    private double getWeaponTimeElapsed() {
        if (weaponTimer >= 0) {
            return weaponUseTime - weaponTimer;
        } else {
            return weaponUseTime;
        }
    }

    public void setupWeaponTrajectory() {
        if (weaponSwingDirection.equalsIgnoreCase("right")) {
            startingAngle = angleToMouse + weaponArc / 2.0;
        } else {
            startingAngle = angleToMouse - weaponArc / 2.0;
        }

    }

    public void resetWeapon() {
        useWeapon = false;
        weaponActive = false;
    }

    public void clearWeaponInputData() {
        useWeapon = false;
    }

    public String getWeapon() {
        return weapon;
    }
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
    public boolean usingWeapon() {
        return useWeapon;
    }
    public void setUseWeapon(boolean useWeapon) {
        this.useWeapon = useWeapon;
    }
    public boolean isWeaponActive() {
        return weaponActive;
    }
    public void setWeaponActive(boolean weaponActive) {
        this.weaponActive = weaponActive;
        weaponTimer = weaponUseTime;
    }
    public boolean isWeaponHit() {
        return weaponHit;
    }
    public void setWeaponHit(boolean weaponHit) {
        this.weaponHit = weaponHit;
    }
    public Position getMousePos() {
        return mousePos;
    }
    public void setMousePos(Position mousePos) {
        this.mousePos = mousePos;
    }
    public double getAngleToMouse() {
        return angleToMouse;
    }
    public void setAngleToMouse(double angleToMouse) {
        this.angleToMouse = angleToMouse;
    }
    public Sprite getWeaponSprite() {
        return weaponSprite;
    }
    public void setWeaponSprite(Sprite weaponSprite) {
        this.weaponSprite = weaponSprite;
    }
    public String getWeaponSwingDirection() {
        return weaponSwingDirection;
    }
    public void setWeaponSwingDirection(String weaponSwingDirection) {
        this.weaponSwingDirection = weaponSwingDirection;
    }
    public double getKnockback() {
        return knockback;
    }
    public void setKnockback(double knockback) {
        this.knockback = knockback;
    }
    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
