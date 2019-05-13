package braingame;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Map;

public class MainScene {
    /**
     * Start main scene
     * @param primaryStage
     * @param neuronToSynapses
     */
    public static void start(Stage primaryStage, Map<Integer, Integer[][]> neuronToSynapses){
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        root.setStyle("-fx-background-color: linear-gradient(to top, #fdfcfb, #e2d1c3)");
        primaryStage.setScene(scene);
        primaryStage.show();
        new Controller().init(root, neuronToSynapses);
    }
}
