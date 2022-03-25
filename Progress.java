import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class Progress {
    private final File file = new File("progress.txt");
    private Adventurer a = new Adventurer("Rachel");
    private Map<Integer, Animal> animalMap;
    private Map<Integer, Plant> plantMap;
    private int level;
    private int stage;

    public Progress() {}

    public Progress(Adventurer a, Map<Integer, Animal> animalMap, Map<Integer, Plant> plantMap, int level, int stage) {
        this.a = a;
        this.animalMap = animalMap;
        this.plantMap = plantMap;
        this.level = level;
        this.stage = stage;
    }

    public void saveProgress() {
        try {
            FileWriter fw = new FileWriter(this.file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String x = "-";
            String y = ",";

            bw.write(a.getName() + x);
            for (int i : new int[]{a.getHealingPoints(), a.getHealthPoints(), this.level, this.stage}) {
                bw.write(i + x);
            }

            bw.write("AM" + x);
            animalMap.forEach((k, v) -> {
                try {
                    bw.write(k + ":" + v.getName() + y + v.getInjuryPoints() + y+ v.getAttackPoints() + x);
                } catch (IOException e) {
                    System.out.println("Failed.");
                }
            });

            bw.write("PM" + x);
            plantMap.forEach((k,v) -> {
                try {
                    bw.write(k + ":" + v.getName() + y + v.getHealingPoints() + y + v.isPoisonous() + x);
                } catch (IOException e) {
                    System.out.println("Failed.");
                }
            });

            bw.write("\n");
            bw.close();
            System.out.println("Successfully wrote to the file.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (NullPointerException npe) {
            System.out.println("null pointer exception");
        } catch (Exception e) {
            System.out.println("???");
        }
    }

    public void loadProgress(int save){
        String line = "";
        try (Stream<String> lines = Files.lines(this.file.toPath())) {
            line = lines.skip(save-1).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Character> temp = new ArrayList<>();
        char c;
        int position = 0;
        line = line.replace(",", "");

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != '-') {
                temp.add(line.charAt(i));
            } else {
                System.out.println(temp);
                temp.clear();
            }
        }

    }
}
