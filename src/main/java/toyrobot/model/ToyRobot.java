package toyrobot.model;

public class ToyRobot {

	private Position position;

	private static final int MOVE_STEP = 1;

	private static final String REPORT_ERROR = "Invalid position please check input file and correct commands";
	
	private static final int NUMBER_OF_VARIABLES_IN_PLACE_COMMAND = 3;

	enum CommandType {
		MOVE, LEFT, RIGHT, REPORT, PLACE
	}

	enum FaceType {
		NORTH, EAST, SOUTH, WEST
	}

	enum MovementType {
		VERTICAL, HORIZONTAL
	}

	public ToyRobot() {
		setPosition(new Position());
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * place action
	 * 
	 * @param x
	 * @param y
	 * @param f
	 */
	public void place(Integer x, Integer y, String f) {
		Position position = new Position();
		if (x != null && x != null && f != null) {
			position.setX(x);
			position.setY(y);
			position.setF(f);
			setPosition(position);
		}
	}

	/**
	 * move action
	 */
	public void move() {
		String face = getPosition().getF();
		Integer x = getPosition().getX();
		Integer y = getPosition().getY();
		if (x != null && y != null && face != null) {
			FaceType faceType = FaceType.valueOf(face);
			switch (faceType) {
			case NORTH:
				processMovement(Integer.sum(y.intValue(), MOVE_STEP),
						MovementType.VERTICAL);
				break;
			case EAST:
				processMovement(Integer.sum(x.intValue(), MOVE_STEP),
						MovementType.HORIZONTAL);
				break;
			case SOUTH:
				processMovement(new Integer(y.intValue() - MOVE_STEP),
						MovementType.VERTICAL);
				break;
			case WEST:
				processMovement(new Integer(x.intValue() - MOVE_STEP),
						MovementType.HORIZONTAL);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * set position base on movement type. if vertical, y position will be set
	 * if horizontal, x position will be set
	 * 
	 * @param value
	 * @param type
	 */
	private void processMovement(Integer value, MovementType type) {
		switch (type) {
		case VERTICAL:
			getPosition().setY(value);
			break;
		case HORIZONTAL:
			getPosition().setX(value);
			break;
		default:
			break;
		}
	}

	/**
	 * turn left action
	 */
	public void turnLeft() {
		String face = getPosition().getF();
		if (face != null) {
			FaceType faceType = FaceType.valueOf(face);
			switch (faceType) {
			case NORTH:
				getPosition().setF(FaceType.WEST.name());
				break;
			case EAST:
				getPosition().setF(FaceType.NORTH.name());
				break;
			case SOUTH:
				getPosition().setF(FaceType.EAST.name());
				break;
			case WEST:
				getPosition().setF(FaceType.SOUTH.name());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * turn right action
	 */
	public void turnRight() {
		String face = getPosition().getF();
		if (face != null) {
			FaceType faceType = FaceType.valueOf(face);
			switch (faceType) {
			case NORTH:
				getPosition().setF(FaceType.EAST.name());
				break;
			case EAST:
				getPosition().setF(FaceType.SOUTH.name());
				break;
			case SOUTH:
				getPosition().setF(FaceType.WEST.name());
				break;
			case WEST:
				getPosition().setF(FaceType.NORTH.name());
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * report action
	 */
	public void report() {
		Position position = getPosition();
		if (position.getX() != null && getPosition().getY() != null
				&& position.getF() != null) {
			System.out.println(String.valueOf(getPosition().getX()) + ','
					+ String.valueOf(getPosition().getY()) + ','
					+ getPosition().getF());
		} else {
			System.out.println(REPORT_ERROR);
		}
	}

	public void processRobotWithCommand(String command) {
		if (command != null) {
			String[] commandValues = command.split(" ");
			CommandType commandType = CommandType.valueOf(commandValues[0]);

			switch (commandType) {
			case PLACE:
				processPlaceRobot(commandValues[1]);
				break;
			case MOVE:
				move();
				break;
			case LEFT:
				turnLeft();
				break;
			case RIGHT:
				turnRight();
				break;
			case REPORT:
				report();
				break;
			default:
				break;
			}
		}
	}

	private void processPlaceRobot(String placeCommand) {
		String[] positionValues = placeCommand.split(",");
		if (positionValues.length == NUMBER_OF_VARIABLES_IN_PLACE_COMMAND) {
			place(Integer.valueOf(positionValues[0]),
					Integer.valueOf(positionValues[1]), positionValues[2]);
		}
	}
}
