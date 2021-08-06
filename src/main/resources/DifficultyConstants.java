package resources;

public class DifficultyConstants {

    private static final int EASY_STARTING_RUBY = 30;
    private static final int MEDIUM_STARTING_RUBY = 20;
    private static final int HARD_STARTING_RUBY = 10;

    public static int getStartingRuby(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) {
            return EASY_STARTING_RUBY;
        } else if (difficulty == Difficulty.MEDIUM) {
            return MEDIUM_STARTING_RUBY;
        } else if (difficulty == Difficulty.HARD) {
            return HARD_STARTING_RUBY;
        } else {
            return -1;
        }
    }
}
