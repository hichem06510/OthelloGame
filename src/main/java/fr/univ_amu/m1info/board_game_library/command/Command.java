package fr.univ_amu.m1info.board_game_library.command;

import fr.univ_amu.m1info.board_game_library.othello.Jeu;

public interface Command {
    void execute(Jeu jeu);
    void unexecute(Jeu jeu);
}
