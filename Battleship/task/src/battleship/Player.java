package battleship;

public class Player {
	private final int id;
	private String[][] field;
	private String[][] fogOfWar;
	private Ship[] ships;

	public Player(int id, String[][] field, String[][] fogOfWar, Ship[] ships) {
		this.id = id;
		this.field = field;
		this.fogOfWar = fogOfWar;
		this.ships = ships;
	}

	public int getId() {
		return id;
	}

	public String[][] getField() {
		return field;
	}

	public void setField(String[][] field) {
		this.field = field;
	}

	public String[][] getFogOfWar() {
		return fogOfWar;
	}

	public void setFogOfWar(String[][] fogOfWar) {
		this.fogOfWar = fogOfWar;
	}

	public Ship[] getShips() {
		return ships;
	}

	public void setShips(Ship[] ships) {
		this.ships = ships;
	}
}
