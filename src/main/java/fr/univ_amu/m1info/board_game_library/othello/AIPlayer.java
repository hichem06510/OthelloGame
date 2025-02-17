package fr.univ_amu.m1info.board_game_library.othello;

import java.util.*;

public class AIPlayer {
    private final Jeu jeu;
    private final Random random;

    public AIPlayer(Jeu jeu) {
        if (jeu == null) {
            throw new IllegalArgumentException("The game instance cannot be null.");
        }
        this.jeu = jeu;
        this.random = new Random();
    }

    public Position calculateRandomMove() {
        List<Position> validMoves = getValidMoves();
        return validMoves.isEmpty() ? null : validMoves.get(random.nextInt(validMoves.size()));
    }

    public Position calculateOptimizedMove() {
        List<Position> validMoves = getValidMoves();
        if (validMoves.isEmpty()) {
            return null;
        }

        Position bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Position move : validMoves) {
            int score = simulateMoveAndEvaluateScore(move);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public int simulateMoveAndEvaluateScore(Position position) {
        Grid gridCopy = jeu.getGameLogic().getGrid().copy();
        PionColor currentColor = jeu.getJoueurActuelle().getColor();

        Pion pion = new Pion(position, currentColor);
        gridCopy.addPion(pion);
        ScoreCalculator scoreCalculator = new ScoreCalculator(gridCopy);

        return scoreCalculator.computeTotalScore(currentColor);
    }

    public List<Position> getValidMoves() {
        List<Position> validMoves = new ArrayList<>();
        Grid grid = jeu.getGameLogic().getGrid();
        PionColor currentColor = jeu.getJoueurActuelle().getColor();

        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                Position position = new Position(row, col);
                Pion pion = new Pion(position, currentColor);

                if (jeu.getGameLogic().isPlaceable(pion)) {
                    validMoves.add(position);
                }
            }
        }

        return validMoves;
    }
}
