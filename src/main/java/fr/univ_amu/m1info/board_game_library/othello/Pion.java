package fr.univ_amu.m1info.board_game_library.othello;


public class Pion {
    Position position;
    PionColor color;

    public Pion(Position position, PionColor color) {
        this.position = position;
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public PionColor getColor() {
        return color;
    }

    public void setColor(PionColor color) {
        this.color = color;
    }

    // Flip the current piece's color
    public void flip() {
        this.color = (this.color == PionColor.BLACK) ? PionColor.WHITE : PionColor.BLACK;
    }
}