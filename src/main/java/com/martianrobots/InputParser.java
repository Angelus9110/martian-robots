package com.martianrobots;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class InputParser {
    private final String inputFile;
    private Point gridBounds;
    private Queue<Robot> robots;

    public InputParser(String inputFile) {
        this.inputFile = inputFile;
        this.robots = new LinkedList<>();
        this.gridBounds = null;
    }

    public RobotMovement generateApplicationState() {

        BufferedReader bufferedReader = null;
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(inputFile);
            bufferedReader = new BufferedReader(fileReader);

            String gridBoundsString;

            if ((gridBoundsString = bufferedReader.readLine()) != null) {
                gridBounds = generatePlanet(gridBoundsString);
            }

            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                if(!currentLine.isEmpty()) {
                    String initialRobotState = currentLine;
                    String instructions = bufferedReader.readLine();
                    Robot rs = generateRobotState(initialRobotState);
                    rs.addInstructions(generateInstructionQueue(instructions));
                    robots.add(rs);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();

                if (fileReader != null)
                    fileReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return new RobotMovement(robots, gridBounds);
    }

    private Robot generateRobotState(String initialState) {
        String delimiters = " ";
        StringTokenizer tokenizer = new StringTokenizer(initialState, delimiters);

        int xCoordinate = Integer.parseInt(tokenizer.nextToken());
        int yCoordinate = Integer.parseInt(tokenizer.nextToken());
        Point startCoordinates = new Point(xCoordinate, yCoordinate);

        Orientation orientation = Orientation.valueOf(tokenizer.nextToken());

        return new Robot(orientation, startCoordinates);
    }

    private Queue<Instruction> generateInstructionQueue(String instructions) {

        Queue<Instruction> instructionQueue = new LinkedList<>();

        for (char instruction : instructions.toCharArray()) {
            Instruction curentInstruction = Instruction.valueOf(Character.toString(instruction));
            instructionQueue.add(curentInstruction);
        }

        return instructionQueue;
    }

    private Point generatePlanet(String planetString) {
        String delimiters = " ";
        StringTokenizer tokenizer = new StringTokenizer(planetString, delimiters);

        int xBounds = Integer.parseInt(tokenizer.nextToken());
        int yBounds = Integer.parseInt(tokenizer.nextToken());

        return new Point(xBounds, yBounds);
    }

}
