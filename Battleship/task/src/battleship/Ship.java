package battleship;

public class Ship {
	private String name;
	private String code;
	private int size;

	public Ship(String name, String code, int size) {
		this.name = name;
		this.code = code;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Ship{" +
				"name='" + name + '\'' +
				", size=" + size +
				'}';
	}
}
