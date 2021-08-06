package view;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import model.Sprite;

import java.util.Random;

/** This class is used to generate the background. The background lives in the BackgroundLayer,
 *      below the GameLayer.
 *
 *  @author Rishi Chillara, Andrew Roach, Amit Kulkarni, Joe Wilmot
  * @version 1.0
 */
public class Background {

    private GridPane backgroundGridPane;
    private final int gridWidth = 16;
    private final int gridHeight = 8;

    /**
     * Constructor for the background. Instantiates floorTileImage, then
     *  populates the backgroundGridPane with background tile sprites.
     */
    public Background() {
        setupBackgroundGridPane();
    }

    // Setup background elements
    private void setupBackgroundGridPane() {

        backgroundGridPane = new GridPane();
        backgroundGridPane.setAlignment(Pos.BOTTOM_CENTER);
        backgroundGridPane.setMinSize(800, 400);

        Random rand = new Random();

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Sprite currentSprite = new Sprite();

                int num = rand.nextInt(3);

                if (num == 0) {
                    currentSprite.addImage(3);
                } else if (num == 1) {
                    currentSprite.addImage(4);
                } else if (num == 2) {
                    currentSprite.addImage(5);
                } else if (num == 3) {
                    System.out.println("oops!");
                }

                currentSprite.scaleImageView();

                backgroundGridPane.add(currentSprite.getImageView(), i, j);
            }
        }
    }

    // Getters
    public GridPane getBackgroundGridPane() {
        return backgroundGridPane;
    }
}
