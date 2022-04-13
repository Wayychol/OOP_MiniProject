import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class Progress {
    private static final File file = new File("progress.txt");
    private static int fileLines;
    private Adventurer a;
    private Map<Integer, Animal> animalMap;
    private Map<Integer, Plant> plantMap;
    private int level;
    private int stage;

    // Static block that sets the number of file lines before the constructor runs
    static {
        try {
            fileLines = (int) Files.lines(file.toPath()).count();
        } catch (IOException e) {
            System.out.println("Error.");
        }
    }

    // Constructors
        // Default constructor
    public Progress() {}

        // Game save constructor
    public Progress(Adventurer a, Map<Integer, Animal> animalMap, Map<Integer, Plant> plantMap, int level, int stage) {
        this.a = a;
        this.animalMap = animalMap;
        this.plantMap = plantMap;
        this.level = level;
        this.stage = stage;
    }

    //Getters
    public File getFile() {
        return file;
    }
    public Adventurer getA() {
        return a;
    }
    public Map<Integer, Animal> getAnimalMap() {
        return animalMap;
    }
    public Map<Integer, Plant> getPlantMap() {
        return plantMap;
    }
    public int getLevel() {
        return level;
    }
    public int getStage() {
        return stage;
    }
    public static int getFileLines() {
        return fileLines;
    }

    // Setters
    public void setA(Adventurer a) {
        this.a = a;
    }
    public void setAnimalMap(Map<Integer, Animal> animalMap) {
        this.animalMap = animalMap;
    }
    public void setPlantMap(Map<Integer, Plant> plantMap) {
        this.plantMap = plantMap;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setStage(int stage) {
        this.stage = stage;
    }
    public static void setFileLines(int fileLines) {
        Progress.fileLines = fileLines;
    }

    public void saveProgress() {
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String x = "-";
            String y = ",";

            // Game save format:
            // Name-HealingPoints-HealthPoints-Level-Stage-AM:-AnimalName,AnimalInjuryPoints,AnimalInjuryPoints-PM:PlantName,PlantHealingPoints-PlantPoisonous

            bw.write(a.getName() + x); //Name
            for (int i : new int[]{a.getHealingPoints(), a.getHealthPoints(), this.level, this.stage}) {
                bw.write(i + x); // HealingPoints, HealthPoints, Level, Stage
            }

            bw.write("AM" + x);
            animalMap.forEach((k, v) -> {
                try { // AnimalName, AnimalInjuryPoints, AnimalInjuryPoints
                    bw.write(k + ":" + v.getName() + y + v.getInjuryPoints() + y+ v.getAttackPoints() + x);
                } catch (IOException e) {
                    System.out.println("Failed.");
                }
            });

            bw.write("PM" + x);
            plantMap.forEach((k,v) -> {
                try { // PlantName, PlantHealingPoints, PlantPoisonous
                    bw.write(k + ":" + v.getName() + y + v.getHealingPoints() + y + (v.isPoisonous() ? "t" : "f") + x);
                } catch (IOException e) {
                    System.out.println("Failed.");
                }
            });

            bw.write("\n"); // Appends new line
            bw.close();
            System.out.println("Game save number " + getFileLines() + " successfully saved.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception ignored) {}
    }

    public void loadProgress(int save){
        String line = "";
        try (Stream<String> lines = Files.lines(file.toPath())) {
            line = lines.skip(save-1).findFirst().get();
        } catch (IOException ignored) {}
        catch (NoSuchElementException nsee) {
            System.out.println("Invalid number. There are only " + Progress.getFileLines() + " game saves.");
        }

        List<String> details = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        line = line.replace(",", "");
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != '-') {
                sb.append(line.charAt(i));
            } else {
                details.add(sb.toString());
                sb = new StringBuilder();
            }
        }

        setA(new Adventurer(details.get(0))); // Creates new Adventurer object
        this.a.setHealingPoints(Integer.parseInt(details.get(1)));
        this.a.setHealthPoints(Integer.parseInt(details.get(2))); // Sets Adventurer attributes
        setLevel(Integer.parseInt(details.get(3)));
        setStage(Integer.parseInt(details.get(4)));


        List<String> animals = details.subList(6, details.indexOf("PM"));
        List<String> plants = details.subList(details.indexOf("PM")+1, details.size());

        animalMap = saveMap("x", animals);
        plantMap = saveMap('x', plants);
    }

    // Takes in the list of Animal details, breaking the string down to return a map of Animal objects
        // Takes in an unused String c to allow for method overloading, the plant version will take a char c
    private Map<Integer, Animal> saveMap (String c, List<String> list) {
        Map<Integer, Animal> newMap = new HashMap<>();

        // Temporary lists
        List<Character> nameTemp = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<Integer> injuryPoints = new ArrayList<>();
        List<Integer> attackPoints = new ArrayList<>();

        // Adds the Animal's index to the indices list
        list.forEach(n -> {
            indices.add(Character.getNumericValue(n.charAt(0)));
            list.set(list.indexOf(n), n.substring(2));
        });


        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length(); j++) {
                nameTemp.add(list.get(i).charAt(j));

                for (String s: new Animal().getTypes()) {
                    String nameString = nameTemp.toString()
                            .replace("[", "")
                            .replace(", ", "")
                            .replace("]", ""); //Deletes the array formatting from the string

                    if (nameString.equals(s)) {
                        names.add(nameString);
                        list.set(i, list.get(i).substring(nameTemp.size())); //Deletes the animal name from the string
                        injuryPoints.add(Character.getNumericValue(list.get(i).charAt(0))); // Digit 1 left over is the injury points
                        attackPoints.add(Character.getNumericValue(list.get(i).charAt(1))); // Digit 2 is the attack points

                        nameTemp.clear(); //Clears the temp to begin loading the next Animal
                    }
                }
            }

        }

        for (int i = 0; i < indices.size(); i++) {
            newMap.put(indices.get(i), new Animal(names.get(i), injuryPoints.get(i), attackPoints.get(i)));
        }
        return newMap;
    }

        // Takes in the list of Plant details, breaking the string down to return a map of Plant objects
    private Map<Integer, Plant> saveMap (char c, List<String> list) {
        Map<Integer, Plant> newMap = new HashMap<>();

        // Temporary lists
        List<Character> nameTemp = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<Integer> healingPoints = new ArrayList<>();
        List<String> poisonous = new ArrayList<>();

        // Adds the Animal's index to the indices list
        list.forEach(n -> {
            indices.add(Character.getNumericValue(n.charAt(0)));
            list.set(list.indexOf(n), n.substring(2));
        });


        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length(); j++) {
                nameTemp.add(list.get(i).charAt(j));

                for (String s: new Plant().getTypes()) {
                    String nameString = nameTemp.toString()
                            .replace("[", "")
                            .replace(", ", "")
                            .replace("]", ""); //Deletes the array formatting from the string

                    if (nameString.equals(s)) {
                        names.add(nameString);
                        list.set(i, list.get(i).substring(nameTemp.size())); //Deletes the animal name from the string
                        healingPoints.add(Character.getNumericValue(list.get(i).charAt(0))); // Digit 1 left over is the healing points
                        poisonous.add(String.valueOf(list.get(i).charAt(1))); // Character 2 is the poisonous boolean representation

                        nameTemp.clear(); //Clears the temp to begin loading the next Animal
                    }
                }
            }

        }

        for (int i = 0; i < indices.size(); i++) {
            newMap.put(indices.get(i), new Plant(names.get(i), healingPoints.get(i), (poisonous.get(i).equals("t"))));
        }
        return newMap;
    }
}
