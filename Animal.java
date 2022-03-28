public class Animal extends Encounterable{
    private int injuryPoints;
    private int attackPoints;
    private final String[] types = {"Wolf", "Bear", "Deer"};

    // Constructor
    public Animal() {
        this.name = types[randomNumber(3)];
        this.injuryPoints = randomNumber(11)+1;
        this.attackPoints = randomNumber(6)+1;
    }

    public Animal(String name, int injuryPoints, int attackPoints) {
        this.name = name;
        this.injuryPoints = injuryPoints;
        this.attackPoints = attackPoints;
    }

    // Getters
    @Override
    public String getName() {
        return name;
    }
    public int getInjuryPoints() {
        return injuryPoints;
    }
    public int getAttackPoints() {
        return attackPoints;
    }
    public String[] getTypes() {
        return types;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setInjuryPoints(int injuryPoints) {
        this.injuryPoints = injuryPoints;
    }
    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    // toString Method
    @Override
    public String toString() {
        return getName()
                + ":\n\tInjury Points: " + getInjuryPoints()
                + "\n\tAttack Points: " + getAttackPoints();
    }
}
