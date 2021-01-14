package battleship;

import java.util.Locale;
import java.util.Scanner;

//todo redo field creation
public class Game {
	private final String[][] FIELD;
	private final Checker checker;

	public Game(String[][] field, Checker checker) {
		this.FIELD = field;
		this.checker = checker;
	}

	public void initGame() {
		createField();
		showField();

		Scanner scanner = new Scanner(System.in);
		Ship[] ships = new Ship[]{
				new Ship("Aircraft Carrier", "carrier", 5),
				new Ship("Battleship", "battleship", 4),
				new Ship("Submarine", "submarine", 3),
				new Ship("Cruiser", "cruiser", 3),
				new Ship("Destroyer", "destroyer", 2)
		};

		int i = 0;
		while (i != 5) {
			System.out.format("\nEnter the coordinates of the %s (%d cells): \n", ships[i].getName(),
					ships[i].getSize());
			String[] coordinates = scanner.nextLine().toLowerCase(Locale.ROOT).split(" ");

			try {
				putShipOnTheField(ships[i].getCode(), coordinates[0], coordinates[1]);
			} catch (IllegalStateException e) {
				System.out.println("\n" + e.getMessage());
				continue;
			}

			showField();
			i++;
		}

		System.out.println("\nThe game starts!");
		showField();
		System.out.println("\nTake a shot!");

		while (true) {
			try {
				if (makeAShot(scanner.next().toLowerCase(Locale.ROOT))) {
					showField();
					System.out.println("You hit a ship!");
				} else {
					showField();
					System.out.println("You missed!");
				}

				break;
			} catch (IllegalArgumentException e) {
				System.out.println("\n" + e.getMessage());
			}
		}
	}

	/**
	 * Method will take coordinate and determine whether user missed or hit
	 *
	 * @param coordinate string with coordinate (a1, g5, ...)
	 * @return true -> hit, false -> miss
	 * @throws IllegalArgumentException wrong coordinates
	 */
	private boolean makeAShot(String coordinate) throws IllegalArgumentException {
		if (!checker.checkCoordinates(coordinate, FIELD))
			throw new IllegalArgumentException("Error! You entered the wrong coordinates!");

		int i = Helper.letterToNumber(coordinate.charAt(0));
		int j = Integer.parseInt(coordinate.substring(1));

		if ("O".equals(FIELD[i][j])) {
			FIELD[i][j] = "X";

			return true;
		} else {
			FIELD[i][j] = "M";
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
	private void putShipOnTheField(String ship, String firstCoordinate, String secondCoordinate)
			throws IllegalStateException {
		//check whether coordinates are correct
		if (!checker.checkCoordinates(ship, firstCoordinate, secondCoordinate))
			throw new IllegalStateException("Error! Wrong coordinates");
		//check whether user can place ship in that position
		if (!checker.checkShipDisplacement(FIELD, firstCoordinate, secondCoordinate))
			throw new IllegalStateException("Error! Too close to another ship");

		//try to put ship on field
		try {
			if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
				int row = Helper.letterToNumber(firstCoordinate.charAt(0));
				int beginning = Math.min(Integer.parseInt(firstCoordinate.substring(1)),
						Integer.parseInt(secondCoordinate.substring(1)));
				int end = Math.max(Integer.parseInt(firstCoordinate.substring(1)),
						Integer.parseInt(secondCoordinate.substring(1)));

				for (int i = beginning; i <= end; i++) {
					FIELD[row][i] = "O";
				}
			} else {
				int column = Integer.parseInt(firstCoordinate.substring(1));
				int beginning = Math.min(Helper.letterToNumber(firstCoordinate.charAt(0)),
						Helper.letterToNumber(secondCoordinate.charAt(0)));
				int end = Math.max(Helper.letterToNumber(firstCoordinate.charAt(0)),
						Helper.letterToNumber(secondCoordinate.charAt(0)));

				for (int i = beginning; i <= end; i++) {
					FIELD[i][column] = "O";
				}
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(e.getMessage());
		}
	}

	/**
	 * Method will output FIELD array to the console
	 */
	private void showField() {
		for (String[] row : FIELD) {
			for (String cell : row) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Method will fill FIELD array with appropriate date
	 */
	private void createField() {
		//fill first row of FIELD with numbers
		FIELD[0][0] = " ";
		for (int i = 1; i < FIELD[0].length; i++) {
			FIELD[0][i] = String.valueOf(i);
		}
		//array contains letters which will be at the beginning of every row
		int n = 'A';

		//fill FIELD
		for (int i = 1; i < FIELD.length; i++) {
			FIELD[i][0] = String.valueOf((char) n);

			for (int j = 1; j < FIELD[i].length; j++) {
				FIELD[i][j] = "~";
			}

			n++;
		}
	}
}
