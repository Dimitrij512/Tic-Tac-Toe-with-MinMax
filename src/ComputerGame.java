import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComputerGame {
  Scanner scan = new Scanner(System.in);
  DB db = new DB();
  Computer comp = new Computer();
  User player = new User();
  List<Point> availablePoints;
  List<PointAndScore> rootsChildrenScores;

  public void determineSymbol() {
    String symbolInput = scan.nextLine();
    player.setSymbol(symbolInput.charAt(0));
    if (player.getSymbol() == '0') {
      comp.setSymbol('x');
    } else {
      comp.setSymbol('0');
    }
  }

  public boolean isGameOver() {
    return (hasWonComp() || hasWonPlayer() || getAvailableStates().isEmpty());
  }

  public boolean hasWonComp() {
    if ((DB.brd[0][0] == DB.brd[1][1] && DB.brd[0][0] == DB.brd[2][2] && DB.brd[0][0] == comp.getSymbol())
        || (DB.brd[0][2] == DB.brd[1][1] && DB.brd[0][2] == DB.brd[2][0] && DB.brd[0][2] == comp.getSymbol())) {
      return true;
    }
    for (int i = 0; i < 3; ++i) {
      if ((DB.brd[i][0] == DB.brd[i][1] && DB.brd[i][0] == DB.brd[i][2] && DB.brd[i][0] == comp.getSymbol())
          || (DB.brd[0][i] == DB.brd[1][i] && DB.brd[0][i] == DB.brd[2][i] && DB.brd[0][i] == comp.getSymbol())) {
        return true;
      }
    }
    return false;
  }

  public boolean hasWonPlayer() {

    if ((DB.brd[0][0] == DB.brd[1][1] && DB.brd[0][0] == DB.brd[2][2] && DB.brd[0][0] == player.getSymbol())
        || (DB.brd[0][2] == DB.brd[1][1] && DB.brd[0][2] == DB.brd[2][0] && DB.brd[0][2] == player.getSymbol())) {
      return true;
    }
    for (int i = 0; i < 3; ++i) {
      if ((DB.brd[i][0] == DB.brd[i][1] && DB.brd[i][0] == DB.brd[i][2] && DB.brd[i][0] == player.getSymbol())
          || (DB.brd[0][i] == DB.brd[1][i] && DB.brd[0][i] == DB.brd[2][i] && DB.brd[0][i] == player.getSymbol())) {
        return true;
      }
    }

    return false;
  }

  public List<Point> getAvailableStates() {
    availablePoints = new ArrayList<>();
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        if (DB.brd[i][j] == 0) {
          availablePoints.add(new Point(i, j));
        }
      }
    }
    return availablePoints;
  }

  public void placeMove(Point point, char symbol) {
    DB.brd[point.x][point.y] = symbol;
  }

  public Point returnBestMove() {
    int MAX = -100000;
    int best = -1;

    for (int i = 0; i < rootsChildrenScores.size(); ++i) {
      if (MAX < rootsChildrenScores.get(i).score) {
        MAX = rootsChildrenScores.get(i).score;
        best = i;
      }
    }

    return rootsChildrenScores.get(best).point;
  }

  public int returnMin(List<Integer> list) {
    int min = Integer.MAX_VALUE;
    int index = -1;
    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i) < min) {
        min = list.get(i);
        index = i;
      }
    }
    return list.get(index);
  }

  public int returnMax(List<Integer> list) {
    int max = Integer.MIN_VALUE;
    int index = -1;
    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i) > max) {
        max = list.get(i);
        index = i;
      }
    }
    return list.get(index);
  }

  public void callMinimax(int depth, char turn) {
    rootsChildrenScores = new ArrayList<>();
    minimax(depth, turn);
  }

  public int minimax(int depth, char turn) {

    if (hasWonComp()) {
      return +1;
    }

    if (hasWonPlayer()) {
      return -1;
    }

    List<Point> pointsAvailable = getAvailableStates();

    if (pointsAvailable.isEmpty()) {
      return 0;
    }

    List<Integer> scores = new ArrayList<>();

    for (int i = 0; i < pointsAvailable.size(); ++i) {
      Point point = pointsAvailable.get(i);

      if (turn == comp.getSymbol()) {
        placeMove(point, comp.getSymbol());
        int currentScore = minimax(depth + 1, player.getSymbol());
        scores.add(currentScore);
        if (depth == 0) {
          rootsChildrenScores.add(new PointAndScore(currentScore, point));
        }
      } else if (turn == player.getSymbol()) {

        placeMove(point, player.getSymbol());
        scores.add(minimax(depth + 1, comp.getSymbol()));
      }
      DB.brd[point.x][point.y] = 0; // Reset
    }
    return turn == comp.getSymbol() ? returnMax(scores) : returnMin(scores);
  }
}
