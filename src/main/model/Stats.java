package model;

public abstract class Stats {
    private static int monsterKilled = 0;
    private static double damageDealt = 0;
    private static double timeTaken = 0;

    public static int getMonsterKilled() {
        return monsterKilled;
    }

    public static double getDamageDealt() {
        return damageDealt;
    }

    public static double getTimeTaken() {
        return timeTaken;
    }

    public static void incrementMonstersKilled() {
        Stats.monsterKilled++;
    }

    public static void incrementDamageDealt(double damageDealt) {
        Stats.damageDealt += damageDealt;
    }

    public static void incrementTimeTaken() {
        Stats.timeTaken += 1;
    }

    public static void resetStats() {
        monsterKilled = 0;
        damageDealt = 0;
        timeTaken = 0;
    }
}
