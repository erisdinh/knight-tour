package quynh;

public class Square implements Comparable {
	private int x;
	private int y;
	private int key;

	public Square() {
	}

	public Square(int x, int y, int key) {
		this.x = x;
		this.y = y;
		this.key = key;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String toString() {
		return "X: " + this.x + "\n" + "Y: " + this.y + "\n" + "Key: " + this.key + "\n";
	}

	@Override
	public int compareTo(Object compare) {
		int compareKey = ((Square) compare).getKey();
		return this.key - compareKey;
	}
}
