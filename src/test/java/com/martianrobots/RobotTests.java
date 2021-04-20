package com.martianrobots;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;

public class RobotTests {

    @Test
    public void getOrientationShouldReturnRobotStateCurrentOrientation() throws Exception {
        Robot state = new Robot(Orientation.E, null);

        Orientation o = state.getOrientation();

        assertEquals(Orientation.E, o);
    }

    @Test
    public void getCurrentPositionShouldReturnRobotCurrentPosition() throws Exception {
        Robot state = new Robot(Orientation.E, new Point(1, 2));

        Point p = state.getCurrentPosition();

        assertEquals(new Point(1, 2), p);
    }

    @Test
    public void getPreviousPositionShouldBeNullAtInitialisation() throws Exception {
        Robot state = new Robot(Orientation.E, new Point(1, 2));

        Point p = state.getPreviousPosition();

        assertEquals(null, p);
    }

    @Test
    public void AddInstructionsAndDequeueNextInstructionShouldAddAndRemoveInstructionsInCorrectOrder() throws Exception {
        Robot state = new Robot(Orientation.E, new Point(1, 2));
        Queue<Instruction> queue = new LinkedList<>();
        queue.add(Instruction.F);
        queue.add(Instruction.L);
        state.addInstructions(queue);

        Instruction x = state.dequeueNextInstruction();
        Instruction y = state.dequeueNextInstruction();

        assertEquals(Instruction.F, x);
        assertEquals(Instruction.L, y);
    }

    @Test
    public void canExecuteNextInstructionShouldReturnFalseIfInstructionSetIsEmpty() throws Exception {
        Robot state = new Robot(Orientation.E, new Point(1, 2));

        boolean canExecuteNextInstruction = state.canExecuteNextInstruction();

        assertFalse(canExecuteNextInstruction);
    }

    @Test
    public void executeNextInstructionShouldChangeRobotStateCorrectly() throws Exception {
        Robot state = new Robot(Orientation.E, new Point(1, 2));
        Queue<Instruction> queue = new LinkedList<>();
        queue.add(Instruction.F);
        queue.add(Instruction.L);
        state.addInstructions(queue);

        assertTrue(state.canExecuteNextInstruction());

        state.executeNextInstruction();
        assertEquals(new Point(2, 2), state.getCurrentPosition());
        assertEquals(Orientation.E, state.getOrientation());
        assertTrue(state.canExecuteNextInstruction());

        state.executeNextInstruction();
        assertEquals(new Point(2, 2), state.getCurrentPosition());
        assertEquals(Orientation.N, state.getOrientation());
        assertFalse(state.canExecuteNextInstruction());
    }

    @Test
    public void peekNextInstructionExecutionPositionResultShouldNotAlterRobotState() throws Exception {
        Robot state = new Robot(Orientation.E, new Point(1, 2));
        Queue<Instruction> queue = new LinkedList<>();
        queue.add(Instruction.L);
        queue.add(Instruction.F);
        state.addInstructions(queue);

        Point p = state.peekNextInstructionExecutionPositionResult();
        assertEquals(new Point(1, 2), p);
        assertTrue(state.getInstructions().size() == 2);

        state.dequeueNextInstruction();

        p = state.peekNextInstructionExecutionPositionResult();
        assertEquals(new Point(2, 2), p);
        assertTrue(state.getInstructions().size() == 1);

        assertEquals(new Point(1, 2), state.getCurrentPosition());
        assertEquals(Orientation.E, state.getOrientation());
    }

}