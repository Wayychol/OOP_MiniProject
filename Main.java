public class Main {
    public static void main(String[] args) {
        Progress p = new Progress();
        p.loadProgress(2);
        Game g = new Game(p);
    }
}
