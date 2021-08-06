package resources;

import javafx.util.Duration;

public class AnimationInfo {

    private final int count;
    private final int columns;
    private final int spritesheetOffsetX;
    private final int spritesheetOffsetY;
    private final Duration duration;
    private int lastIndex;

    public AnimationInfo(int count, int columns,
                         int spritesheetOffsetX,
                         int spritesheetOffsetY,
                         Duration duration) {
        this.count = count;
        this.columns = columns;
        this.spritesheetOffsetX = spritesheetOffsetX;
        this.spritesheetOffsetY = spritesheetOffsetY;
        this.duration = duration;
        this.lastIndex = 0;
    }

    // Getters and Setters
    public int getLastIndex() {
        return lastIndex;
    }
    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }
    public int getCount() {
        return count;
    }
    public int getColumns() {
        return columns;
    }
    public int getSpritesheetOffsetX() {
        return spritesheetOffsetX;
    }
    public int getSpritesheetOffsetY() {
        return spritesheetOffsetY;
    }
    public Duration getDuration() {
        return duration;
    }
}
