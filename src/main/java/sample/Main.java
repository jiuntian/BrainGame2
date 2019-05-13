package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    //a key value pair to store all the neurons with their synapses
    Map<Integer, Integer[][]> neuronToSynapses = new HashMap<>();
    @Override
    public void start(Stage primaryStage){
        //start scene to ask number of neurons
        int neurons = AskScene.start();
        //loop through each neurons
        for(int i=0;i<neurons;i++){
            //ask number of synapse
            int synapseCount = AskSynapseNumber.start(i+1);
            Integer[][] data = new Integer[synapseCount][4];

            //ask detail of each synapse
            for(int j=0;j<synapseCount;j++){
                AskSynapseDetail.start(j+1,primaryStage);
                data[j] = AskSynapseDetail.synapseData;
            }
            //store the date we got from user
            neuronToSynapses.put(i, data);
        }
        //start the simulator
        MainScene.start(primaryStage, neuronToSynapses);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
