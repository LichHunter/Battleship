package battleship;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Game {
	private final Checker CHECKER;
	private String[][] field;
	private String[][] fogOfWar;

	public Game(String[][] field, Checker checker) {
		this.field = field;
		this.CHECKER = checker;

		this.fogOfWar = new String[field.length][field[0].length];
		for (int i = 0; i < fogOfWar.length; i++) {
			System.arraycopy(field[i], 0, fogOfWar[i], 0, fogOfWar[i].length);
		}
	}

	public void initGame() {
		field = createField(field);
		fogOfWar = createField(fogOfWar);
		showField(field);

		Scanner scanner = new Scanner(System.in);
		//todo make ships array an Enum
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

			showField(field);
			i++;
		}

		System.out.println("\nThe game starts!");
		showField(fogOfWar);
		System.out.println("\nTake a shot!\n");

		while (true) {
			try {
				if (makeAShot(scanner.next().toLowerCase(Locale.ROOT), field, fogOfWar)) {
					System.out.println();
					showField(fogOfWar);
					System.out.println("\nYou hit a ship!");
				} else {
					System.out.println();
					showField(fogOfWar);
					System.out.println("\nYou missed!");
				}
				showField(field);

				break;
			} catch (IllegalArgumentException e) {
				System.out.println("\n" + e.getMessage());
			}
		}
	}

	/**
	 * Method will take coordinate and determine whether user missed or hit
	 * @param coordinate string with coordinate (a1, g5, ...)
	 * @return true -> hit, false -> miss
	 * @throws IllegalArgumentException wrong coordinates
	 */
	private boolean makeAShot(String coordinate, String[][] field, String[][] fogOfWar)
			throws IllegalArgumentException{
		if (!CHECKER.checkCoordinates(coordinate, field))
			throw new IllegalArgumentException("Error! You entered the wrong coordinates!");

		int i = Helper.letterToNumber(coordinate.charAt(0));
		int j = Integer.parseInt(coordinate.substring(1));

		if ("O".equals(field[i][j])) {
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
	private void putShipOnTheField(String ship, String firstCoordinate, String secondCoordinate)
			throws IllegalStateException {
		//check whether coordinates are correct
		if (!CHECKER.checkCoordinates(ship, firstCoordinate, secondCoordinate))
			throw new IllegalStateException("Error! Wrong coordinates");
		//check whether user can place ship in that position
		if (!CHECKER.checkShipDisplacement(field, firstCoordinate, secondCoordinate))
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
					field[row][i] = "O";
				}
			} else {
				int column = Integer.parseInt(firstCoordinate.substring(1));
				int beginning = Math.min(Helper.letterToNumber(firstCoordinate.charAt(0)),
						Helper.letterToNumber(secondCoordinate.charAt(0)));
				int end = Math.max(Helper.letterToNumber(firstCoordinate.charAt(0)),
						Helper.letterToNumber(secondCoordinate.charAt(0)));

				for (int i = beginning; i <= end; i++) {
					field[i][column] = "O";
				}
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
