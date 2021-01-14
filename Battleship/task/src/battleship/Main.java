package battleship;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String[][] FIELD = new String[11][11];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ship[] ships = new Ship[]{new Ship("Aircraft Carrier", "carrier", 5),
                new Ship("Battleship", "battleship", 4),
                new Ship("Submarine", "submarine", 3),
                new Ship("Cruiser", "cruiser", 3),
                new Ship("Destroyer", "destroyer", 2)};

        createField();
        showField();

        int i = 0;
        while (i != 5) {
            System.out.format("Enter the coordinates of the %s (%d cells): \n", ships[i].getName(),
                    ships[i].getSize());
            String[] coordinates = scanner.nextLine().toLowerCase(Locale.ROOT).split(" ");

            try {
                putShipOnTheField(ships[i].getCode(), coordinates[0], coordinates[1]);
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                continue;
            }

            showField();
            i++;
        }
    }

    /**
     */
    /**
     * Method will check whether coordinates are right and put given ship on the field
     *
     * @param ship             string which contains name of ship(carrier, battleship, submarine, cruiser, destroyer)
     * @param firstCoordinate  string which contains coordinates of the ship beginning (f3, a1, d5)
     * @param secondCoordinate string which contains coordinates of the ship ending (f3, a1, d5)
     * @throws IllegalStateException if coordinates were not correct
     *                               or was not able to convert letter from coordinate to number
     */
    private static void putShipOnTheField(String ship, String firstCoordinate, String secondCoordinate) throws IllegalStateException {
        //check whether coordinates are correct
        if (!checkCoordinates(ship, firstCoordinate, secondCoordinate))
            throw new IllegalStateException("Error! Wrong coordinates");
        //check whether user can place ship in that position
        if (!checkShipDisplacement(firstCoordinate, secondCoordinate))
            throw new IllegalStateException("Error! Too close to another ship");

        //put ship on field
        try {
            if (firstCoordinate.charAt(0) == secondCoordinate.charAt(0)) {
                int row = letterToNumber(firstCoordinate.charAt(0));
                int num1 = Integer.parseInt(firstCoordinate.substring(1));
                int num2 = Integer.parseInt(secondCoordinate.substring(1));
                int beginning = Math.min(num1, num2);
                int end = Math.max(num1, num2);

                for (int i = beginning; i <= end; i++) {
                    FIELD[row][i] = "O";
                }
            } else {
                int num1 = letterToNumber(firstCoordinate.charAt(0));
                int num2 = letterToNumber(secondCoordinate.charAt(0));
                int beginning = Math.min(num1, num2);
                int end = Math.max(num1, num2);
                int column = Integer.parseInt(firstCoordinate.substring(1));

                for (int i = beginning; i <= end; i++) {
                    FIELD[i][column] = "O";
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e.getMessage());
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
    private static boolean checkCoordinates(String ship, String firstCoordinate, String secondCoordinate) {
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
            firstNum = letterToNumber(firstCoordinate.charAt(0));
            secondNum = letterToNumber(secondCoordinate.charAt(0));
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

    private static boolean checkShipDisplacement(String firstCoordinate, String secondCoordinate) {
        return tmpName(letterToNumber(firstCoordinate.charAt(0)), Integer.parseInt(firstCoordinate.substring(1)))
                && tmpName(letterToNumber(secondCoordinate.charAt(0)), Integer.parseInt(secondCoordinate.substring(1)));
    }

    //TODO rename
    private static boolean tmpName(int i, int j) {
        if (i != 0 && i != FIELD.length - 1) {
            if (j != 0 && j != FIELD[0].length - 1) {
                //i-1 j
                if ("O".equals(FIELD[i - 1][j])) return false;
                //i+1 j
                if ("O".equals(FIELD[i + 1][j])) return false;
                //i j-1
                if ("O".equals(FIELD[i][j - 1])) return false;
                //i j+1
                return !"O".equals(FIELD[i][j + 1]);
            } else if (j == 0) {
                //i-1 j
                if ("O".equals(FIELD[i - 1][j])) return false;
                //i+1 j
                if ("O".equals(FIELD[i + 1][j])) return false;
                //i j+1
                return !"O".equals(FIELD[i][j + 1]);
            } else {
                //i-1 j
                if ("O".equals(FIELD[i - 1][j])) return false;
                //i+1 j
                if ("O".equals(FIELD[i + 1][j])) return false;
                //i j-1
                return !"O".equals(FIELD[i][j - 1]);
            }
        } else if (i == 0) {
            if (j != 0 && j != FIELD[0].length - 1) {
                //i+1 j
                if ("O".equals(FIELD[i + 1][j])) return false;
                //i j-1
                if ("O".equals(FIELD[i][j - 1])) return false;
                //i j+1
                return !"O".equals(FIELD[i][j + 1]);
            } else if (j == 0) {
                //i+1 j
                if ("O".equals(FIELD[i + 1][j])) return false;
                //i j+1
                return !"O".equals(FIELD[i][j + 1]);
            } else {
                //i+1 j
                if ("O".equals(FIELD[i + 1][j])) return false;
                //i j-1
                return !"O".equals(FIELD[i][j - 1]);
            }
        } else {
            if (j != 0 && j != FIELD[0].length - 1) {
                //i-1 j
                if ("O".equals(FIELD[i - 1][j])) return false;
                //i j-1
                if ("O".equals(FIELD[i][j - 1])) return false;
                //i j+1
                return !"O".equals(FIELD[i][j + 1]);
            } else if (j == 0) {
                //i-1 j
                if ("O".equals(FIELD[i - 1][j])) return false;
                //i j+1
                return !"O".equals(FIELD[i][j + 1]);
            } else {
                //i-1 j
                if ("O".equals(FIELD[i - 1][j])) return false;
                //i j-1
                return !"O".equals(FIELD[i][j - 1]);
            }
        }
    }

    /**
     * Method will output FIELD array to the console
     */
    private static void showField() {
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
    private static void createField() {
        //fill first row of FIELD with numbers
        FIELD[0] = new String[]{" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        //array contains letters which will be at the beginning of every row
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

        //fill FIELD
        for (int i = 1; i < FIELD.length; i++) {
            FIELD[i][0] = letters[i - 1];

            for (int j = 1; j < FIELD[i].length; j++) {
                FIELD[i][j] = "~";
            }
        }
    }

    /**
     * Convert letter to number
     *
     * @param letter char with letter (a,b,c,d,e,f,g,h,i,j)
     * @return integer with number that corresponds to given letter
     * @throws IllegalArgumentException if was not able to convert letter to number
     */
    private static int letterToNumber(char letter) throws IllegalArgumentException {
        switch (letter) {
            case 'a':
                return 1;
            case 'b':
                return 2;
            case 'c':
                return 3;
            case 'd':
                return 4;
            case 'e':
                return 5;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'h':
                return 8;
            case 'i':
                return 9;
            case 'j':
                return 10;
            default:
                throw new IllegalArgumentException("Error! Wrong letter: " + letter);
        }
    }
}
