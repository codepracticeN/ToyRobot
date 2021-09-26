package toyrobot.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import toyrobot.model.ToyRobot;

public class ToyRobotApplication {

	private static final String FILE_PATH_ERROR = "Reading file error: Please ensure correct file path is entered";

	private static final String FILE_NOT_EXISTED = "Reading file error: Please ensure input.txt file is existed";

	private static final String PROCESS_DATA_ERROR = "Please check input file to ensure all commands are correct. Correct commands would be \'MOVE, LEFT, RIGHT, REPORT or PLACE X,Y,F\'";

	private static final String PLACE_ERROR = "Could not place robot Please provide PLACE command as per format \' PLACE X,Y,F \'";
	
	private static final String EMPTY_FILE = "Empty file: please enter valid file input";

	public static void main(String[] args) {
		ToyRobot robot = new ToyRobot();
		Scanner reader = new Scanner(System.in);
		String userInput = null;
		do {
			System.out.print("Please enter your file path: ");
			userInput = reader.nextLine();
			try (Stream<String> stream = Files.lines(Paths.get(userInput))) {
				List<String> fileData = stream.collect(Collectors.toList());
				if (fileData.size() == 0) {
					System.out.println(EMPTY_FILE);
					userInput = null;
				} else {
					fileData.forEach(command -> robot.processRobotWithCommand(command));
				}
			} catch (InvalidPathException e) {
				userInput = null;
				System.out.println(FILE_PATH_ERROR);
			} catch (IOException e) {
				userInput = null;
				System.out.println(FILE_NOT_EXISTED);
			} catch (NumberFormatException e) {
				userInput = null;
				System.out.println(PLACE_ERROR);
			} catch (IllegalArgumentException e) {
				userInput = null;
				System.out.println(PROCESS_DATA_ERROR);
			}
		} while (userInput == null);
		reader.close();
	}
}
