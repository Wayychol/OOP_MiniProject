import java.util.Random;

public abstract class Encounterable {
    public String name;

    // Getter
    public String getName() {
        return name;
    }

    // toString Method
    @Override
    public abstract String toString();

    // Helper method
    public int randomNumber(int bound) {
        Random r = new Random();
        return r.nextInt(bound);
    }
}
