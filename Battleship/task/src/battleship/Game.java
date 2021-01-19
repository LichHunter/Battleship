package battleship;

import java.util.Locale;
import java.util.Scanner;

public class Game {
	private final Checker CHECKER;
	private final String[][] field;
	private final String[][] fogOfWar;

	public Game(String[][] field, Checker checker) {
		this.field = field;
		this.CHECKER = checker;

		this.fogOfWar = new String[field.length][field[0].length];
		for (int i = 0; i < fogOfWar.length; i++) {
			System.arraycopy(field[i], 0, fogOfWar[i], 0, fogOfWar[i].length);
		}
	}

	public void initGame() {
		createField(field);
		createField(fogOfWar);
		showField(field);

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
				putShipOnTheField(ships[i], coordinates[0], coordinates[1]);
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

		while (!allShipsSank(field)) {
			try {
				if (makeAShot(scanner.next().toLowerCase(Locale.ROOT), field, fogOfWar)) {
					System.out.println();
					showField(fogOfWar);
					System.out.println("\nYou hit a ship!");

					if (shipIsSank(ships, field)) {
						System.out.println("\nYou sank a ship!");
					}
				} else {
					System.out.println();
					showField(fogOfWar);
					System.out.println("\nYou missed!");
				}
			} catch (IllegalArgumentException e) {
				System.out.println("\n" + e.getMessage());
			}
		}

		System.out.println("\nYou sank the last ship. You won. Congratulations!");
	}

	/**
	 * Method will go through ships and check if any of them were lastly sank
	 *
	 * @param ships array that contains ships
	 * @param field string array that contains game field
	 * @return true - ship has been sunk
	 */
	private boolean shipIsSank(Ship[] ships, String[][] field) {
		for (Ship ship : ships) {
			if (!ship.isSank()) {
				String[] shipCoordinates = ship.getShipCoordinates();

				for (String shipCoordinate : shipCoordinates) {
					if ("O".equals(getCell(shipCoordinate, field))) {
						break;
					}
				}

				ship.setSank(true);
				return true;
			}
		}

		return false;
	}

	private String getCell(String shipCoordinate, String[][] field) {
		return field[Integer.parseInt(shipCoordinate.substring(0, 0))][Integer.parseInt(shipCoordinate.substring(1))];
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
	private void putShipOnTheField(Ship ship, String firstCoordinate, String secondCoordinate)
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
