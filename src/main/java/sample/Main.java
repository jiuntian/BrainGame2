package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    static Vertex[] a;

    @Override
    public void start(Stage primaryStage){
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        root.setStyle("-fx-background-color: linear-gradient(to top, #fdfcfb, #e2d1c3)");
        primaryStage.setScene(scene);
        primaryStage.show();
        a = new Controller().init(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
