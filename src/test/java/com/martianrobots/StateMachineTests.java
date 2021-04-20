package com.martianrobots;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;

public class StateMachineTests {

    @Test
    public void triggerStateMachineShouldExecuteInstructionsInCorrectOrder() {
        Robot state = new Robot(Orientation.E, new Point(1, 1));
        Queue<Instruction> instructionQueue = new LinkedList<>();
        instructionQueue.add(Instruction.R);
        instructionQueue.add(Instruction.F);
        instructionQueue.add(Instruction.L);
        state.addInstructions(instructionQueue);

        Robot state2 = new Robot(Orientation.N, new Point(3, 2));
        Queue<Instruction> instructionQueue2 = new LinkedList<>();
        instructionQueue2.add(Instruction.F);
        instructionQueue2.add(Instruction.R);
        state2.addInstructions(instructionQueue2);

        Queue<Robot> robots = new LinkedList<>();
        robots.add(state);
        robots.add(state2);

        RobotMovement robotMovement = new RobotMovement(robots, new Point(5, 3));

        String expectedOutput = "1 0 E\n3 3 E\n";

        String output = robotMovement.triggerStateMachine();

        assertEquals(expectedOutput, output);
    }

}
