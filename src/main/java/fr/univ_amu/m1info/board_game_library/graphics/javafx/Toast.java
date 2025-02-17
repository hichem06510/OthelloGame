package fr.univ_amu.m1info.board_game_library.graphics.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Toast {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Toast() {
        // Private constructor to prevent instantiation
    }

    public static void makeText(Stage ownerStage, String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay) {
        Stage toastStage = new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastMsg);
        text.setFont(Font.font("Verdana", 14));
        text.setFill(Color.WHITE);

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 10; -fx-background-color: rgba(50, 50, 50, 0.8); -fx-padding: 10px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);

        // Position the toast at the bottom center of the owner stage
        updateToastPosition(ownerStage, toastStage, root);

        // Add a listener to reposition the toast when the owner stage moves or resizes
        ownerStage.xProperty().addListener((observable, oldValue, newValue) -> updateToastPosition(ownerStage, toastStage, root));
        ownerStage.yProperty().addListener((observable, oldValue, newValue) -> updateToastPosition(ownerStage, toastStage, root));
        ownerStage.widthProperty().addListener((observable, oldValue, newValue) -> updateToastPosition(ownerStage, toastStage, root));
        ownerStage.heightProperty().addListener((observable, oldValue, newValue) -> updateToastPosition(ownerStage, toastStage, root));

        toastStage.show();

        Timeline fadeInTimeline = new Timeline(
                new KeyFrame(Duration.millis(fadeInDelay),
                        new KeyValue(root.opacityProperty(), 1))
        );

        fadeInTimeline.setOnFinished(event -> executorService.submit(() -> {
            try {
                Thread.sleep(toastDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                Timeline fadeOutTimeline = new Timeline(
                        new KeyFrame(Duration.millis(fadeOutDelay),
                                new KeyValue(root.opacityProperty(), 0))
                );
                fadeOutTimeline.setOnFinished(aeb -> toastStage.close());
                fadeOutTimeline.play();
            });
        }));

        fadeInTimeline.play();
    }

    private static void updateToastPosition(Stage ownerStage, Stage toastStage, StackPane root) {
        Bounds ownerBounds = ownerStage.getScene().getRoot().localToScreen(ownerStage.getScene().getRoot().getBoundsInLocal());

        double toastWidth = root.getWidth();
        double toastHeight = root.getHeight();

        double x = ownerBounds.getMinX() + (ownerBounds.getWidth() - toastWidth) / 2;
        double y = ownerBounds.getMaxY() - toastHeight - 20; // 20px padding from the bottom

        toastStage.setX(x);
        toastStage.setY(y-15);
    }
}
