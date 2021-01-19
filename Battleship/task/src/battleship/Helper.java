package battleship;

public class Helper {
	/**
	 * Convert letter to number
	 *
	 * @param letter char with letter (a,b,c,d,e,f,g,h,i,j)
	 * @return integer with number that corresponds to given letter
	 * @throws IllegalArgumentException if was not able to convert letter to number
	 */
	public static int letterToNumber(char letter) throws IllegalArgumentException {
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
