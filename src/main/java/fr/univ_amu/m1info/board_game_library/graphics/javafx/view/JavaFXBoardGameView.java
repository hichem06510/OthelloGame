package fr.univ_amu.m1info.board_game_library.graphics.javafx.view;

import fr.univ_amu.m1info.board_game_library.graphics.BoardGameController;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.Toast;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.bar.Bar;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.board.BoardGridView;
import fr.univ_amu.m1info.board_game_library.graphics.Color;
import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class JavaFXBoardGameView implements BoardGameControllableView {
    private final Stage stage;
    private BoardGridView boardGridView;
    private Bar bar;
    private BoardGameController controller;

    // Labels for players
    private Label player1Label;
    private Label player2Label;

    // Player scores for highlighting
    private int player1Score;
    private int player2Score;

    public void setController(BoardGameController controller) {
        this.controller = controller;
    }

    public JavaFXBoardGameView(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(_ -> Platform.exit());
        stage.setResizable(false);
        stage.sizeToScene();
    }

    public synchronized void reset() {
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(10));

        // Top bar (players info + menu/close buttons)
        HBox topBar = createTopBar();
        mainContainer.getChildren().add(topBar);

        // Button bar with Undo/Redo
        HBox buttonBar = createButtonBar();
        mainContainer.getChildren().add(buttonBar);

        // Initialize the game board
        bar = new Bar();
        boardGridView = new BoardGridView();
        mainContainer.getChildren().add(boardGridView);

        Scene scene = new Scene(mainContainer);
        stage.setScene(scene);
    }


    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 0 0 1 0;");

        // Player labels
        player1Label = new Label("Player 1: 0");
        player1Label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 5;");

        player2Label = new Label("Player 2: 0");
        player2Label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 5;");

        // HBox for players
        HBox playerBox = new HBox(15, player1Label, player2Label);
        playerBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Close button with icon
        ImageView closeIconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/close_icon.png"))));
        closeIconView.setFitWidth(20);
        closeIconView.setFitHeight(20);
        Button closeButton = new Button();
        closeButton.setGraphic(closeIconView);
        closeButton.setOnAction(e -> Platform.exit());
        closeButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        // Home (main menu) button with icon
        ImageView homeIconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/home_icon.png"))));
        homeIconView.setFitWidth(20);
        homeIconView.setFitHeight(20);
        Button homeButton = new Button();
        homeButton.setGraphic(homeIconView);
        homeButton.setOnAction(e -> controller.buttonActionOnClick("mainMenu"));
        homeButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        HBox menuButtons = new HBox(10, homeButton, closeButton);
        menuButtons.setAlignment(Pos.CENTER_RIGHT);

        topBar.getChildren().addAll(playerBox, spacer, menuButtons);
        return topBar;
    }

    private HBox createButtonBar() {
        HBox buttonBar = new HBox(15);
        buttonBar.setPadding(new Insets(10));
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setStyle("-fx-background-color: #f8fff8; -fx-border-color: #ccc; -fx-border-width: 0 0 1 0;");

        // Undo button
        Button undoButton = new Button("Undo");
        undoButton.setOnAction(event -> controller.buttonActionOnClick("undo"));
        undoButton.setStyle("-fx-background-color: #e0e0e0; -fx-font-weight: bold; -fx-cursor: hand;");

        // Redo button
        Button redoButton = new Button("Redo");
        redoButton.setOnAction(event -> controller.buttonActionOnClick("redo"));
        redoButton.setStyle("-fx-background-color: #e0e0e0; -fx-font-weight: bold; -fx-cursor: hand;");

        buttonBar.getChildren().addAll(undoButton, redoButton);
        return buttonBar;
    }

    @Override
    public synchronized void updateLabeledElement(String id, String newText) {
        bar.updateLabel(id, newText);
        int toastMsgTime = 500;
        int fadeInTime = 300;
        int fadeOutTime = 300;
        //Toast.makeText(stage, newText, toastMsgTime, fadeInTime, fadeOutTime);
    }

    @Override
    public void updatePlayerLabels(String id1, String player1Name, int player1Score, String id2, String player2Name, int player2Score) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;

        player1Label.setText(player1Name + " : " + player1Score);
        player2Label.setText(player2Name + " : " + player2Score);

        // Styles
        String defaultStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 5;";
        String highlightStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #dff0d8; -fx-padding: 5;";

        if (player1Score > player2Score) {
            player1Label.setStyle(highlightStyle);
            player2Label.setStyle(defaultStyle);
        } else if (player2Score > player1Score) {
            player2Label.setStyle(highlightStyle);
            player1Label.setStyle(defaultStyle);
        } else {
            // If tied, both normal
            player1Label.setStyle(defaultStyle);
            player2Label.setStyle(defaultStyle);
        }
    }

    @Override
    public synchronized void setCellColor(int row, int column, Color color) {
        boardGridView.setColorSquare(row, column, color);
    }

    @Override
    public synchronized void addShapeAtCell(int row, int column, Shape shape, Color color) {
        boardGridView.addShapeAtSquare(row, column, shape, color);
    }

    @Override
    public synchronized void removeShapesAtCell(int row, int column) {
        boardGridView.removeShapesAtSquare(row, column);
    }

    public BoardGridView getBoardGridView() {
        return boardGridView;
    }

    public Stage getStage() {
        return stage;
    }

    public Bar getBar() {
        return bar;
    }

    public void buttonActionOnclick(String id) {
        controller.buttonActionOnClick(id);
    }

    public void boardActionOnclick(int row, int column) {
        controller.boardActionOnClick(row, column);
    }
}
