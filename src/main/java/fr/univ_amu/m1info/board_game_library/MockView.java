package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of the BoardGameView for testing purposes.
 */
public class MockView implements BoardGameView {

    private final Map<String, CellState> cells = new HashMap<>();
    private final Map<String, String> labeledElements = new HashMap<>();

    @Override
    public void updateLabeledElement(String id, String newText) {
        labeledElements.put(id, newText);
    }

    @Override
    public void updatePlayerLabels(String id1,String player1Name, int player1Score,String id2, String player2Name, int player2Score) {
        labeledElements.put(id1, player1Name+" : "+player1Score);
        labeledElements.put(id2,player1Score +" : "+player1Name);
    }

    @Override
    public void setCellColor(int row, int column, Color color) {
        getOrCreateCell(row, column).setColor(color);
    }

    @Override
    public void addShapeAtCell(int row, int column, Shape shape, Color color) {
        CellState cell = getOrCreateCell(row, column);
        cell.setShape(shape);
        cell.setShapeColor(color);
    }

    @Override
    public void removeShapesAtCell(int row, int column) {
        getOrCreateCell(row, column).clearShape();
    }

    public CellState getCell(int row, int column) {
        return cells.getOrDefault(row + "," + column, new CellState());
    }

    public String getLabeledElement(String id) {
        return labeledElements.getOrDefault(id, null);
    }

    private CellState getOrCreateCell(int row, int column) {
        return cells.computeIfAbsent(row + "," + column, key -> new CellState());
    }

    /**
     * Represents the state of a cell in the mock view.
     */
    public static class CellState {
        private Color color = Color.GREEN; // Default color
        private Shape shape = null;
        private Color shapeColor = null;

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }

        public Color getShapeColor() {
            return shapeColor;
        }

        public void setShapeColor(Color shapeColor) {
            this.shapeColor = shapeColor;
        }

        public void clearShape() {
            this.shape = null;
            this.shapeColor = null;
        }
    }
}
