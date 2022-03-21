public class Adventurer {
    private final String name;
    private int healingPoints = 5;
    private int healthPoints = 10;

    // Constructor
    public Adventurer(String name) {
        this.name = name;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getHealingPoints() {
        return healingPoints;
    }
    public int getHealthPoints() {
        return healthPoints;
    }
    public boolean isAlive() {
        return this.healthPoints > 0;
    }

    // Setters
    public void setHealingPoints(int healingPoints) {
        this.healingPoints = healingPoints;
    }
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    // toString Method
    @Override
    public String toString() {
        return "Adventurer " + getName()
                + ":\n\tHealing Points: " + getHealingPoints()
                + "\n\tHealth points: " + getHealthPoints();
    }
}
