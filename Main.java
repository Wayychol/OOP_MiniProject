import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Forest Adventure Game!");
        System.out.print("Would you like to start a new game or load a save? [N/S]: ");
        Scanner s = new Scanner(System.in);
        String choice = s.nextLine();
        while (!(choice.equals("N") || choice.equals("S"))) {
            System.out.print("Enter either N or S for a new game or to load a save: ");
            choice = s.nextLine();
        }

        if (choice.equals("N")) {
            Game g = new Game();
        } else {
            Progress p = new Progress();
            p.loadProgress(2);
            Game g = new Game(p);
        }
    }
}
