public class Plant extends Encounterable{
    private String name;
    private int healingPoints;
    private boolean poisonous;
    private final String[] types = {"Berry", "Mint", "Daffodil", "Mushroom"};

    // Constructor
    public Plant() {
        this.name = types[randomNumber(4)];
        this.healingPoints = randomNumber(4)+1;
        this.poisonous = randomNumber(4) == 2;
    }

    // Getters
    public String getName() {
        return this.name;
    }
    public int getHealingPoints() {
        return this.healingPoints;
    }
    public boolean isPoisonous() {
        return this.poisonous;
    }
    public String[] getTypes() {
        return this.types;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setHealingPoints(int healingPoints) {
        this.healingPoints = healingPoints;
    }
    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    // toString Method
    @Override
    public String toString() {
        return getName() + " with " + getHealingPoints() + " healing points";
    }
}
