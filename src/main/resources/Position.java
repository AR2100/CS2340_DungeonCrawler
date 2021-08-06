package resources;

public class Position {
    private double xPos;
    private double yPos;
    private double xOffset;
    private double yOffset;

    public Position() {
        this.xPos = 0;
        this.yPos = 0;
        this.xOffset = 0;
        this.yOffset = 0;
    }

    public Position(double xPos, double yPos, double xOffset, double yOffset) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public Position(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xOffset = 0;
        this.yOffset = 0;
    }

    public void setCenter(double objWidth, double objHeight, double x, double y) {
        this.xOffset = objWidth * x;
        this.yOffset = objHeight * y;
    }

    // Getters and Setters
    public double getXPos() {
        return xPos;
    }
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }
    public double getYPos() {
        return yPos;
    }
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }
    public double getXOffset() {
        return xOffset;
    }
    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
    }
    public double getYOffset() {
        return yOffset;
    }
    public void setYOffset(double yOffset) {
        this.yOffset = yOffset;
    }
}
