package model;

import resources.Direction;

public class TriggerZone extends Sprite {

    private int destination;
    private Direction exitDirection;

    public TriggerZone(double xPos, double yPos,
                       double zoneWidth, double zoneHeight,
                       Direction exitDirection) {
        super();

        setPosition(xPos, yPos);
        setCenter(zoneWidth, zoneHeight, 0.5, 0.5, true);

        addBoxCollider(zoneWidth, zoneHeight);
        this.getBoxCollider().setTrigger(true);

        this.exitDirection = exitDirection;
        this.destination = -1;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    // Getters
    public int getDestination() {
        return this.destination;
    }
    public Direction getExitDirection() {
        return exitDirection;
    }

    @Override
    public String toString() {
        return exitDirection.toString() + " TriggerZone";
    }
}
