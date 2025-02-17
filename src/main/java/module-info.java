module fr.univ_amu.m1info.board_game_library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports fr.univ_amu.m1info.board_game_library;
    opens fr.univ_amu.m1info.board_game_library to javafx.fxml;
}
