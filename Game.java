import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {
    private final Adventurer adventurer;
    private int level = 0;
    private final Map<Integer, Animal> animalEncounters = new HashMap<>();
    private final Map<Integer, Plant> plantEncounters = new HashMap<>();
    private final int[] mapSizes = {3, 5, 7, 10};
    private int mapLength = this.mapSizes[this.level];

    // Constructor
    public Game() {
        System.out.print("Enter your adventurer's name: ");
        this.adventurer = new Adventurer(inputString());
        nextLevel();
        intro();

        while (this.adventurer.isAlive() && this.level<5) {
            System.out.println("Starting...");
            wait(1);
            gameplay();
            System.out.println("Loading next level...");
            wait(2);
            nextLevel();
        }
    }

    public Game(Progress save) {
        System.out.println("Loading save...");
        wait(2);
        System.out.println(save.getA().toString());
        this.adventurer = save.getA();
        wait(1);
        nextLevel();

        while (this.adventurer.isAlive() && this.level<5) {
            System.out.println("Starting...");
            wait(1);
            setLevel(save.getLevel()); // Infinite loop, fix it
            System.out.println("Level: " + save.getLevel() + ", Stage: " + save.getStage());
            wait(1);
            gameplay(save);
            System.out.println("Loading next level...");
            wait(2);
            nextLevel();
        }
    }

    // Getters
    public Adventurer getAdventurer() {
        return adventurer;
    }
    public int getLevel() {
        return level;
    }
    public Map<Integer, Animal> getAnimalEncounters() {
        return animalEncounters;
    }
    public Map<Integer, Plant> getPlantEncounters() {
        return plantEncounters;
    }
    public int[] getMapSizes() {
        return mapSizes;
    }
    public int getMapLength() {
        return this.mapLength;
    }

    // Setters
    public void setMap() {
        Random r = new Random();
        this.animalEncounters.put(0, new Animal());

        for (int i = 0; i < getMapLength()+1; i++) {
            if (r.nextBoolean()) {
                this.animalEncounters.put(i, new Animal());
            } else if (r.nextInt(3)+ 1 == 3) {
                this.plantEncounters.put(i, new Plant());
            }
        }
    }
    public void setLevel(int level) {
        this.level = level;
    }

    // Helper Methods
    private int rollDice() {
        Random r = new Random();
        return (r.nextInt(6)+1) + (r.nextInt(6)+1);
    }
    private String inputString() {
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
    private void wait(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException ignored) {}
    }

    // Gameplay methods
    private void intro() {
        String[] introduction = {
                "Welcome to the forest adventure game",
                "In this game you have to navigate five levels of forest paths, healing animals and finding plants",
                "You have health points and healing points, you start the game at 10 and 5",
                "To win the game you must end with 15 healing points or more",
                "If your health points go below zero you die and the game ends",
                "Every level has an increasing number of stages, each with a 1/2 chance to find an animal and a 1/4 chance to find a plant",
                "Each animal has injury and attack points",
                "If you choose to heal the animal you will roll two dice",
                "If your combined score is higher than their injury points, you will heal the animal and gain 2 healing points",
                "If you fail, you will lose 1 healing point and the animal will attack you, reducing your health points by the number of attack points they have so be wise!",
                "If you choose not to heal an animal there is a 1/4 chance the angry animal will attack you",
                "You can encounter a plant that you can eat",
                "Plants have healing points, if the plant is safe, you will gain that many health points",
                "There is a 1/4 chance a plant is poisonous, be careful!"
        };

        Arrays.asList(introduction).forEach(n -> {
            System.out.println(n);
            wait(1);
        });

    }

    private void nextLevel() {
        this.level++;
        if (getLevel() > 4) {
            if (adventurer.getHealingPoints() >= 15) {
                System.out.println("Game over, you won! Here's how you ended the game:\n"
                        + getAdventurer().toString());
            } else {
                System.out.println("Game over, you lost. Here's how you ended the game:\n"
                        + getAdventurer().toString());
            }
            System.exit(0);
        } else {
            getAnimalEncounters().clear();
            mapLength = getMapSizes()[getLevel() - 1];
            setMap();
        }
    }

    private void heal(int animal) {
        int rolled = rollDice();
        Animal currentAnimal = getAnimalEncounters().get(animal);
        wait(1);
        if (rolled >= currentAnimal.getInjuryPoints()) {
            System.out.println("You rolled " + rolled + ". The animal is healed!\n" +
                    "Your healing points go up by 2");
            getAdventurer().setHealingPoints(getAdventurer().getHealingPoints() + 2);
        } else {
            System.out.println("You rolled " + rolled + ". The animal could not be healed\n" +
                    "Your healing points go down by 1");
            getAdventurer().setHealingPoints(getAdventurer().getHealingPoints() - 1);
            System.out.println("Oh no! The animal attacked you!" +
                    "\nYour health points go down by " + currentAnimal.getAttackPoints());
            attack(animal);
        }
        wait(1);
    }

    private void attack(int animal) {
        getAdventurer().setHealthPoints(
                getAdventurer().getHealthPoints() - getAnimalEncounters().get(animal).getAttackPoints()
        );
    }

    private void eat(int plant) {
        Plant currentPlant = getPlantEncounters().get(plant);
        if (currentPlant.isPoisonous()) {
            System.out.println("Oh no! The " + currentPlant.getName() + " you ate is poisonous." +
                    "\nYour health points go down by " + currentPlant.getHealingPoints());
            getAdventurer().setHealthPoints(
                    getAdventurer().getHealthPoints() - currentPlant.getHealingPoints()
            );
        } else {
            System.out.println("The " + currentPlant.getName() + " you ate healed you by " +
                    currentPlant.getHealingPoints() + " points");
            getAdventurer().setHealthPoints(
                    getAdventurer().getHealthPoints() + currentPlant.getHealingPoints());
        }
    }

    private void checkDead() {
        if (!adventurer.isAlive()) {
            System.out.println("Oh no! You died and won't finish the game\n GAME OVER");
            System.exit(0);
        }
    }

    public void gameplay() {
        System.out.println("Level " + getLevel() + ", Map Length: " + getMapLength());
        wait(1);
        for (int i = 0; i < getMapLength(); i++) {
            System.out.println("Stage " + (i+1) + "\n" + getAdventurer().toString());
            wait(1);
            if (getAnimalEncounters().containsKey(i)) {
                System.out.println("\nYou have met " + getAnimalEncounters().get(i).toString());

                System.out.print("Would you like to heal the animal? [Y/N] ");
                String choice = inputString();
                if (choice.equals("Y")) {
                    heal(i);
                } else if (choice.equals("S")) {
                    Progress p = new Progress(getAdventurer(), getAnimalEncounters(), getPlantEncounters(), getLevel(), i);
                    p.saveProgress();
                } else {
                    System.out.println("Your healing points stay the same");
                    if (rollDice() % 4 == 0) {
                        wait(1);
                        System.out.println("The animal is angry you didn't heal it and has attacked you!");
                        attack(i);
                        System.out.println("Your health points have gone down to " + getAdventurer().getHealthPoints());
                    }
                }

            } else if (getPlantEncounters().containsKey(i)) {
                System.out.println("\nYou have found a " + getPlantEncounters().get(i).toString());

                System.out.print("Would you like to eat the plant? [Y/N] ");
                String choice = inputString();
                wait(1);
                if (choice.equals("Y")) {
                    eat(i);
                } else if (choice.equals("S")) {
                    Progress p = new Progress(getAdventurer(), getAnimalEncounters(), getPlantEncounters(), getLevel(), i);
                    p.saveProgress();
                }
            } else {
                System.out.println("There were no animals to heal, you move freely");
            }
            checkDead();
            wait(1);
        }
        System.out.println("End of level " + getLevel());
    }

    public void gameplay(Progress save) {
        System.out.println("Level " + getLevel() + ", Map Length: " + getMapLength());
        wait(1);

        if (save.getLevel() == getLevel()) {
            for (int i = 0; i < save.getStage(); i++) {
                System.out.println("Stage " + (i+1) + "\n" + getAdventurer().toString());
                wait(1);
                if (getAnimalEncounters().containsKey(i)) {
                    System.out.println("\nYou have met " + getAnimalEncounters().get(i).toString());

                    System.out.print("Would you like to heal the animal? [Y/N] ");
                    String choice = inputString();
                    if (choice.equals("Y")) {
                        heal(i);
                    } else if (choice.equals("S")) {
                        Progress p = new Progress(getAdventurer(), getAnimalEncounters(), getPlantEncounters(), getLevel(), i);
                        p.saveProgress();
                    } else {
                        System.out.println("Your healing points stay the same");
                        if (rollDice() % 4 == 0) {
                            wait(1);
                            System.out.println("The animal is angry you didn't heal it and has attacked you!");
                            attack(i);
                            System.out.println("Your health points have gone down to " + getAdventurer().getHealthPoints());
                        }
                    }

                } else if (getPlantEncounters().containsKey(i)) {
                    System.out.println("\nYou have found a " + getPlantEncounters().get(i).toString());

                    System.out.print("Would you like to eat the plant? [Y/N] ");
                    String choice = inputString();
                    wait(1);
                    if (choice.equals("Y")) {
                        eat(i);
                    } else if (choice.equals("S")) {
                        Progress p = new Progress(getAdventurer(), getAnimalEncounters(), getPlantEncounters(), getLevel(), i);
                        p.saveProgress();
                    }
                } else {
                    System.out.println("There were no animals to heal, you move freely");
                }
                checkDead();
                wait(1);
            }
            System.out.println("End of level " + getLevel());
        }
        else {
            for (int i = 0; i < getMapLength(); i++) {
                System.out.println("Stage " + (i + 1) + "\n" + getAdventurer().toString());
                wait(1);
                if (getAnimalEncounters().containsKey(i)) {
                    System.out.println("\nYou have met " + getAnimalEncounters().get(i).toString());

                    System.out.print("Would you like to heal the animal? [Y/N] ");
                    String choice = inputString();
                    if (choice.equals("Y")) {
                        heal(i);
                    } else if (choice.equals("S")) {
                        Progress p = new Progress(getAdventurer(), getAnimalEncounters(), getPlantEncounters(), getLevel(), i);
                        p.saveProgress();
                    } else {
                        System.out.println("Your healing points stay the same");
                        if (rollDice() % 4 == 0) {
                            wait(1);
                            System.out.println("The animal is angry you didn't heal it and has attacked you!");
                            attack(i);
                            System.out.println("Your health points have gone down to " + getAdventurer().getHealthPoints());
                        }
                    }

                } else if (getPlantEncounters().containsKey(i)) {
                    System.out.println("\nYou have found a " + getPlantEncounters().get(i).toString());

                    System.out.print("Would you like to eat the plant? [Y/N] ");
                    String choice = inputString();
                    wait(1);
                    if (choice.equals("Y")) {
                        eat(i);
                    } else if (choice.equals("S")) {
                        Progress p = new Progress(getAdventurer(), getAnimalEncounters(), getPlantEncounters(), getLevel(), i);
                        p.saveProgress();
                    }
                } else {
                    System.out.println("There were no animals to heal, you move freely");
                }
                checkDead();
                wait(1);
            }
            System.out.println("End of level " + getLevel());
        }
    }

}
