package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class AskScene {

    static int resultInt = -1;

    /**
     * This function will initiate a dialog to ask user how many neurons
     * they wanted in simulator
     * @return
     */
    public static int start(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Number of neurons");
        dialog.setHeaderText("Enter number of neurons");

        //ensure input is valid
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField inputField = dialog.getEditor();
        BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalid(inputField.getText()), inputField.textProperty());
        okButton.disableProperty().bind(isInvalid);

        Optional<String> result = dialog.showAndWait();

        //if OK is clicked, then set resultInt to input value
        result.ifPresent(numberOfNeuron -> {
            resultInt = Integer.parseInt(numberOfNeuron);
        });

        dialog.close();
        return resultInt;
    }

    //check if input is valid
    public static boolean isInvalid(String input){
        if(StringUtils.isNumeric(input)) {
            int resultInt = Integer.parseInt(input);
            if (resultInt >= 2 && resultInt <= 20) {
                return false;
            }
        }
        return true;
    }
}
