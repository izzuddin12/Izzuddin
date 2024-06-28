import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        SnakeAndLadder g1 = new SnakeAndLadder(100);
        System.out.println("1. Round Mode (Best of Three)");
        System.out.println("2. Level Mode (Single Game)");
        System.out.print("Choose 1 / 2 = ");
        int choose = input.nextInt();
        input.nextLine();
        System.out.println();
        if (choose == 1) {
            g1.play();
        } else if (choose == 2) {
            g1.playSingleLevel();
        } else {
            System.out.print("Please input 1 or 2");
        }

    }
}
