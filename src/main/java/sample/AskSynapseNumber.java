package sample;

import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class AskSynapseNumber {

    static int numberOfSynapse = 0;

    public static int start(int index) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of synapse");
        dialog.setHeaderText("Enter number of synapse for Neuron "+index);

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(data -> {
                numberOfSynapse = Integer.parseInt(data);
        });
        System.out.println(numberOfSynapse);
        dialog.close();
        return numberOfSynapse;

    }
}
