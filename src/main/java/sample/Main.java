package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    Map<Integer, Integer[][]> neuronToSynapses = new HashMap<>();
    @Override
    public void start(Stage primaryStage){
        int neurons = AskScene.start();
        for(int i=0;i<neurons;i++){
            int synapseCount = AskSynapseNumber.start(i+1);
            Integer[][] data = new Integer[synapseCount][4];

            for(int j=0;j<synapseCount;j++){
                AskSynapseDetail.start(j+1,primaryStage);
                data[j] = AskSynapseDetail.synapseData;
            }

            neuronToSynapses.put(i, data);
        }
        System.out.println(neuronToSynapses);
        MainScene.start(primaryStage, neuronToSynapses);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
