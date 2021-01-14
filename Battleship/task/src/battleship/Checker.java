package battleship;

public class Checker {
	//TODO rename
	private static boolean tmpName(String[][] field, int i, int j) {
		if (i != 0 && i != field.length - 1) {
			if (j != 0 && j != field[0].length - 1) {
				//i-1 j
				if ("O".equals(field[i - 1][j])) return false;
				//i+1 j
				if ("O".equals(field[i + 1][j])) return false;
				//i j-1
				if ("O".equals(field[i][j - 1])) return false;
				//i j+1
				return !"O".equals(field[i][j + 1]);
			} else if (j == 0) {
				//i-1 j
				if ("O".equals(field[i - 1][j])) return false;
				//i+1 j
				if ("O".equals(field[i + 1][j])) return false;
				//i j+1
				return !"O".equals(field[i][j + 1]);
			} else {
				//i-1 j
				if ("O".equals(field[i - 1][j])) return false;
				//i+1 j
				if ("O".equals(field[i + 1][j])) return false;
				//i j-1
				return !"O".equals(field[i][j - 1]);
			}
		} else if (i == 0) {
			if (j != 0 && j != field[0].length - 1) {
				//i+1 j
				if ("O".equals(field[i + 1][j])) return false;
				//i j-1
				if ("O".equals(field[i][j - 1])) return false;
				//i j+1
				return !"O".equals(field[i][j + 1]);
			} else if (j == 0) {
				//i+1 j
				if ("O".equals(field[i + 1][j])) return false;
				//i j+1
				return !"O".equals(field[i][j + 1]);
			} else {
				//i+1 j
				if ("O".equals(field[i + 1][j])) return false;
				//i j-1
				return !"O".equals(field[i][j - 1]);
			}
		} else {
			if (j != 0 && j != field[0].length - 1) {
				//i-1 j
				if ("O".equals(field[i - 1][j])) return false;
				//i j-1
				if ("O".equals(field[i][j - 1])) return false;
				//i j+1
				return !"O".equals(field[i][j + 1]);
			} else if (j == 0) {
				//i-1 j
				if ("O".equals(field[i - 1][j])) return false;
				//i j+1
				return !"O".equals(field[i][j + 1]);
			} else {
				//i-1 j
				if ("O".equals(field[i - 1][j])) return false;
				//i j-1
				return !"O".equals(field[i][j - 1]);
			}
		}
	}

	/**
	 * Method will check whether coordinates are correct
	 *
	 * @param ship             string which contains name of ship(carrier, battleship, submarine, cruiser, destroyer)
	 * @param firstCoordinate  string which contains coordinates of the ship beginning (f3, a1, d5)
	 * @param secondCoordinate string which contains coordinates of the ship ending (f3, a1, d5)
	 * @return true -> correct, false -> wrong
	 */
	public boolean checkCoordinates(String ship, String firstCoordinate, String secondCoordinate) {
		//check if coordinates are equal
		if (firstCoordinate.matches(secondCoordinate)) return false;

		//check if coordinates are diagonal
		if (firstCoordinate.charAt(0) != secondCoordinate.charAt(0)
				&& !firstCoordinate.substring(1).equals(secondCoordinate.substring(1)))
			return false;

		//check length of ship
		int length;
		int firstNum;
		int secondNum;

		if (firstCoordinate.charAt(1) == secondCoordinate.charAt(1)) {
			firstNum = Helper.letterToNumber(firstCoordinate.charAt(0));
			secondNum = Helper.letterToNumber(secondCoordinate.charAt(0));
		} else {
			firstNum = Integer.parseInt(firstCoordinate.substring(1));
			secondNum = Integer.parseInt(secondCoordinate.substring(1));
		}

		length = secondNum > firstNum ? secondNum - firstNum : firstNum - secondNum;

		switch (ship) {
			case "carrier":
				if (length != 4) return false;
				break;
			case "battleship":
				if (length != 3) return false;
				break;
			case "submarine":
			case "cruiser":
				if (length != 2) return false;
				break;
			case "destroyer":
				if (length != 1) return false;
				break;
		}

		return true;
	}

	public boolean checkShipDisplacement(String[][] field, String firstCoordinate, String secondCoordinate) {
		return tmpName(field, Helper.letterToNumber(firstCoordinate.charAt(0)), Integer.parseInt(firstCoordinate.substring(1)))
				&& tmpName(field, Helper.letterToNumber(secondCoordinate.charAt(0)), Integer.parseInt(secondCoordinate.substring(1)));
	}
}
