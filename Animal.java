public class Animal extends Encounterable{
    private final String name;
    private final int injuryPoints;
    private final int attackPoints;

    // Constructor
    public Animal() {
        String[] types = {"Wolf", "Bear", "Deer"};
        this.name = types[randomNumber(3)];
        this.injuryPoints = randomNumber(11)+1;
        this.attackPoints = randomNumber(6)+1;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getInjuryPoints() {
        return injuryPoints;
    }
    public int getAttackPoints() {
        return attackPoints;
    }

    // toString Method
    @Override
    public String toString() {
        return getName() + ":\n\tInjury Points: " + getInjuryPoints() + "\n\tAttack Points: " + getAttackPoints();
    }
}
