package fr.univ_amu.m1info.board_game_library.othello;

public class ScoreCalculator {

    private final Grid grid;

    // Constructeur qui reçoit la grille
    public ScoreCalculator(Grid grid) {
        this.grid = grid;
    }

    /**
     * Calcule le score total pour une couleur donnée.
     *
     * @param color La couleur des pions à compter.
     * @return Le nombre total de pions de cette couleur sur la grille.
     */
    public int computeTotalScore(PionColor color) {
        int score = 0;
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                Pion pion = grid.getPion(row, col);
                if (pion != null && pion.getColor().equals(color)) {
                    score++;
                }
            }
        }
        return score;
    }
}
