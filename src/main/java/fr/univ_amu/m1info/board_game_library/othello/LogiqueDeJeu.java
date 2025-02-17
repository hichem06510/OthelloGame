package fr.univ_amu.m1info.board_game_library.othello;


public class LogiqueDeJeu {

    private static final int SIZE = 8;
    private final Grid grid;

    // Constructor
    public LogiqueDeJeu(Grid grid) {
        this.grid = grid; // Use the grid passed from Jeu
    }

    public boolean isCellNull(Position pos) {
        return grid.getPion(pos.row(), pos.col()) == null;
    }

    // Check if a position is within the bounds of the grid
    public boolean isPositionValid(Position position) {
        int row = position.row();
        int col = position.col();
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    // Check if a pion can be placed at a given position (based on Othello rules)
    public boolean isPlaceable(Pion pion) {
        // The cell must be empty, and the move must result in capturing opponent's pions
        if (!isPositionValid(pion.getPosition()) || !isCellNull(pion.getPosition())) {
            return false;
        }

        // Check all 8 directions for possible captures
        PionColor opponentColor = (pion.getColor() == PionColor.BLACK) ? PionColor.WHITE : PionColor.BLACK;
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow != 0 || dCol != 0) { // Skip the (0, 0) direction
                    if (canCaptureInDirection(pion, opponentColor, dRow, dCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Check if there is a valid move for the given color
    public boolean hasValidMove(PionColor color) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Position position = new Position(row, col);
                Pion pion = new Pion(position, color);
                if (isPlaceable(pion)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check if a pion can capture opponent's pions in a specific direction
    public boolean canCaptureInDirection(Pion pion, PionColor opponentColor, int dRow, int dCol) {
        int row = pion.getPosition().row() + dRow;
        int col = pion.getPosition().col() + dCol;
        boolean hasOpponentPawn = false;

        // Traverse in the direction while within bounds and encountering valid pions
        while (isPositionValid(new Position(row, col)) && !isCellNull(new Position(row, col))) {
            Pion currentPion = grid.getPion(row, col);

            if (currentPion.getColor() == opponentColor) {
                hasOpponentPawn = true; // Found an opponent pion
            } else if (currentPion.getColor() == pion.getColor()) {
                return hasOpponentPawn; // Found a matching color after opponent pions
            } else {
                break; // Encountered an empty cell or invalid pion
            }

            // Move to the next cell in the direction
            row += dRow;
            col += dCol;
        }

        return false; // No valid capture found in this direction
    }

    public Grid getGrid() {
        return grid;
    }
}
