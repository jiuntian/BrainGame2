package sample;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class AskScene {

    static int resultInt = -1;

    public static int start(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of neurons");
        dialog.setHeaderText("Enter number of neurons");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(numberOfNeuron -> {
            resultInt = Integer.parseInt(numberOfNeuron);
        });

        System.out.println(resultInt);
        dialog.close();
        return resultInt;
    }
}
