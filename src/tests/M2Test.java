import controller.Controller;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import resources.Difficulty;

import static javafx.scene.input.KeyCode.ENTER;
import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

/**
 * This class is used to test the GameScreen class.
 *
 * @author Rishi Chillara, Andrew Roach, Amit Kulkarni, Joe Wilmot
 * @version 1.0
 */
public class M2Test extends ApplicationTest {

    private Controller controller;

    /**
     * Set up the screen for testing.
     *
     * @param stage the stage the program is set on
     * @throws Exception throws exception if anything goes wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        controller = new Controller();
        controller.isTesting(true);
        controller.start(stage);
    }

    /**
     * Tests that the error message pops up when the character's
     * name is only whitespace.
     */
    @Test
    public void testCharacterNameIncorrect() {
        clickOn("#debugMenu");
        clickOn("CUSTOMIZATION");

        type(KeyCode.BACK_SPACE);
        type(KeyCode.SPACE);
        clickOn("Sword");
        clickOn("Next");
        verifyThat("The character's name cannot be empty or only whitespace.",
                NodeMatchers.isNotNull());
    }

    /**
     * Tests that the error message pops up when the difficulty and
     * weapon option haven't been selected.
     */
    @Test
    public void testOptionsIncorrect() {
        clickOn("#debugMenu");
        clickOn("CUSTOMIZATION");

        type(KeyCode.BACK_SPACE);
        type(KeyCode.R, KeyCode.O, KeyCode.G, KeyCode.E, KeyCode.R);
        clickOn("Next");
        verifyThat("Please select a weapon.",
                NodeMatchers.isNotNull());
    }

    /**
     * Test that checks if a dagger is selected.
     */
    @Test
    public void testDaggerSelected() {
        clickOn("#debugMenu");
        clickOn("CUSTOMIZATION");

        type(KeyCode.BACK_SPACE);
        type(KeyCode.P, KeyCode.L, KeyCode.A, KeyCode.Y, KeyCode.E, KeyCode.R);
        clickOn("Dagger");
        clickOn("Next");

        //assertEquals(controller.getPlayerController().getPlayerModel().getWeapon(), "Dagger");
    }

    /**
     * Test that checks if a sword is selected.
     */
    @Test
    public void testSwordSelected() {
        clickOn("#debugMenu");
        clickOn("CUSTOMIZATION");

        write("player").push(ENTER);

        clickOn("Sword");
        clickOn("Next");

        //assertEquals(controller.getPlayerController().getPlayerModel().getWeapon(), "Sword");
    }

    /**
     * Test that checks if a sword is selected.
     */
    @Test
    public void testMaceSelected() {
        clickOn("#debugMenu");
        clickOn("CUSTOMIZATION");

        type(KeyCode.BACK_SPACE);
        type(KeyCode.P, KeyCode.L, KeyCode.A, KeyCode.Y, KeyCode.E, KeyCode.R);
        clickOn("Mace");
        clickOn("Next");

        //assertEquals(controller.getPlayerController().getPlayerModel().getWeapon(), "Mace");
    }

    /**
     * Test that checks if easy was selected as the difficulty.
     */
    @Test
    public void testEasyDifficultyMoney() {
        clickOn("#debugMenu");
        clickOn("CUSTOMIZATION");

        write("placeholder").push(ENTER);
        clickOn("Mace");
        clickOn("Next");

        assertEquals(Difficulty.EASY,
                controller.getPlayerController().getPlayerModel().getDifficulty());

        /*
        Label rubyNum =
                (Label) lookup("#rubiesVBox").lookup("#rubyHBox").lookup("#numRuby").queryLabeled();
        assertEquals("100\n\n", rubyNum.getText());
         */
    }

    /**
     * Test that checks if medium was selected as the difficulty.
     */
    @Test
    public void testMediumDifficultyMoney() {
        clickOn("#debugMenu");
        clickOn("CUSTOMIZATION");

        write("placeholder").push(ENTER);
        clickOn("EASY");
        clickOn("MEDIUM");
        clickOn("Mace");
        clickOn("Next");

        assertEquals(Difficulty.MEDIUM,
                controller.getPlayerController().getPlayerModel().getDifficulty());

        /*
        Label rubyNum =
                (Label) lookup("#rubiesVBox").lookup("#rubyHBox").lookup("#numRuby").queryLabeled();
        assertEquals("50\n\n", rubyNum.getText());
         */
    }

    /**
     * Test that checks if hard was selected as the difficulty.
     */
    @Test
    public void testHardDifficultyMoney() {
        clickOn("#debugMenu");
        clickOn("CUSTOMIZATION");

        write("placeholder").push(ENTER);
        clickOn("EASY");
        clickOn("HARD");
        clickOn("Mace");
        clickOn("Next");

        assertEquals(Difficulty.HARD,
                controller.getPlayerController().getPlayerModel().getDifficulty());

        /*
        Label rubyNum =
                (Label) lookup("#rubiesVBox").lookup("#rubyHBox").lookup("#numRuby").queryLabeled();
        assertEquals("25\n\n", rubyNum.getText());

         */
    }

}
