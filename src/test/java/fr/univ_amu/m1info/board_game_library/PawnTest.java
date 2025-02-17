package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.othello.PionColor;
import fr.univ_amu.m1info.board_game_library.othello.Pion;
import fr.univ_amu.m1info.board_game_library.othello.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PawnTest {
    @Test
    void flipBlackToWhite() {
        Pion pion = new Pion(new Position(2, 3), PionColor.BLACK);
        pion.flip();
        assertEquals(PionColor.WHITE, pion.getColor());
    }

    @Test
    void flipWhiteToBlack() {
        Pion pion = new Pion(new Position(5, 5), PionColor.WHITE);
        pion.flip();
        assertEquals(PionColor.BLACK, pion.getColor());
    }
}
