package fr.univ_amu.m1info.board_game_library.Controller;

import fr.univ_amu.m1info.board_game_library.graphics.JavaFXBoardGameApplicationLauncher;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.BoardGameDimensions;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementConfiguration;
import fr.univ_amu.m1info.board_game_library.graphics.configuration.LabeledElementKind;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class MenuController {

    private String playerName1;
    private String playerName2;
    private String aiDifficulty;

    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }

    private void showMainMenu(Stage primaryStage) {
        VBox mainMenu = createStyledVBox();

        Label title = createTitleLabel("Othello - Menu Principal");
        Button newGameButton = createStyledButton("Nouvelle Partie");
        Button quitButton = createStyledButton("Quitter");

        newGameButton.setOnAction(event -> showGameModeMenu(primaryStage));
        quitButton.setOnAction(event -> System.exit(0));

        mainMenu.getChildren().addAll(title, newGameButton, quitButton);

        Scene scene = new Scene(mainMenu, 400, 300);
        primaryStage.setTitle("Othello - Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showGameModeMenu(Stage primaryStage) {
        VBox gameModeMenu = createStyledVBox();

        Label title = createTitleLabel("Choisissez le mode de jeu");
        Button singlePlayerButton = createStyledButton("Jouer contre IA");
        Button multiplayerButton = createStyledButton("Multijoueur");
        Button backButton = createStyledButton("Retour");

        singlePlayerButton.setOnAction(event -> showAIDifficultyMenu(primaryStage));
        multiplayerButton.setOnAction(event -> showMultiplayerSetup(primaryStage));
        backButton.setOnAction(event -> showMainMenu(primaryStage));

        gameModeMenu.getChildren().addAll(title, singlePlayerButton, multiplayerButton, backButton);

        Scene scene = new Scene(gameModeMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showAIDifficultyMenu(Stage primaryStage) {
        VBox aiDifficultyMenu = createStyledVBox();

        Label title = createTitleLabel("Choisissez la difficulté");
        Button easyButton = createStyledButton("Facile");
        Button hardButton = createStyledButton("Difficile");
        Button backButton = createStyledButton("Retour");

        easyButton.setOnAction(event -> {
            aiDifficulty = "Facile";
            showSinglePlayerSetup(primaryStage);
        });
        hardButton.setOnAction(event -> {
            aiDifficulty = "Difficile";
            showSinglePlayerSetup(primaryStage);
        });
        backButton.setOnAction(event -> showGameModeMenu(primaryStage));

        aiDifficultyMenu.getChildren().addAll(title, easyButton, hardButton, backButton);

        Scene scene = new Scene(aiDifficultyMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showSinglePlayerSetup(Stage primaryStage) {
        VBox playerSetupMenu = createStyledVBox();

        Label title = createTitleLabel("Entrez votre nom");
        TextField playerNameField = createStyledTextField();
        Button confirmButton = createStyledButton("Confirmer");
        Button backButton = createStyledButton("Retour");

        confirmButton.setOnAction(event -> {
            playerName1 = playerNameField.getText();
            launchGame(primaryStage, true);
        });
        backButton.setOnAction(event -> showAIDifficultyMenu(primaryStage));

        playerSetupMenu.getChildren().addAll(title, playerNameField, confirmButton, backButton);

        Scene scene = new Scene(playerSetupMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showMultiplayerSetup(Stage primaryStage) {
        VBox multiplayerSetupMenu = createStyledVBox();

        Label title = createTitleLabel("Entrez les noms des joueurs");
        TextField player1NameField = createStyledTextField();
        player1NameField.setPromptText("Joueur 1");
        TextField player2NameField = createStyledTextField();
        player2NameField.setPromptText("Joueur 2");
        Button confirmButton = createStyledButton("Confirmer");
        Button backButton = createStyledButton("Retour");

        confirmButton.setOnAction(event -> {
            playerName1 = player1NameField.getText();
            playerName2 = player2NameField.getText();
            launchGame(primaryStage, false);
        });
        backButton.setOnAction(event -> showGameModeMenu(primaryStage));

        multiplayerSetupMenu.getChildren().addAll(title, player1NameField, player2NameField, confirmButton, backButton);

        Scene scene = new Scene(multiplayerSetupMenu, 400, 300);
        primaryStage.setScene(scene);
    }

    private void launchGame(Stage primaryStage, boolean isSinglePlayer) {
        BoardGameConfiguration config = new BoardGameConfiguration(
                "Othello Game",
                new BoardGameDimensions(8, 8),
                List.of(
                        new LabeledElementConfiguration(
                                playerName1 + ": 0",
                                "player1Label",
                                LabeledElementKind.TEXT
                        ),
                        new LabeledElementConfiguration(
                                (isSinglePlayer ? "IA" : playerName2) + ": 0",
                                "player2Label",
                                LabeledElementKind.TEXT
                        )
                )
        );

        OthelloController controller = new OthelloController(
                playerName1,
                isSinglePlayer ? "IA" : playerName2,
                isSinglePlayer,
                aiDifficulty
        );

        JavaFXBoardGameApplicationLauncher.getInstance().launchApplication(config, controller);
        primaryStage.hide(); // On ne ferme pas complètement pour pouvoir revenir plus tard si besoin
    }

    // Méthodes utilitaires pour le style
    private VBox createStyledVBox() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        // Dégradé d'arrière-plan
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #d3d3d3);"
                + "-fx-font-family: 'Arial';");
        return vbox;
    }

    private Label createTitleLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
        return label;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-radius: 20;"
                        + "-fx-background-color: #4CAF50;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-weight: bold;"
                        + "-fx-font-size: 14px;"
                        + "-fx-padding: 10 20 10 20;"
        );

        // Effet au survol
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-radius: 20;"
                        + "-fx-background-color: #45a049;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-weight: bold;"
                        + "-fx-font-size: 14px;"
                        + "-fx-padding: 10 20 10 20;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-radius: 20;"
                        + "-fx-background-color: #4CAF50;"
                        + "-fx-text-fill: white;"
                        + "-fx-font-weight: bold;"
                        + "-fx-font-size: 14px;"
                        + "-fx-padding: 10 20 10 20;"
        ));

        return button;
    }

    private TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-padding: 8; -fx-font-size:14px; -fx-background-radius: 5; -fx-background-color: #fafafa; -fx-border-color: #ccc;");
        textField.setMaxWidth(200);
        return textField;
    }
}
