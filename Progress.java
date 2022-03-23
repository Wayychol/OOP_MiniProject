import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Progress {
    private File file = new File("progress.txt");
    private Adventurer a;
    private Map<Integer, Animal> animalMap;
    private Map<Integer, Plant> plantMap;
    private int Level;

    public Progress(Adventurer a, Map<Integer, Animal> animalMap, Map<Integer, Plant> plantMap, int level) {
        this.a = a;
        this.animalMap = animalMap;
        this.plantMap = plantMap;
        Level = level;
    }



}
