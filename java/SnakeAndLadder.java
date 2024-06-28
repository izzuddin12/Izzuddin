import java.util.ArrayList;
import java.util.Scanner;

public class SnakeAndLadder {
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private int boardSize;
    private int status;
    private int playerInTurn;
    private int[] gameWins = new int[2];
    private ArrayList<Integer> freezeSpots;
    private boolean[] frozenPlayers;
    private boolean[] protectedPlayers;

    public SnakeAndLadder(int boardSize) {
        this.boardSize = boardSize;
        this.players = new ArrayList<Player>();
        this.snakes = new ArrayList<Snake>();
        this.ladders = new ArrayList<Ladder>();
        this.status = 0;
        this.gameWins = new int[2];
        this.freezeSpots = new ArrayList<Integer>();
        this.frozenPlayers = new boolean[2];
        this.protectedPlayers = new boolean[2];
        initializeFreezeSpots();
    }

    public void initiateGame() {
        int[][] ladders = {
                {2, 23},
                {8, 34},
                {20, 77},
                {32, 68},
                {41, 79},
                {74, 88},
                {82, 100},
                {85, 95},
                {48, 52},
                {51, 76},
                {59, 98},
                {39, 45}
        };
        addLadders(ladders);

        int[][] snakes = {
                {29, 9},
                {38, 15},
                {47, 5},
                {53, 33},
                {62, 37},
                {86, 54},
                {92, 70},
                {96, 25},
                {30, 7},
                {97, 60},
                {55, 44},
                {70, 38},
                {58, 21},
                {90, 81},
                {73, 44},
                {43, 4},
                {26, 10},
                {91, 88}
        };
        addSnakes(snakes);
    }

