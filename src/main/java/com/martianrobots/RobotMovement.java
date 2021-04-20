package com.martianrobots;

import java.awt.*;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class RobotMovement {

    private final Queue<Robot> robots;
    private final Point gridBounds;
    private Set<Point> robotScents;

    public RobotMovement(Queue<Robot> robots, Point gridBounds) {
        this.robots = robots;
        this.gridBounds = gridBounds;
        this.robotScents = new HashSet<>();
    }

    public Point getGridBounds() {
        return gridBounds;
    }

    public Set<Point> getRobotScents() {
        return robotScents;
    }

    public Queue<Robot> getRobotStates() {
        return robots;
    }

    public String triggerStateMachine() {
        StringBuilder sb = new StringBuilder();
        for (Robot state : robots) {
            executeRobotInstructions(state);
            sb.append(state.toString() + '\n');
        }
        return sb.toString();
    }

    private void executeRobotInstructions(Robot state) {

        while(state.canExecuteNextInstruction()) {
            if(isOnScentedPosition(state.getCurrentPosition())
                    && isOffGridPosition(state.peekNextInstructionExecutionPositionResult())) {
                state.dequeueNextInstruction();
            } else {
                state.executeNextInstruction();
                if(isOffGridPosition(state.getCurrentPosition())) {
                    state.setLostState(true);
                    //Add last valid grid position to robotScent set
                    addRobotScent(state.getPreviousPosition());
                    break;
                }
            }
        }
    }

    private boolean isOnScentedPosition(Point p) {
        return getRobotScents().contains(p);
    }

    private boolean isOffGridPosition(Point p) {
        return (p.x > gridBounds.x
                || p.y > gridBounds.y
                || p.x < 0
                || p.y < 0);
    }

    private void addRobotScent(Point p) {
        robotScents.add(p);
    }

}
