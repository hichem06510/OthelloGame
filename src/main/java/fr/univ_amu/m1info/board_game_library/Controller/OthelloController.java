package fr.univ_amu.m1info.board_game_library.Controller;

import fr.univ_amu.m1info.board_game_library.command.Command;
import fr.univ_amu.m1info.board_game_library.command.Invoker;
import fr.univ_amu.m1info.board_game_library.command.PlacePionCommand;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.BoardGameView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.othello.*;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.util.List;

public class OthelloController implements BoardGameController {
    private final Grid grid;
    private final Jeu jeu;
    private final AIPlayer aiPlayer;
    private final boolean isSinglePlayer;
    private final String aiDifficulty;
    private final Invoker invoker;
    private final ScoreCalculator scoreCalculator;
    private BoardGameView view;

    public OthelloController(String player1Name, String player2Name, boolean isSinglePlayer, String aiDifficulty) {
        this.jeu = new Jeu(new Grid(8), player1Name, isSinglePlayer ? "IA" : player2Name);
        this.grid = this.jeu.getGameLogic().getGrid();
        this.aiPlayer = new AIPlayer(jeu);
        this.isSinglePlayer = isSinglePlayer;
        this.aiDifficulty = aiDifficulty;
        this.invoker = new Invoker(jeu);
        this.scoreCalculator = new ScoreCalculator(grid);
        initializeGrid();
    }

    private void initializeGrid() {
        List<Pion> initialPions = List.of(
                new Pion(new Position(3, 3), PionColor.WHITE),
                new Pion(new Position(3, 4), PionColor.BLACK),
                new Pion(new Position(4, 3), PionColor.BLACK),
                new Pion(new Position(4, 4), PionColor.WHITE)
        );

        for (Pion pion : initialPions) {
            Command initializeCommand = new PlacePionCommand(pion.getPosition(), pion.getColor());
            invoker.executeCommand(initializeCommand);
        }
    }



    @Override
    public void initializeViewOnStart(BoardGameView view) {
        this.view = view;
        refreshBoard();
        updatePlayersDisplay();
        highlightPossibleMoves();
    }

    public void boardActionOnClick(int row, int column) {
        Position position = new Position(row, column);
        Pion pion = new Pion(position, jeu.getJoueurActuelle().getColor());

        if (grid.getPion(row, column) == null && jeu.getGameLogic().isPlaceable(pion)) {
            Command command = new PlacePionCommand(position, jeu.getJoueurActuelle().getColor());
            invoker.executeCommand(command);

            onMoveMade();
        }
    }

    public void buttonActionOnClick(String buttonId) {
        if (buttonId.equalsIgnoreCase("undo")) {
            System.out.println("undo button clicked");
            if (isSinglePlayer) {
                System.out.println("single");

                // Undo twice in AI mode to revert to the player's turn
                invoker.undo(); // Undo AI's move
                invoker.undo(); // Undo player's previous move
            } else {
                System.out.println("multiple");
                // Undo once in multiplayer mode
                invoker.undo();
            }
            onMoveMade(); // Refresh and update the game state
        } else if (buttonId.equalsIgnoreCase("redo")) {
            System.out.println("redo button clicked");
            if (isSinglePlayer) {
                // Redo twice in AI mode to go back to the AI's turn
                invoker.redo(); // Redo player's move
                invoker.redo(); // Redo AI's move
            } else {
                // Redo once in multiplayer mode
                invoker.redo();
            }
            onMoveMade(); // Refresh and update the game state
        }
    }

    private void onMoveMade() {
        refreshBoard();
        updatePlayersDisplay();

        if (isSinglePlayer && jeu.getJoueurActuelle().getName().equals("IA")) {
            clearHighlights();
            handleAITurn();
        } else {
            highlightPossibleMoves();
        }
    }

