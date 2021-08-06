package resources;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class GamePosition {

    private static Pane layerPane = new Pane();

    public GamePosition() {

    }

    public static Position translateRawToRelative(double rawX, double rawY) {
        double relativeX = rawX - 400;
        double relativeY =  -1 * rawY + 250;
        return new Position(relativeX, relativeY);
    }

    public static void setPosition(Position position, Node obj) {
        double rawX = position.getXPos() - position.getXOffset();
        double rawY = position.getYPos() + position.getYOffset();
        obj.relocate(rawX + 400.0, -1 * rawY + 250);
    }

    // Getters
    public static Pane getLayerPane() {
        return layerPane;
    }
}
