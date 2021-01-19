package battleship;

public class Main {
    public static void main(String[] args) {
        Checker checker = new Checker();
        Game game = new Game(new String[11][11], checker);
        game.initGame();
    }
}
