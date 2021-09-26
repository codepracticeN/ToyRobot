package toyrobot.model;

public class Position {

	private static final Integer MIN = new Integer(0);

	private static final Integer MAX = new Integer(6);

	private Integer x;

	private Integer y;

	private String f;

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		if (validate(x))
			this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		if (validate(y))
			this.y = y;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	/**
	 * validation to ensure robot will not move out of table
	 * 
	 * @param value
	 * @return true if robot is in the table
	 */
	private boolean validate(Integer value) {
		if (value == null) return false;
		return value.compareTo(MIN) >= 0 && value.compareTo(MAX) < 0;
	}
}
