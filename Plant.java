public class Plant extends Encounterable{
    private final String name;
    private final int healingPoints;
    private final boolean poisonous;

    // Constructor
    public Plant() {
        String[] types = {"Berry", "Mint Leaf", "Daffodil", "Mushroom"};
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

    // toString Method
    @Override
    public String toString() {
        return getName() + " with " + getHealingPoints() + " healing points";
    }
}
