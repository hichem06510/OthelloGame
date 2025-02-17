package fr.univ_amu.m1info.board_game_library.command;

import fr.univ_amu.m1info.board_game_library.othello.*;

import java.util.List;

public class PlacePionCommand implements Command {
    private final Position position;
    private final PionColor playerColor;
    private Pion placedPion; // Reference to the placed pion
    private Pion[][] originalGridState; // Snapshot of the grid before execution

    public PlacePionCommand(Position position, PionColor playerColor) {
        this.position = position;
        this.playerColor = playerColor;
    }

    @Override
    public void execute(Jeu jeu) {
        // Take a snapshot of the grid before changes
        originalGridState = copyGrid(jeu.getGameLogic().getGrid().getPions());

        // Place the pion
        placedPion = new Pion(position, playerColor);
        jeu.getGameLogic().getGrid().addPion(placedPion);

        // Capture opponent pions
        jeu.retournerPions(placedPion);

        // Switch turn
        jeu.switchTurn();
    }

    @Override
    public void unexecute(Jeu jeu) {
        // Restore the grid to its original state
        Pion[][] currentGrid = jeu.getGameLogic().getGrid().getPions();
        for (int row = 0; row < currentGrid.length; row++) {
            for (int col = 0; col < currentGrid[row].length; col++) {
                currentGrid[row][col] = originalGridState[row][col];
            }
        }

        // Switch turn back to the previous player
        jeu.switchTurn();
    }

    /**
     * Creates a deep copy of the grid's current state.
     *
     * @param grid The original grid state.
     * @return A deep copy of the grid.
     */
    private Pion[][] copyGrid(Pion[][] grid) {
        int size = grid.length;
        Pion[][] copy = new Pion[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Pion pion = grid[row][col];
                if (pion != null) {
                    copy[row][col] = new Pion(pion.getPosition(), pion.getColor());
                }
            }
        }

        return copy;
    }
}
