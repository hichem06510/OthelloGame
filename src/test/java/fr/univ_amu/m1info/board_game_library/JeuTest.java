package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.othello.Joueur;
import fr.univ_amu.m1info.board_game_library.othello.PionColor;
import fr.univ_amu.m1info.board_game_library.othello.Grid;
import fr.univ_amu.m1info.board_game_library.othello.Jeu;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JeuTest {

    private Jeu jeu;
    private Joueur joueur1;
    private Joueur joueur2;

    @BeforeEach
    public void setUp() {
        joueur1 = new Joueur(PionColor.BLACK, "Player 1");
        joueur2 = new Joueur(PionColor.WHITE, "Player 2");
        jeu = new Jeu(new Grid(8), "Player 1", "Player 2");
// Assurez-vous que Grid soit correctement initialisé
    }

    @Test
    public void testPassageDeTourAutomatique() {
        // Vérifier que le joueur 1 commence
        assertEquals(joueur1, jeu.getJoueurActuelle());

        // Simuler un tour où le joueur 1 ne peut pas jouer (par exemple, aucun coup valide)
        jeu.passerTourAutomatique();
        assertEquals(joueur2, jeu.getJoueurActuelle());  // Le tour doit passer au joueur 2

        // Simuler un tour où le joueur 2 ne peut pas jouer (aucun coup valide)
        jeu.passerTourAutomatique();
        assertEquals(joueur1, jeu.getJoueurActuelle());  // Le tour doit revenir au joueur 1
    }
}