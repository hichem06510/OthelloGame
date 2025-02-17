package fr.univ_amu.m1info.board_game_library.othello;

import fr.univ_amu.m1info.board_game_library.graphics.Color;
import javafx.css.Size;

public class Grid {

    public final Pion[][] pions;
    private final int size;

    public Grid(int size){
        this.size = size;
        pions = new Pion[size][size];
    }//

    // méthode pour obtenir un pion spécifique a une position donnée sur la grille

    public Pion getPion(int row, int column){
        return pions[row][column];

    }
    public void removePion(Position position) {
        pions[position.row()][position.col()] = null;
    }

    // méthode permet d'obtenir l'ensemble des pions présents sur le plateau
    public Pion[][] getPions(){
        return pions;
    }
    // méthode pour ajouter un nouveau pion sur le plateau de jeu
    public void addPion(Pion newPion){
        pions[newPion.position.row()][newPion.position.col()] = newPion;
    }


    public int countPions(PionColor color) {
        int count = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (pions[row][col] != null && pions[row][col].getColor() == color) count++;
            }
        }
        return count;
    }

    // méthode rertourne le nombre de colonnes du plateau
    public int getSize() {
        return pions[0].length;
    }



    /**
     * Clears all pions from the grid.
     */
    public void clear() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.pions[row][col] = null; // Assuming the grid is a 2D array of Pion or similar.
            }
        }
    }
    public Grid copy() {
        Grid copy = new Grid(size);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Pion pion = this.getPion(row, col);
                if (pion != null) {
                    // Crée une copie du pion et l'ajoute à la grille copiée
                    copy.addPion(new Pion(new Position(row, col), pion.getColor()));
                }
            }
        }
        return copy;
    }
    /**
     * Retourne la couleur d'un pion à une position donnée sur la grille.
     *
     * @param pion La pion à retourner.
.
     */
    public void flipPion(Pion pion) {
        Position position = pion.getPosition();
        Pion existingPion = getPion(position.row(), position.col());
        if (existingPion != null) {
            existingPion.setColor(existingPion.getColor() == PionColor.BLACK ? PionColor.WHITE : PionColor.BLACK);
        }
    }



}
