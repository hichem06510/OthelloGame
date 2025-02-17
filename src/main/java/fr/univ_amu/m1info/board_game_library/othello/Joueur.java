package fr.univ_amu.m1info.board_game_library.othello;

import fr.univ_amu.m1info.board_game_library.graphics.Color;

import java.util.Scanner;
import java.util.Objects;


public class Joueur {
    private final PionColor color;
    private final String name;
    private int score;
    private boolean bloque=false;


    public boolean isBloque() {
        return bloque;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public PionColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }



    public Joueur(PionColor color, String name) {
        this.color = color;
        this.name = name;
        this.score = 2;

    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Si les références sont égales
        if (obj == null || getClass() != obj.getClass()) return false;  // Si l'objet est nul ou d'un autre type

        Joueur joueur = (Joueur) obj;
        return this.getColor() == joueur.getColor() && this.getName().equals(joueur.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getColor(), getName());
    }

}
