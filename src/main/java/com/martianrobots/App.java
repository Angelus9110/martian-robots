package com.martianrobots;

public class App {

    public static void main(String[] args) {
        String inputFile = args[0];

        InputParser parser = new InputParser(inputFile);

        RobotMovement robotMovement = parser.generateApplicationState();

        String output = robotMovement.triggerStateMachine();

        System.out.println(output);
    }
}
