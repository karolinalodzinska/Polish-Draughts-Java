package com.codecool.polishdraughts;
import java.util.Scanner;

public class Game {

    Board board;
    boolean currentPlayer = true;

    public void playRound(){
        displayTurn();
        tryToMakeMove();
        System.out.println(board);
    }

    private void displayTurn() {
        if (currentPlayer) {
            System.out.println("White turn!");
        } else {
            System.out.println("Black turn!");
        }
    }

    public void start() {
        System.out.println("Welcome in Polish Draughts Game! White starts!");
        createBoard();
        while (!checkForWinner()) {
            playRound();
            currentPlayer = !currentPlayer;
        }
    }

    private void createBoard() {
        Scanner scan = new Scanner(System.in);
        boolean wrongUserInput = true;
        while (wrongUserInput) {
            System.out.print("Put the size of the board. It need to be from 10 to 20: ");
            String userInput = scan.nextLine();
            System.out.println("Your choice is: " + userInput);
            int intUserInput = Integer.parseInt(userInput);
            if (intUserInput >= 10 && intUserInput <= 20) {
                wrongUserInput = false;
                board = new Board(intUserInput);
                System.out.println(board);
            } else {
                System.out.println("Only numbers from 10 to 20 are available.Try again.");
            }
        }
    }

    public Coordinates getStartingPosition() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the starting position:");
        String position = scanner.nextLine().toUpperCase();

        Coordinates coordinates;
        while ((coordinates = board.getValidPosition(position)) == null
                || !validateStartingPosition(coordinates)) {
            System.out.print("Wrong coordinates! Enter correct position: ");
            position = scanner.nextLine().toUpperCase();
        }

        return coordinates;
    }

    public Coordinates getEndingPosition() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ending position:");
        String position = scanner.nextLine().toUpperCase();
        Coordinates coordinates;
        while ((coordinates = board.getValidPosition(position)) == null) {
            System.out.print("Wrong coordinates! Enter correct position: ");
            position = scanner.nextLine().toUpperCase();
        }
        return coordinates;
    }

    public boolean validateStartingPosition(Coordinates coordinates) {
        Pawn pawn = board.getField(coordinates.getXCoordinate(), coordinates.getYCoordinate());
        if (pawn != null && pawn.getColor() == currentPlayer) {
            return true;
        }
        return false;
    }

    public void tryToMakeMove(){
        Coordinates coordinatesStart = getStartingPosition();
        Coordinates coordinatesEnd = getEndingPosition();
        Pawn pawn = board.getField(coordinatesStart.getXCoordinate(), coordinatesStart.getYCoordinate());
        while (!pawn.validateMove(board, coordinatesEnd.getXCoordinate(), coordinatesEnd.getYCoordinate())) {
            coordinatesEnd = getEndingPosition();
        }
        board.removePawn(coordinatesStart.getXCoordinate(), coordinatesStart.getYCoordinate());
        capturePawn(coordinatesStart, coordinatesEnd);
        pawn.setCoordinates(coordinatesEnd);
        board.setPawn(pawn);
        }

    private void capturePawn(Coordinates coordinatesStart, Coordinates coordinatesEnd) {
        if (Math.abs(coordinatesEnd.getXCoordinate() - coordinatesStart.getXCoordinate()) == 2) {
            board.removePawn((coordinatesStart.getXCoordinate() + coordinatesEnd.getXCoordinate()) / 2,
                    (coordinatesStart.getYCoordinate() + coordinatesEnd.getYCoordinate()) / 2);
        }
    }


    public boolean checkForWinner() {
        return false;
    }
}