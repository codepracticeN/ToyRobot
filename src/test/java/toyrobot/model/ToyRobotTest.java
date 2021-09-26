package toyrobot.model;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ToyRobotTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	private final PrintStream originalOut = System.out;
	
	private ToyRobot toyRobot;
	
	private final String moveCommand = "MOVE";

	private final String turnLeftCommand = "LEFT";

	private final String turnRightCommand = "RIGHT";

	private final String reportCommand = "REPORT";
	
	private static final String REPORT_ERROR = "Invalid position please check input file and correct commands";
	
	private static final String FILE_PATH1 = "src/test/resources/test1.txt";
	
	private static final String FILE_PATH = "src/test/resources/test.txt";
	
	private static final String FILE_PATH2 = "src/test/resources/test2.txt";
	
	private static final String FILE_PATH3 = "src/test/resources/test3.txt";
	
	
	@Before
	public void setUp()
	{
		toyRobot = new ToyRobot();
	}
	
	@After
	public void tearDown() throws Exception {
		System.setOut(originalOut);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidProcessRobotData() {
		String place = "pLACE  1 , 4, 5 , NORTH";
		toyRobot.processRobotWithCommand(place);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testInvalidPlaceRobot() {
		String place = "PLACE x,4,NORTH";
		System.setOut(new PrintStream(outContent));
		toyRobot.processRobotWithCommand(place);
	}
	
	@Test
	public void testValidPlaceRobot() {
		String place = "PLACE 1,4,NORTH";
		toyRobot.processRobotWithCommand(place);
		assertStatement(toyRobot, new Integer(1), new Integer(4), "NORTH");
	}
	
	@Test
	public void testMoveRobotWithoutPlaceCommand() {
		toyRobot.processRobotWithCommand(moveCommand);
		// assert all x,y,f are null as there is no place first
		assertStatement(toyRobot,null,null,null);
	}
	
	@Test
	public void testValidMoveRobotToNortWithPlaceCommand() {
		String placeCommand = "PLACE 0,0,NORTH";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		
		// assert correct movement of the robot to the north
		assertStatement(toyRobot, new Integer(0), new Integer(1), "NORTH");
	}
	
	@Test
	public void testValidMoveRobotToEastWithPlaceCommand() {
		String placeCommand = "PLACE 0,0,EAST";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		
		// assert correct movement of the robot to the east
		assertStatement(toyRobot, new Integer(1), new Integer(0), "EAST");
	}
	
	@Test
	public void testValidMoveRobotToSouthWithPlaceCommand() {
		String placeCommand = "PLACE 1,1,SOUTH";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		
		// assert correct movement of the robot to the south
		assertStatement(toyRobot, new Integer(1), new Integer(0), "SOUTH");
	}
	
	@Test
	public void testValidMoveRobotToWestWithPlaceCommand() {
		String placeCommand = "PLACE 1,1,WEST";
		String moveCommand = "MOVE";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		
		// assert correct movement of the robot to the west
		assertStatement(toyRobot, new Integer(0), new Integer(1), "WEST");
	}
	
	@Test
	public void testInvalidMoveRobotToNorthWithPlaceCommand() {
		String placeCommand = "PLACE 0,5,NORTH";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		
		// assert unchange movement as robot will fall
		assertStatement(toyRobot, new Integer(0), new Integer(5), "NORTH");
	}
	
	@Test
	public void testInvalidMoveRobotToEastWithPlaceCommand() {
		String placeCommand = "PLACE 5,0,EAST";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		
		// assert unchange movement as robot will fall
		assertStatement(toyRobot, new Integer(5), new Integer(0), "EAST");
	}
	
	@Test
	public void testInvalidMoveRobotToSouthWithPlaceCommand() {
		String placeCommand = "PLACE 0,0,SOUTH";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		
		// assert unchange movement as robot will fall
		assertStatement(toyRobot, new Integer(0), new Integer(0), "SOUTH");
	}
	
	@Test
	public void testInvalidMoveRobotToWestWithPlaceCommand() {
		String placeCommand = "PLACE 0,0,WEST";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		
		// assert unchange movement as robot will fall
		assertStatement(toyRobot, new Integer(0), new Integer(0), "WEST");
	}
	
	@Test
	public void testTurnRobotWithoutPlaceCommand() {
		toyRobot.processRobotWithCommand(turnLeftCommand);
		toyRobot.processRobotWithCommand(turnRightCommand);
		//assert all x,y,f are null as there is no place before it
		assertStatement(toyRobot, null, null, null);
	}
	
	@Test
	public void testTurnRobotToLeftWithPlaceCommand() {
		String placeCommand = "PLACE 0,0,NORTH";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(turnLeftCommand);
		//assert correct turn left of the robot
		assertStatement(toyRobot, new Integer(0), new Integer(0), "WEST");
	}
	
	@Test
	public void testTurnRobotToRightWithPlaceCommand() {
		String placeCommand = "PLACE 0,0,NORTH";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(turnRightCommand);
		//assert correct turn left of the robot
		assertStatement(toyRobot, new Integer(0), new Integer(0), "EAST");
	}
	
	@Test
	public void testReportCommandWithoutPlaceCommand() {
		System.setOut(new PrintStream(outContent));
		toyRobot.processRobotWithCommand(reportCommand);
		
		//assert empty as there is no data 
		assertEquals(REPORT_ERROR, outContent.toString().replaceAll("(\r\n)", ""));
	}
	
	@Test
	public void testReportCommandWithPlaceCommand() {
		System.setOut(new PrintStream(outContent));
		String placeCommand = "PLACE 0,0,NORTH";
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		toyRobot.processRobotWithCommand(reportCommand);
		
		//assert out put same as expectations
		assertEquals("0,1,NORTH", outContent.toString().replaceAll("(\r\n)", ""));
	}
	
	@Test
	public void testComplexRobotMoveExample1() {
		String placeCommand = "PLACE 0,0,NORTH";
		System.setOut(new PrintStream(outContent));
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(turnLeftCommand);
		toyRobot.processRobotWithCommand(reportCommand);
		
		//Assert result
		assertEquals("0,0,WEST", outContent.toString().replaceAll("(\r\n)", ""));
	}
	
	@Test
	public void testComplexRobotMoveExample2() {
		String placeCommand = "PLACE 1,2,EAST";
		System.setOut(new PrintStream(outContent));
		toyRobot.processRobotWithCommand(placeCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		toyRobot.processRobotWithCommand(turnLeftCommand);
		toyRobot.processRobotWithCommand(moveCommand);
		toyRobot.processRobotWithCommand(reportCommand);
		
		//Assert result
		assertEquals("3,3,NORTH", outContent.toString().replaceAll("(\r\n)", ""));
	}
	
	@Test
	public void testComplexRobotMoveWithFileInput1() {
		try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH1))) {
			stream.forEach(command -> toyRobot.processRobotWithCommand(command));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertStatement(toyRobot, new Integer(5), new Integer(0), "EAST");
	}
	
	@Test
	public void testComplexRobotMoveWithFileInput2() {
		System.setOut(new PrintStream(outContent));
		try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH2))) {
			stream.forEach(command -> toyRobot.processRobotWithCommand(command));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(REPORT_ERROR, outContent.toString().replaceAll("(\r\n)", ""));
	}
	
	@Test
	public void testComplexRobotMoveWithFileInput3() {
		try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH3))) {
			stream.forEach(command -> toyRobot.processRobotWithCommand(command));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertStatement(toyRobot, new Integer(1), new Integer(0), "SOUTH");
	}
	
	@Test
	public void testComplexRobotMoveWithFileInput() {
		try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
			stream.forEach(command -> toyRobot.processRobotWithCommand(command));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertStatement(toyRobot, new Integer(5), new Integer(4), "NORTH");
	}
	
	private void assertStatement(ToyRobot toyRobot, Integer actual1, Integer actual2, String actual3 )
	{
		assertEquals(toyRobot.getPosition().getX(), actual1);
		assertEquals(toyRobot.getPosition().getY(), actual2);
		assertEquals(toyRobot.getPosition().getF(), actual3);
	}
}
