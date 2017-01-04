import java.util.Random;

public class Main {
  static int countPlayerWin = 0;
  static int countCompWin = 0;
  static int countTie = 0;
  Random rand = new Random();

  public static void main(String args[]) {
    Main main = new Main();
    main.playGame();

  }

  public void playGame() {
    System.out.println("------------------------------------");
    System.out.println("Wellcome to Tic Tac Toe game!".toUpperCase());
    System.out.println("------------------------------------");

    boolean startGame = true;
    while (startGame) {
      ComputerGame computerGame = new ComputerGame();
      DB.brd = new char[3][3];
      System.out.println("Choose symbol for game X or 0");
      computerGame.determineSymbol();

      System.out.println("Do you want start first? Y/N ");

      String choice = computerGame.scan.nextLine().toUpperCase();

      if (choice.charAt(0) == 'N') {
        Point p = new Point(rand.nextInt(3), rand.nextInt(3));
        computerGame.placeMove(p, computerGame.comp.getSymbol());
      }
      while (!computerGame.isGameOver()) {
        computerGame.db.printMatrix();
        System.out.println("Your move: ");
        Point userMove = new Point(computerGame.scan.nextInt(), computerGame.scan.nextInt());

        computerGame.placeMove(userMove, computerGame.player.getSymbol());
        computerGame.db.printMatrix();
        if (computerGame.isGameOver()) {
          break;
        }
        computerGame.callMinimax(0, computerGame.comp.getSymbol());
        computerGame.placeMove(computerGame.returnBestMove(), computerGame.comp.getSymbol());
        System.out.println("Comp move: ");

      }

      if (computerGame.hasWonComp()) {
        countCompWin++;
        System.out.println("you lost!".toUpperCase());
        System.out.println();
        computerGame.db.printMatrix();
        showInfoAboutResults();
      } else if (computerGame.hasWonPlayer()) {
        countPlayerWin++;
        System.out.println("You win! This is not going to get printed.");
        System.out.println();
        computerGame.db.printMatrix();
        showInfoAboutResults();
      } else {
        countTie++;
        System.out.println("It's a draw!");
        System.out.println();
        computerGame.db.printMatrix();
        showInfoAboutResults();
      }
      System.out.println("If you want continue press 0");
      int parametr = computerGame.scan.nextInt();
      if (parametr != 0) {
        startGame = false;
      }
    }
  }

  public void showInfoAboutResults() {
    System.out.println("WINS of COMP : " + countCompWin);
    System.out.println("WINS of PLAYER : " + countPlayerWin);
    System.out.println("TIE : " + countTie);
  }

}
