package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.othello.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {

    private LogiqueDeJeu gameLogic;
    private Grid grid;
    private static final int SIZE = 8;

    @BeforeEach
    void setUp() {
        grid = new Grid(SIZE);
        gameLogic = new LogiqueDeJeu(grid);
        initializeStandardPosition();
    }

    /**
     * Initializes the standard Othello starting position.
     */
    private void initializeStandardPosition() {
        addPion(3, 3, PionColor.WHITE);
        addPion(3, 4, PionColor.BLACK);
        addPion(4, 3, PionColor.BLACK);
        addPion(4, 4, PionColor.WHITE);
    }

    /**
     * Helper method to add a pion to the grid.
     */
    private void addPion(int row, int col, PionColor color) {
        grid.addPion(new Pion(new Position(row, col), color));
    }

    /**
     * Helper method to create a position and assert validity.
     */
    private void assertPositionValidity(int row, int col, boolean expected, String message) {
        assertEquals(expected, gameLogic.isPositionValid(new Position(row, col)), message);
    }

    /**
     * Helper method to check if a cell is null.
     */
    private void assertCellNullity(int row, int col, boolean expected, String message) {
        assertEquals(expected, gameLogic.isCellNull(new Position(row, col)), message);
    }

    /**
     * Helper method to check if a pion is placeable.
     */
    private void assertPlaceable(int row, int col, PionColor color, boolean expected, String message) {
        assertEquals(expected, gameLogic.isPlaceable(new Pion(new Position(row, col), color)), message);
    }

    /**
     * Test for position validity within and outside grid boundaries.
     */
    @Test
    void testIsPositionValid() {
        assertPositionValidity(0, 0, true, "Position (0,0) should be valid.");
        assertPositionValidity(7, 7, true, "Position (7,7) should be valid.");
        assertPositionValidity(-1, 0, false, "Position (-1,0) should be invalid.");
        assertPositionValidity(8, 8, false, "Position (8,8) should be invalid.");
    }

    /**
     * Test for cell nullity when the cell is empty or occupied.
     */
    @Test
    void testIsCellNull() {
        assertCellNullity(0, 0, true, "Position (0,0) should be empty.");
        assertCellNullity(3, 3, false, "Position (3,3) should be occupied by WHITE.");
    }

    /**
     * Test for pion placeability in various scenarios.
     */
    @Test
    void testIsPlaceable() {
        assertPlaceable(2, 3, PionColor.BLACK, true, "Position (2,3) should be placeable for BLACK.");
        assertPlaceable(0, 0, PionColor.BLACK, false, "Position (0,0) should not be placeable for BLACK.");
        assertPlaceable(3, 3, PionColor.BLACK, false, "Position (3,3) is already occupied.");
    }

    /**
     * Test for at least one valid move in the standard starting position.
     */
    @Test
    void testHasValidMove() {
        assertTrue(gameLogic.hasValidMove(PionColor.BLACK), "BLACK should have valid moves.");
        assertTrue(gameLogic.hasValidMove(PionColor.WHITE), "WHITE should have valid moves.");
    }

    /**
     * Test for no valid moves when the grid is filled.
     */
    @Test
    void testHasValidMoveWhenGridIsFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Position pos = new Position(row, col);
                if (gameLogic.isCellNull(pos)) {
                    addPion(row, col, PionColor.BLACK);
                }
            }
        }
        assertFalse(gameLogic.hasValidMove(PionColor.BLACK), "BLACK should have no valid moves on a full grid.");
    }

    /**
     * Test for capture functionality in various directions.
     */
    @Test
    void testCanCaptureInDirection() {
        addPion(3, 4, PionColor.WHITE);
        addPion(3, 5, PionColor.WHITE);
        addPion(3, 6, PionColor.BLACK);

        assertTrue(gameLogic.canCaptureInDirection(new Pion(new Position(3, 3), PionColor.BLACK), PionColor.WHITE, 0, 1),
                "Should capture horizontally to the right.");
    }

    /**
     * Test for non-capture scenarios in specified directions.
     */
    @Test
    void testCanCaptureInDirectionNoCapture() {
        assertFalse(gameLogic.canCaptureInDirection(new Pion(new Position(0, 0), PionColor.BLACK), PionColor.WHITE, 0, 1),
                "No capture should occur with no opponent pions.");
    }
}