    private void handleAITurn() {
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(event -> {
            Position movePlayed;
            if ("Difficile".equalsIgnoreCase(aiDifficulty)) {
                movePlayed = aiPlayer.calculateOptimizedMove();
            } else {
                movePlayed = aiPlayer.calculateRandomMove();
            }

            if (movePlayed != null) {
                // Create and execute a command for the AI move
                Command aiCommand = new PlacePionCommand(movePlayed, jeu.getJoueurActuelle().getColor());
                invoker.executeCommand(aiCommand); // Save AI move to the command history

                onMoveMade();
            } else {
                jeu.getJoueurActuelle().setBloque(true);
                passerTourSiNecessaire();
            }
        });
        pause.play();
    }


    private void passerTourSiNecessaire() {
        if (jeu.areBothPlayersBlocked()) {
            terminerLeJeu();
        } else if (jeu.getJoueurActuelle().isBloque()) {
            jeu.switchTurn();
            if (jeu.getJoueurActuelle().getName().equals("IA")) {
                handleAITurn();
            } else {
                highlightPossibleMoves();
            }
        }
    }

    public void highlightPossibleMoves() {
        clearHighlights();
        PionColor currentColor = jeu.getJoueurActuelle().getColor();
        boolean hasValidMove = false;

        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                Position position = new Position(row, col);
                Pion pion = new Pion(position, currentColor);
                if (jeu.getGameLogic().isPlaceable(pion)) {
                    view.setCellColor(row, col, Color.LIGHTBLUE);
                    hasValidMove = true;
                }
            }
        }

        if (!hasValidMove) {
            jeu.getJoueurActuelle().setBloque(true);
            passerTourSiNecessaire();
        }
    }

    public void terminerLeJeu() {
        int scorePlayer1 = scoreCalculator.computeTotalScore(jeu.getListeDeJoueur().get(0).getColor());
        int scorePlayer2 = scoreCalculator.computeTotalScore(jeu.getListeDeJoueur().get(1).getColor());
        String winner;

        if (scorePlayer1 > scorePlayer2) {
            winner = jeu.getListeDeJoueur().get(0).getName() + " a gagné !";
        } else if (scorePlayer2 > scorePlayer1) {
            winner = jeu.getListeDeJoueur().get(1).getName() + " a gagné !";
        } else {
            winner = "Match nul !";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Jeu terminé");
        alert.setHeaderText(winner);
        alert.setContentText("Scores:\n" +
                jeu.getListeDeJoueur().get(0).getName() + ": " + scorePlayer1 + "\n" +
                jeu.getListeDeJoueur().get(1).getName() + ": " + scorePlayer2);
        alert.showAndWait();
    }

    private void clearHighlights() {
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                view.setCellColor(row, col, Color.GREEN);
            }
        }
    }

    private void refreshBoard() {
        for (int row = 0; row < grid.getSize(); row++) {
            for (int col = 0; col < grid.getSize(); col++) {
                Pion pion = grid.getPion(row, col);
                if (pion != null) {
                    view.addShapeAtCell(row, col, Shape.CIRCLE, mapPionColorToGraphicsColor(pion.getColor()));
                } else {
                    view.removeShapesAtCell(row, col);
                    view.setCellColor(row, col, Color.GREEN);
                }
            }
        }
    }

    private void updatePlayersDisplay() {
        for (Joueur joueur : jeu.getListeDeJoueur()) {
            joueur.setScore(scoreCalculator.computeTotalScore(joueur.getColor()));
        }

        view.updatePlayerLabels("player1Label", jeu.getListeDeJoueur().get(0).getName(), jeu.getListeDeJoueur().get(0).getScore(),
                "player2Label", jeu.getListeDeJoueur().get(1).getName(), jeu.getListeDeJoueur().get(1).getScore());
    }

    private Color mapPionColorToGraphicsColor(PionColor pionColor) {
        return pionColor == PionColor.BLACK ? Color.BLACK : Color.WHITE;
    }

    public Grid getGrid() {
        return grid;
    }
}
