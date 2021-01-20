package battleship;

import java.util.Locale;
import java.util.Scanner;

public class Game {
	private final Player one;
	private final Player two;
	private final Checker CHECKER;

	public Game(Checker checker) {
		this.CHECKER = checker;

		this.one = new Player(1, createField(new String[11][11]), createField(new String[11][11]), createShips());
		this.two = new Player(2, createField(new String[11][11]), createField(new String[11][11]), createShips());
	}

	public void startGame() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Player " + one.getId() + ", place your ships on the field\n");
		fillFieldWithShips(one.getShips(), scanner, one.getField());
		System.out.println("Press Enter and pass the move to another player\n");
		scanner.nextLine();

		System.out.println("Player " + two.getId() + ", place your ships to the game field");
		fillFieldWithShips(two.getShips(), scanner, two.getField());
		System.out.println("Press Enter and pass the move to another player\n");
		scanner.nextLine();

		int i = 0;
		while (true) {
			if (i % 2 == 0) {
				if (!playerMakeAShot(one, two, scanner)) {
					break;
				}
			} else {
				if (!playerMakeAShot(two, one, scanner)) {
					break;
				}
			}

			i++;
		}
	}

	/**
	 * Method will get coordinates from player and check whether a shot hit ship or not
	 *
	 * @return false - player which just shot hit last ship and won, true - continue game
	 */
	private boolean playerMakeAShot(Player playerThatMakesAShot, Player playerInWhichShotIsMade, Scanner scanner) {
		showField(playerInWhichShotIsMade.getFogOfWar());
		System.out.print("---\n");
		showField(playerThatMakesAShot.getField());
		System.out.println("\nPlayer " + playerThatMakesAShot.getId() + ", it's your turn: \n");
		try {
			String shootCoordinates = scanner.next().toLowerCase(Locale.ROOT);

			//if a shot has hit any ship
			if (makeAShot(shootCoordinates, playerInWhichShotIsMade.getField(), playerInWhichShotIsMade.getFogOfWar())) {
				System.out.println("\nYou hit a ship!");

				if (shipIsSank(playerInWhichShotIsMade.getShips(), playerInWhichShotIsMade.getField())) {
					System.out.println("\nYou sank a ship!");
				}

				if (allShipsSank(playerInWhichShotIsMade.getField())) {
					System.out.println("You sank the last ship. You won. Congratulations!");
					return false;
				}
			} else {
				System.out.println("\nYou missed!");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("\n" + e.getMessage());
		}

		System.out.println("Press Enter and pass the move to another player\n");
		new Scanner(System.in).nextLine();

		return true;
	}

	/**
	 * Method will interact with user and place ships on field
	 *
	 * @param ships   array that contains ship objects
	 * @param scanner object to get input from user
	 */
	private void fillFieldWithShips(Ship[] ships, Scanner scanner, String[][] field) {
		int i = 0;

		showField(field);

		//for each ship
		while (i < 5) {
			System.out.format("\nEnter the coordinates of the %s (%d cells): \n", ships[i].getName(),
					ships[i].getSize());
			//user will enter something like "a1 d1", we will split it to two coordinates - a1 beginning, d1 ending
			String[] coordinates = scanner.nextLine().toLowerCase(Locale.ROOT).split(" ");

			//try to put ship on the field
			try {
				putShipOnTheField(field, ships[i], coordinates[0], coordinates[1]);

				showField(field);
				i++;
			} catch (IllegalStateException e) {
				System.out.println("\n" + e.getMessage());
			}
		}
	}

	/**
	 * Create ships
	 *
	 * @return array that contains ships
	 */
	private Ship[] createShips() {
		return new Ship[]{
				new Ship("Aircraft Carrier", "carrier", 5),
				new Ship("Battleship", "battleship", 4),
				new Ship("Submarine", "submarine", 3),
				new Ship("Cruiser", "cruiser", 3),
				new Ship("Destroyer", "destroyer", 2)
		};
	}

	/**
	 * Method will go through ships and check if any of them were lastly sank
	 *
	 * @param ships array that contains ships
	 * @param field string array that contains game field
	 * @return true - ship has been sunk
	 */
	private boolean shipIsSank(Ship[] ships, String[][] field) {
		//for each ship
		for (Ship ship : ships) {
			//if ship is not already sank
			if (!ship.isSank()) {
				boolean flag = false;
				String[] shipCoordinates = ship.getShipCoordinates();

				//check if every cell of ship is "X"
				for (String shipCoordinate : shipCoordinates) {
					//if not -> ship is still on the water
					if ("O".equals(getCell(shipCoordinate, field))) {
						flag = true;
					}
				}

				if (!flag) {
					ship.setSank(true);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Get field cell using ship cell coordinate
	 *
	 * @param shipCellCoordinate string which contains coordinate e.g. 11(a1), 21(b1), ...
	 * @param field              string array that contains game field
	 * @return cell from field that lies by given coordinate
	 */
	private String getCell(String shipCellCoordinate, String[][] field) {
		return field[Integer.parseInt(shipCellCoordinate.substring(0, 1))][Integer.parseInt(shipCellCoordinate.substring(1))];
	}

	/**
	 * Method will search field for "O". If "O" found it means that not all ships has been sunk
	 *
	 * @param field string array that contains game field
	 * @return true - all ships sank
	 */
	private boolean allShipsSank(String[][] field) {
		for (String[] row : field) {
			for (String cell : row) {
				if ("O".equals(cell)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Method will take coordinate and determine whether user missed or hit
	 *
	 * @param coordinate string with coordinate (a1, g5, ...)
	 * @return true -> hit, false -> miss
	 * @throws IllegalArgumentException wrong coordinates
	 */
	private boolean makeAShot(String coordinate, String[][] field, String[][] fogOfWar)
			throws IllegalArgumentException {
		if (!CHECKER.checkCoordinates(coordinate, field))
			throw new IllegalArgumentException("Error! You entered the wrong coordinates!");

		int i = Helper.letterToNumber(coordinate.charAt(0));
		int j = Integer.parseInt(coordinate.substring(1));

		if ("O".equals(field[i][j]) || "X".equals(field[i][j])) {
			fogOfWar[i][j] = "X";
			field[i][j] = "X";

			return true;
		} else {
			fogOfWar[i][j] = "M";
			field[i][j] = "M";
			return false;
		}
	}

	/**
	 * Method will check whether coordinates are right and put given ship on the field
	 *
	 * @param ship             string which contains name of ship(carrier, battleship, submarine, cruiser, destroyer)
	 * @param firstCoordinate  string which contains coordinates of the ship beginning (f3, a1, d5)
	 * @param secondCoordinate string which contains coordinates of the ship ending (f3, a1, d5)
	 * @throws IllegalStateException if coordinates were not correct
	 *                               or was not able to convert letter from coordinate to number
	 */
	private void putShipOnTheField(String[][] field, Ship ship, String firstCoordinate, String secondCoordinate)
			throws IllegalStateException {
		//check whether coordinates are correct
		if (!CHECKER.checkCoordinates(ship.getCode(), firstCoordinate, secondCoordinate))
			throw new IllegalStateException("Error! Wrong coordinates");
		//check whether user can place ship in that position
		if (!CHECKER.checkShipDisplacement(field, firstCoordinate, secondCoordinate))
			throw new IllegalStateException("Error! Too close to another ship");

		//try to put ship on field
		try {
			String[] shipCoordinates;
			if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
				int row = Helper.letterToNumber(firstCoordinate.charAt(0));
				int beginning = Math.min(Integer.parseInt(firstCoordinate.substring(1)),
						Integer.parseInt(secondCoordinate.substring(1)));
				int end = Math.max(Integer.parseInt(firstCoordinate.substring(1)),
						Integer.parseInt(secondCoordinate.substring(1)));

				shipCoordinates = new String[ship.getSize()];
				int counter = 0;

				for (int i = beginning; i <= end; i++) {
					field[row][i] = "O";
					shipCoordinates[counter] = row + "" + i;
					counter++;
				}

				ship.setShipCoordinates(shipCoordinates);
			} else {
				int column = Integer.parseInt(firstCoordinate.substring(1));
				int beginning = Math.min(Helper.letterToNumber(firstCoordinate.charAt(0)),
						Helper.letterToNumber(secondCoordinate.charAt(0)));
				int end = Math.max(Helper.letterToNumber(firstCoordinate.charAt(0)),
						Helper.letterToNumber(secondCoordinate.charAt(0)));

				shipCoordinates = new String[ship.getSize()];
				int counter = 0;

				for (int i = beginning; i <= end; i++) {
					field[i][column] = "O";
					shipCoordinates[counter] = i + "" + column;
					counter++;
				}

				ship.setShipCoordinates(shipCoordinates);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e.getMessage());
		}
	}

	/**
	 * Method will output FIELD array to the console
	 */
	private void showField(String[][] field) {
		for (String[] row : field) {
			for (String cell : row) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Method will fill FIELD array with appropriate date
	 */
	private String[][] createField(String[][] field) {
		//fill first row of FIELD with numbers
		field[0][0] = " ";
		for (int i = 1; i < field[0].length; i++) {
			field[0][i] = String.valueOf(i);
		}
		//array contains letters which will be at the beginning of every row
		int n = 'A';

		//fill FIELD
		for (int i = 1; i < field.length; i++) {
			field[i][0] = String.valueOf((char) n);

			for (int j = 1; j < field[i].length; j++) {
				field[i][j] = "~";
			}

			n++;
		}

		return field;
	}
}
