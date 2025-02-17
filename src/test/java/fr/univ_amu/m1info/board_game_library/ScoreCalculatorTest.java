package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.othello.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreCalculatorTest {

    private Grid grid;
    private ScoreCalculator scoreCalculator;

    @BeforeEach
    void setUp() {
        // Initialisation de la grille et de la classe ScoreCalculator
        grid = new Grid(8); // Grille 8x8 (standard pour Othello)
        scoreCalculator = new ScoreCalculator(grid);

        // Configuration initiale des pions sur la grille
        grid.addPion(new Pion(new Position(3, 3), PionColor.WHITE));
        grid.addPion(new Pion(new Position(3, 4), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 3), PionColor.BLACK));
        grid.addPion(new Pion(new Position(4, 4), PionColor.WHITE));
    }

    @Test
    void testInitialScores() {
        // Test des scores initiaux après le placement des pions de départ
        int whiteScore = scoreCalculator.computeTotalScore(PionColor.WHITE);
        int blackScore = scoreCalculator.computeTotalScore(PionColor.BLACK);

        assertEquals(2, whiteScore, "Score initial  pour les pions blancs");
        assertEquals(2, blackScore, "Score initial  pour les pions noirs");
    }

    @Test
    void testEmptyGrid() {
        // Test avec une grille vide (sans pions)
        grid.clear(); // Vide tous les pions de la grille
        int whiteScore = scoreCalculator.computeTotalScore(PionColor.WHITE);
        int blackScore = scoreCalculator.computeTotalScore(PionColor.BLACK);

        assertEquals(0, whiteScore, "Le score des pions blancs sur une grille vide devrait être 0");
        assertEquals(0, blackScore, "Le score des pions noirs sur une grille vide devrait être 0");
    }

    @Test
    void testScoreAfterAddingPions() {
        // Ajout de nouveaux pions sur la grille
        grid.addPion(new Pion(new Position(2, 2), PionColor.BLACK));
        grid.addPion(new Pion(new Position(5, 5), PionColor.WHITE));


        int whiteScore = scoreCalculator.computeTotalScore(PionColor.WHITE);
        int blackScore = scoreCalculator.computeTotalScore(PionColor.BLACK);

        assertEquals(3, whiteScore, "Le score des pions blancs  après ajout");
        assertEquals(3, blackScore, "Le score des pions noirs  après ajout");
    }

}
