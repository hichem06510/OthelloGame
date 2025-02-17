package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.MockView;
import fr.univ_amu.m1info.board_game_library.command.PlacePionCommand;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.othello.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.univ_amu.m1info.board_game_library.Controller.OthelloController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OthelloController class using MockView.
 */
public class OthelloControllerTest {

    private OthelloController controller;
    private MockView mockView;

    @BeforeEach
    void setUp() {
        // Initialisation avec des valeurs par défaut pour le constructeur
        mockView = new MockView();
        controller = new OthelloController("Player 1", "Player 2", false, null);
        controller.initializeViewOnStart(mockView);
    }

    @Test
    void testInitializeGrid() {
        // Validate initial positions
        validatePion(3, 3, PionColor.WHITE);
        validatePion(3, 4, PionColor.BLACK);
        validatePion(4, 3, PionColor.BLACK);
        validatePion(4, 4, PionColor.WHITE);
    }

    @Test
    void testPlayerMoveValid() {
        controller.boardActionOnClick(2, 3);

        // Validate that the pion was added
        MockView.CellState cellState = mockView.getCell(2, 3);
        assertNotNull(cellState.getShape(), "The cell should contain a shape after a valid move.");
        assertEquals(Color.BLACK, cellState.getShapeColor(), "The shape color should match the player's color.");
    }

    @Test
    void testPlayerMoveInvalid() {
        // Attempt to move on an occupied cell
        controller.boardActionOnClick(3, 3);

        // Validate that the cell state did not change
        MockView.CellState cellState = mockView.getCell(3, 3);
        assertEquals(Color.GREEN, cellState.getColor(), "The cell color should remain unchanged for an invalid move.");
    }

    @Test
    void testUndoButton() {
        controller.boardActionOnClick(2, 3); // Valid move
        controller.buttonActionOnClick("undo"); // Undo the move

        // Validate the cell is cleared
        MockView.CellState cellState = mockView.getCell(2, 3);
        assertNull(cellState.getShape(), "The cell should be cleared after undo.");
    }

    @Test
    void testRedoButton() {
        controller.boardActionOnClick(2, 3); // Valid move
        controller.buttonActionOnClick("undo"); // Undo the move
        controller.buttonActionOnClick("redo"); // Redo the move

        // Validate the cell is restored
        MockView.CellState cellState = mockView.getCell(2, 3);
        assertNotNull(cellState.getShape(), "The cell should contain a shape after redo.");
        assertEquals(Color.BLACK, cellState.getShapeColor(), "The shape color should match the player's color.");
    }

    @Test
    void testHighlightPossibleMoves() {
        controller.highlightPossibleMoves();

        // Check for highlighted cells
        assertEquals(Color.LIGHTBLUE, mockView.getCell(2, 3).getColor(), "Position (2,3) should be highlighted as a possible move.");
        assertEquals(Color.LIGHTBLUE, mockView.getCell(3, 2).getColor(), "Position (3,2) should be highlighted as a possible move.");
        assertEquals(Color.LIGHTBLUE, mockView.getCell(4, 5).getColor(), "Position (4,5) should be highlighted as a possible move.");
        assertEquals(Color.LIGHTBLUE, mockView.getCell(5, 4).getColor(), "Position (5,4) should be highlighted as a possible move.");
    }


    private void validatePion(int row, int col, PionColor expectedColor) {
        MockView.CellState cellState = mockView.getCell(row, col);
        assertNotNull(cellState.getShape(), "Cell should contain a shape.");
        assertEquals(expectedColor == PionColor.BLACK ? Color.BLACK : Color.WHITE, cellState.getShapeColor(), "Shape color should match expected pion color.");
    }
}
