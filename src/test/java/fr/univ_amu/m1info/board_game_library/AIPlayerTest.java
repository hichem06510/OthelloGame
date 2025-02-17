package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.othello.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AIPlayer class.
 */
public class AIPlayerTest {

    private Jeu jeu;
    private AIPlayer aiPlayer;
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(8);
        jeu = new Jeu(grid, "Player 1", "Player 2");
        aiPlayer = new AIPlayer(jeu);

        // Set up the initial Othello positions
        grid.addPion(new Pion(new Position(3, 3), PionColor.WHITE));
        grid.addPion(new Pion(new Position(3, 4), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 3), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 4), PionColor.WHITE));
    }

    @Test
    void testConstructor() {
        assertNotNull(aiPlayer, "AIPlayer instance should be created successfully.");

        assertThrows(IllegalArgumentException.class, () -> new AIPlayer(null),
                "AIPlayer constructor should throw IllegalArgumentException when given a null Jeu instance.");
    }

    @Test
    void testCalculateRandomMove() {
        Position randomMove = aiPlayer.calculateRandomMove();
        assertNotNull(randomMove, "AI should successfully calculate a random move.");

        // Ensure the move is valid
        List<Position> validMoves = aiPlayer.getValidMoves();
        assertTrue(validMoves.contains(randomMove), "The random move should be one of the valid moves.");
    }

    @Test
    void testCalculateOptimizedMove() {
        // Get the optimized move
        Position optimizedMove = aiPlayer.calculateOptimizedMove();
        assertNotNull(optimizedMove, "AI should calculate an optimized move.");

        // Ensure the optimized move is valid
        List<Position> validMoves = aiPlayer.getValidMoves();
        assertTrue(validMoves.contains(optimizedMove), "The optimized move should be one of the valid moves.");
    }

    @Test
    void testSimulateMoveAndEvaluateScore() {
        // Get valid moves
        List<Position> validMoves = aiPlayer.getValidMoves();
        assertFalse(validMoves.isEmpty(), "There should be valid moves to simulate.");

        // Simulate a move and check the score
        Position testMove = validMoves.get(0);
        int score = aiPlayer.simulateMoveAndEvaluateScore(testMove);
        assertTrue(score > 0, "Simulated score should be positive for a valid move.");
    }

    @Test
    void testGetValidMoves() {
        List<Position> validMoves = aiPlayer.getValidMoves();

        // Validate moves for AI (Black)
        assertTrue(validMoves.contains(new Position(2, 3)), "Position (2,3) should be a valid move.");
        assertTrue(validMoves.contains(new Position(3, 2)), "Position (3,2) should be a valid move.");
        assertTrue(validMoves.contains(new Position(4, 5)), "Position (4,5) should be a valid move.");
        assertTrue(validMoves.contains(new Position(5, 4)), "Position (5,4) should be a valid move.");
        assertEquals(4, validMoves.size(), "There should be exactly 4 valid moves at the start of the game.");
    }

    @Test
    void testCalculateRandomMoveNoMovesAvailable() {
        // Simulate a situation where no moves are available
        grid.clear(); // Clear the grid
        grid.addPion(new Pion(new Position(3, 3), PionColor.BLACK));
        grid.addPion(new Pion(new Position(3, 4), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 3), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 4), PionColor.BLACK));

        Position movePlayed = aiPlayer.calculateRandomMove();
        assertNull(movePlayed, "AI should not calculate a move when no valid moves are available.");
    }

    @Test
    void testCalculateOptimizedMoveNoMovesAvailable() {
        // Simulate a situation where no moves are available
        grid.clear();
        grid.addPion(new Pion(new Position(3, 3), PionColor.BLACK));
        grid.addPion(new Pion(new Position(3, 4), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 3), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 4), PionColor.BLACK));

        Position movePlayed = aiPlayer.calculateOptimizedMove();
        assertNull(movePlayed, "AI should not calculate a move when no valid moves are available.");
    }

    @Test
    void testSimulateMove() {
        // Simulate a scenario where a move does not improve the score
        grid.clear();
        grid.addPion(new Pion(new Position(3, 3), PionColor.BLACK));
        grid.addPion(new Pion(new Position(3, 4), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 3), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 4), PionColor.WHITE));

        List<Position> validMoves = aiPlayer.getValidMoves();
        assertFalse(validMoves.isEmpty(), "There should still be valid moves.");

        Position testMove = validMoves.get(0);
        int scoreBefore = new ScoreCalculator(grid).computeTotalScore(PionColor.BLACK);
        int simulatedScore = aiPlayer.simulateMoveAndEvaluateScore(testMove);

        assertNotEquals(
                scoreBefore, simulatedScore, "The score should not change if no pions are flipped.");
    }

    // Helper to print grid state
    private void printGridState() {
        System.out.println("Current Grid State:");
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                Pion pion = grid.getPion(row, col);
                System.out.print((pion != null ? pion.getColor() : ".") + " ");
            }
            System.out.println();
        }
    }
}
