package battleship;

import java.util.Arrays;

public class Ship {
	private final String name; // official name e.g. Air Carrier
	private final String code; // coded name e.g. carrier
	private final int size;
	private boolean isSank;
	private String[] shipCoordinates;

	public Ship(String name, String code, int size) {
		this.name = name;
		this.code = code;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public String getCode() {
		return code;
	}

	public String[] getShipCoordinates() {
		return shipCoordinates;
	}

	public void setShipCoordinates(String[] shipCoordinates) {
		this.shipCoordinates = shipCoordinates;
	}

	public boolean isSank() {
		return isSank;
	}

	public void setSank(boolean sank) {
		isSank = sank;
	}

	@Override
	public String toString() {
		return "Ship{" +
				"name='" + name + '\'' +
				", code='" + code + '\'' +
				", size=" + size +
				", isSank=" + isSank +
				", shipCoordinates=" + Arrays.toString(shipCoordinates) +
				'}';
	}
}
