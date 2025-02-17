package fr.univ_amu.m1info.board_game_library;

import fr.univ_amu.m1info.board_game_library.Controller.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class OthelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        MenuController mainMenuController = new MenuController();
        mainMenuController.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args); // Application JavaFX démarrée une seule fois
    }


}
