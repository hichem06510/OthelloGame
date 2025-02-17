package fr.univ_amu.m1info.board_game_library.othello;

import java.util.ArrayList;
import java.util.List;

public class Jeu {

    private LogiqueDeJeu gameLogic;
    private List<Pion> listPawnFlipped;
    private Joueur joueurActuelle;
    private List<Joueur> listeDeJoueur;
    private AIPlayer aiPlayer; // AIPlayer instance for AI-related logic

    public Jeu(Grid grid, String nom1, String nom2) {
        this.gameLogic = new LogiqueDeJeu(grid);
        this.listPawnFlipped = new ArrayList<>();

        Joueur joueur1 = new Joueur(PionColor.BLACK, nom1);
        Joueur joueur2 = new Joueur(PionColor.WHITE, nom2);

        this.joueurActuelle = joueur1;
        this.listeDeJoueur = new ArrayList<>();
        listeDeJoueur.add(joueur1);
        listeDeJoueur.add(joueur2);

        // Initialize AIPlayer with the current game instance
        this.aiPlayer = new AIPlayer(this);
    }

    public LogiqueDeJeu getGameLogic() {
        return gameLogic;
    }

    public void setGameLogic(LogiqueDeJeu gameLogic) {
        this.gameLogic = gameLogic;
    }

    public boolean areBothPlayersBlocked() {
        return listeDeJoueur.stream().allMatch(Joueur::isBloque);
    }

    public List<Pion> retournerPions(Pion pion) {
        listPawnFlipped.clear();
        PionColor couleurAdverse = (pion.getColor() == PionColor.BLACK) ? PionColor.WHITE : PionColor.BLACK;

        for (int dLigne = -1; dLigne <= 1; dLigne++) {
            for (int dColonne = -1; dColonne <= 1; dColonne++) {
                if (dLigne != 0 || dColonne != 0) {
                    if (gameLogic.canCaptureInDirection(pion, couleurAdverse, dLigne, dColonne)) {
                        retournerDansDirection(pion, dLigne, dColonne);
                    }
                }
            }
        }
        return listPawnFlipped;
    }

    private void retournerDansDirection(Pion pion, int dLigne, int dColonne) {
        int ligne = pion.getPosition().row() + dLigne;
        int colonne = pion.getPosition().col() + dColonne;

        while (gameLogic.isPositionValid(new Position(ligne, colonne)) &&
                !gameLogic.isCellNull(new Position(ligne, colonne)) &&
                gameLogic.getGrid().getPion(ligne, colonne).getColor() != pion.getColor()) {

            gameLogic.getGrid().getPion(ligne, colonne).flip();
            listPawnFlipped.add(gameLogic.getGrid().getPion(ligne, colonne));

            ligne += dLigne;
            colonne += dColonne;
        }
    }

    public int countFlippedPions(Position position, PionColor color) {
        int totalFlipped = 0;

        for (int dLigne = -1; dLigne <= 1; dLigne++) {
            for (int dColonne = -1; dColonne <= 1; dColonne++) {
                if (dLigne != 0 || dColonne != 0) {
                    totalFlipped += countFlippedInDirection(position, color, dLigne, dColonne);
                }
            }
        }

        return totalFlipped;
    }

    private int countFlippedInDirection(Position position, PionColor color, int dLigne, int dColonne) {
        int count = 0;
        int ligne = position.row() + dLigne;
        int colonne = position.col() + dColonne;

        while (gameLogic.isPositionValid(new Position(ligne, colonne)) &&
                !gameLogic.isCellNull(new Position(ligne, colonne)) &&
                gameLogic.getGrid().getPion(ligne, colonne).getColor() != color) {

            count++;
            ligne += dLigne;
            colonne += dColonne;
        }

        if (gameLogic.isPositionValid(new Position(ligne, colonne)) &&
                !gameLogic.isCellNull(new Position(ligne, colonne)) &&
                gameLogic.getGrid().getPion(ligne, colonne).getColor() == color) {
            return count;
        }

        return 0;
    }

    public void switchTurn() {
        int currentIndex = listeDeJoueur.indexOf(joueurActuelle);
        int nextIndex = (currentIndex + 1) % listeDeJoueur.size();
        joueurActuelle = listeDeJoueur.get(nextIndex);
    }

    public Joueur getJoueurActuelle() {
        return joueurActuelle;
    }

    public void passerTourAutomatique() {
        if (!gameLogic.hasValidMove(joueurActuelle.getColor())) {
            System.out.println("Le joueur " + joueurActuelle.getName() + " n'a aucun mouvement valide. Passage de tour !");
            switchTurn();
        }
    }

    public List<Joueur> getListeDeJoueur() {
        return listeDeJoueur;
    }

    public AIPlayer getAiPlayer() {
        return aiPlayer;
    }
}
