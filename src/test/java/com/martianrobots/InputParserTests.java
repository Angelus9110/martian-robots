package com.martianrobots;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.util.Queue;
import org.junit.jupiter.api.Test;

public class InputParserTests {

    @Test
    public void cityParserShouldParseGridBoundsCorrectly() {
        InputParser parser = new InputParser("src/test/resources/test_input.txt");
        Point expectedGridBounds = new Point(5, 3);

        RobotMovement robotMovement = parser.generateApplicationState();

        assertEquals(expectedGridBounds, robotMovement.getGridBounds());
    }

    @Test
    public void cityParserShouldParseInitialRobotStatesCorrectly() {
        InputParser parser = new InputParser("src/test/resources/test_input.txt");

        RobotMovement robotMovement = parser.generateApplicationState();

        assertEquals(robotMovement.getRobotStates().size(), 3);

        Robot r1 = robotMovement.getRobotStates().poll();
        Robot r2 = robotMovement.getRobotStates().poll();
        Robot r3 = robotMovement.getRobotStates().poll();

        assertEquals(new Point(1, 1), r1.getCurrentPosition());
        assertEquals(Orientation.E, r1.getOrientation());

        assertEquals(new Point(3, 2), r2.getCurrentPosition());
        assertEquals(Orientation.N, r2.getOrientation());

        assertEquals(new Point(0, 3), r3.getCurrentPosition());
        assertEquals(Orientation.W, r3.getOrientation());
    }

    @Test
    public void cityParserShouldParseRobotInstructionsCorrectly() {
        InputParser parser = new InputParser("src/test/resources/test_input.txt");

        RobotMovement robotMovement = parser.generateApplicationState();

        Queue<Instruction> queue = robotMovement.getRobotStates().poll().getInstructions();

        assertEquals(Instruction.R, queue.poll());
        assertEquals(Instruction.F, queue.poll());
        assertEquals(Instruction.L, queue.poll());
    }

}