    private void initializeFreezeSpots() {
        int[] spots = {5, 15, 22, 37, 49, 58, 71, 84}; // Example freeze spots
        for (int spot : spots) {
            freezeSpots.add(spot);
        }
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPlayerInTurn(int p) {
        this.playerInTurn = p;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void addSnake(Snake s) {
        this.snakes.add(s);
    }

    public void addSnakes(int[][] s) {
        if (gameWins[0] == 0 && gameWins[1] == 0) {
            for (int i = 0; i < s.length - 6 * 2; i++) {
                Snake snake = new Snake(s[i][0], s[i][1]);
                this.snakes.add(snake);
            }
        } else if (gameWins[0] == 1 && gameWins[1] == 0 || gameWins[0] == 0 && gameWins[1] == 1) {
            for (int i = 0; i < s.length - 6 * 1; i++) {
                Snake snake = new Snake(s[i][0], s[i][1]);
                this.snakes.add(snake);
            }
        } else {
            for (int i = 0; i < s.length; i++) {
                Snake snake = new Snake(s[i][0], s[i][1]);
                this.snakes.add(snake);
            }
        }
    }

    public void addLadder(Ladder l) {
        this.ladders.add(l);
    }

    public void addLadders(int[][] l) {
        if (gameWins[0] == 0 && gameWins[1] == 0) {
            for (int i = 0; i < l.length; i++) {
                Ladder ladder = new Ladder(l[i][0], l[i][1]);
                this.ladders.add(ladder);
            }
        } else if (gameWins[0] == 1 && gameWins[1] == 0 || gameWins[0] == 0 && gameWins[1] == 1) {
            for (int i = 0; i < l.length - 4*1; i++) {
                Ladder ladder = new Ladder(l[i][0], l[i][1]);
                this.ladders.add(ladder);
            }
        } else {
            for (int i = 0; i < l.length - 4*2; i++) {
                Ladder ladder = new Ladder(l[i][0], l[i][1]);
                this.ladders.add(ladder);
            }
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getStatus() {
        return status;
    }

    public int getPlayerInTurn() {
        return playerInTurn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }

    public ArrayList<Ladder> getLadders() {
        return ladders;
    }

    public int getTurn() {
        if (this.status == 0) {
            double r = Math.random();
            if (r < 0.5) return 0;
            else return 1;
        } else {
            if (playerInTurn == 0) {
                return 1;
            } else return 0;
        }
    }

    public void movePlayer(Player p, int x) {
        p.moveAround(x, this.boardSize);

        for (int i = 0; i < this.ladders.size(); i++) {
            Ladder l = this.ladders.get(i);
            if (p.getPosition() == l.getFromPosition()) {
                p.setPosition(l.getToPosition());
                System.out.println(p.getUserName() + " got ladder from " + l.getFromPosition() + " climb to " + l.getToPosition());
                Sound.playSound("ladder.wav");
            }
        }

        for (int i = 0; i < this.snakes.size(); i++) {
            Snake s = this.snakes.get(i);
            if (p.getPosition() == s.getFromPosition()) {
                int playerIndex = players.indexOf(p);
                if (!protectedPlayers[playerIndex]) {
                    p.setPosition(s.getToPosition());
                    System.out.println(p.getUserName() + " got snake from " + s.getFromPosition() + " slide down to " + s.getToPosition());
                    Sound.playSound("snake.wav");
                }else {
                    System.out.println(p.getUserName() + " is protected and avoids the snake at " + s.getFromPosition());
                    protectedPlayers[playerIndex] = false; // Remove protection after avoiding one snake
                }
            }
        }

        if (p.getPosition() == 50 || p.getPosition() == 40) {
            int playerIndex = players.indexOf(p);
            protectedPlayers[playerIndex] = true;
            System.out.println(p.getUserName() + " is now protected from snakes for one encounter.");
        }

        for (int i = 0; i < freezeSpots.size(); i++) {
            if (p.getPosition() == freezeSpots.get(i)) {
                if (p == players.get(0)) {
                    frozenPlayers[0] = true;
                } else {
                    frozenPlayers[1] = true;
                }
                System.out.println(p.getUserName() + " landed on a freeze spot and will miss the next turn!");
                Sound.playSound("freeze.wav");
                break;
            } else {
                if (p == players.get(0)) {
                    frozenPlayers[0] = false;
                } else {
                    frozenPlayers[1] = false;
                }
            }
        }

        System.out.println(p.getUserName() + " new position is " + p.getPosition());
        if (p.getPosition() == this.boardSize) {
            this.status = 2;
            System.out.println("The winner is: " + p.getUserName());
            Sound.playSound("win.wav");
        }
    }

    public void play() {
        System.out.println("Enter first player name:");
        Scanner sc = new Scanner(System.in);
        String firstPlayer = sc.nextLine();
        System.out.println("Enter second player name:");
        String secondPlayer = sc.nextLine();

        Player p1 = new Player(firstPlayer);
        Player p2 = new Player(secondPlayer);

        initiateGame();

        addPlayer(p1);
        addPlayer(p2);

        int gameCount = 0;

        while (gameWins[0] < 2 && gameWins[1] < 2) {
            gameCount++;
            System.out.println("Starting game " + gameCount);
            initiateGame();
            p1.setPosition(0);
            p2.setPosition(0);
            setStatus(1);

            do {
                int t1 = getTurn();

                setStatus(1);
                setPlayerInTurn(t1);

                Player playerInTurn = getPlayers().get(getPlayerInTurn());

                if ((playerInTurn == p1 && frozenPlayers[0]) || (playerInTurn == p2 && frozenPlayers[1])) {
                    System.out.println(playerInTurn.getUserName() + " is frozen and misses this turn!");
                    if (playerInTurn == p1) {
                        frozenPlayers[0] = false;
                    } else {
                        frozenPlayers[1] = false;
                    }
                    continue;
                }

                System.out.println("---------------------------------");
                System.out.println("Player in turn is " + playerInTurn.getUserName());

                int consecutiveSixes = 0;
                boolean turnOver = false;

                while (!turnOver) {
                    System.out.println(playerInTurn.getUserName() + " it's your turn, please press enter to roll dice");
                    String input = sc.nextLine();
                    int x = 0;
                    if (input.isEmpty()) {
                        x = playerInTurn.rollDice();
                    }

                    System.out.println("Dice number: " + x);
                    Sound.playSound("dice.wav");
                    movePlayer(playerInTurn, x);

                    if (x == 6) {
                        consecutiveSixes++;
                        if (consecutiveSixes >= 3) {
                            System.out.println(playerInTurn.getUserName() + " rolled three 6s in a row! Turn over.");
                            turnOver = true;
                        } else {
                            System.out.println(playerInTurn.getUserName() + " rolled a 6! Roll again.");
                        }
                    } else {
                        turnOver = true;
                    }

                    if (getStatus() == 2) {
                        break;
                    }
                }

            } while (getStatus() != 2);
            if (p1.getPosition() == boardSize) {
                gameWins[0]++;
                System.out.println(p1.getUserName() + " wins game " + gameCount);
                Sound.playSound("win.wav");
            } else if (p2.getPosition() == boardSize) {
                gameWins[1]++;
                System.out.println(p2.getUserName() + " wins game " + gameCount);
                Sound.playSound("win.wav");
            }

            if (gameWins[0] < 2 && gameWins[1] < 2) {
                setStatus(0);
            }
        }
        if (gameWins[0] > gameWins[1]) {
            System.out.println(p1.getUserName() + " wins the best of three series!");
            Sound.playSound("win.wav");
        } else {
            System.out.println(p2.getUserName() + " wins the best of three series!");
            Sound.playSound("win.wav");
        }
    }
    public void playSingleLevel() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter first player name:");
        String firstPlayer = sc.nextLine();
        System.out.println("Enter second player name:");
        String secondPlayer = sc.nextLine();
        System.out.println("Choose level 1");
        System.out.println("Choose level 2");
        System.out.println("Choose level 3");
        System.out.println("We choose : ");
        int level = sc.nextInt();
        sc.nextLine();
        Player p1 = new Player(firstPlayer);
        Player p2 = new Player(secondPlayer);

        addPlayer(p1);
        addPlayer(p2);
        switch (level) {
            case 1:
                int[][] level1Ladders = {
                        {2, 23},
                        {8, 34},
                        {20, 77},
                        {32, 68},
                        {41, 79},
                        {74, 88},
                        {82, 100},
                        {85, 95},
                        {48, 52},
                        {51, 76},
                        {59, 98},
                        {39, 45}
                };
                int[][] level1Snakes = {
                        {29, 9},
                        {38, 15},
                        {47, 5},
                        {53, 33},
                        {62, 37},
                        {86, 54},
                        {92, 70},
                        {96, 25},
                        {30, 7},
                        {97, 60},
                        {55, 44},
                        {70, 38},
                        {58, 21},
                        {90, 81},
                        {73, 44},
                        {43, 4},
                        {26, 10},
                        {91, 88}
                };
                addLadders(level1Ladders);
                addSnakes(level1Snakes);
                break;
            case 2:
                int[][] level2Ladders = {
                        {2, 23},
                        {8, 34},
                        {20, 77},
                        {32, 68},
                        {41, 79},
                        {74, 88},
                        {82, 100},
                        {85, 95},
                        {48, 52},
                        {51, 76},
                        {59, 98},
                        {39, 45},
                        {11, 26},
                        {13, 42}
                };
                int[][] level2Snakes = {
                        {29, 9},
                        {38, 15},
                        {47, 5},
                        {53, 33},
                        {62, 37},
                        {86, 54},
                        {92, 70},
                        {96, 25},
                        {30, 7},
                        {97, 60},
                        {55, 44},
                        {70, 38},
                        {58, 21},
                        {90, 81},
                        {73, 44},
                        {43, 4},
                        {26, 10},
                        {91, 88},
                        {14, 3},
                        {33, 19}
                };
                addLadders(level2Ladders);
                addSnakes(level2Snakes);
                break;
            case 3:
                int[][] level3Ladders = {
                        {2, 23},
                        {8, 34},
                        {20, 77},
                        {32, 68},
                        {41, 79},
                        {74, 88},
                        {82, 100},
                        {85, 95},
                        {48, 52},
                        {51, 76},
                        {59, 98},
                        {39, 45},
                        {11, 26},
                        {13, 42},
                        {17, 56},
                        {19, 58}
                };
                int[][] level3Snakes = {
                        {29, 9},
                        {38, 15},
                        {47, 5},
                        {53, 33},
                        {62, 37},
                        {86, 54},
                        {92, 70},
                        {96, 25},
                        {30, 7},
                        {97, 60},
                        {55, 44},
                        {70, 38},
                        {58, 21},
                        {90, 81},
                        {73, 44},
                        {43, 4},
                        {26, 10},
                        {91, 88},
                        {14, 3},
                        {33, 19},
                        {66, 48},
                        {72, 50}
                };
                addLadders(level3Ladders);
                addSnakes(level3Snakes);
                break;
        }
        setStatus(1);
        p1.setPosition(0);
        p2.setPosition(0);

        do {
            int t1 = getTurn();
            setStatus(1);
            setPlayerInTurn(t1);

            Player playerInTurn = getPlayers().get(getPlayerInTurn());

            if ((playerInTurn == p1 && frozenPlayers[0]) || (playerInTurn == p2 && frozenPlayers[1])) {
                System.out.println(playerInTurn.getUserName() + " is frozen and misses this turn!");
                if (playerInTurn == p1) {
                    frozenPlayers[0] = false;
                } else {
                    frozenPlayers[1] = false;
                }
                continue;
            }

            System.out.println("---------------------------------");
            System.out.println("Player in turn is " + playerInTurn.getUserName());

            int consecutiveSixes = 0;
            boolean turnOver = false;

            while (!turnOver) {
                System.out.println(playerInTurn.getUserName() + " it's your turn, please press enter to roll dice");
                String input = sc.nextLine();
                int x = 0;
                if (input.isEmpty()) {
                    x = playerInTurn.rollDice();
                }

                System.out.println("Dice number: " + x);
                Sound.playSound("dice.wav");
                movePlayer(playerInTurn, x);

                if (x == 6) {
                    consecutiveSixes++;
                    if (consecutiveSixes >= 3) {
                        System.out.println(playerInTurn.getUserName() + " rolled three 6s in a row! Turn over.");
                        turnOver = true;
                    } else {
                        System.out.println(playerInTurn.getUserName() + " rolled a 6! Roll again.");
                    }
                } else {
                    turnOver = true;
                }

                if (getStatus() == 2) {
                    break;
                }
            }

        } while (getStatus() != 2);

        if (p1.getPosition() == boardSize) {
            System.out.println("The winner is: " + p1.getUserName());
            Sound.playSound("win.wav");
        } else if (p2.getPosition() == boardSize) {
            System.out.println("The winner is: " + p2.getUserName());
            Sound.playSound("win.wav");
        }
    }
}
