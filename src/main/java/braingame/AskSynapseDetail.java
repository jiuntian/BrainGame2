package braingame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;


public class AskSynapseDetail {
    public static Integer[] synapseData;

    /**
     * Initiate the dialog
     * @param index
     * index of neuron currently input
     * @param primaryStage
     * primary stage is the Stage pass from Main class
     * @return
     */
    public static Integer[] start(int index, Stage primaryStage){
        synapseData = new Integer[4];
        //initialise dialog
        Dialog<Integer[]> dialog = new Dialog<>();
        dialog.setTitle("Detail of Synapse "+index);
        dialog.setHeaderText("Please enter synapse details");

        //create it as a grid pane
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5,12.5,13.5,14.5));
        pane.setVgap(5.5);

        pane.add(new Label("Destination neuron"),0,0);
        TextField destinationTextField = new TextField();
        pane.add(destinationTextField, 1, 0);

        pane.add(new Label("Distance"), 0, 1);
        TextField distanceTextField = new TextField();
        pane.add(distanceTextField, 1,1);

        pane.add(new Label("Travel time"), 0, 2);
        TextField timeTextField = new TextField();
        pane.add(timeTextField, 1, 2);

        pane.add(new Label("Synapse Lifetime"), 0, 3);
        TextField lifetimeTextField = new TextField();
        pane.add(lifetimeTextField, 1, 3);

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(pane);

        //when submit button pressed, return the input and end this method
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == submitButtonType){
                synapseData[0] = Integer.valueOf(destinationTextField.getText());
                synapseData[1] = Integer.valueOf(distanceTextField.getText());
                synapseData[2] = Integer.valueOf(timeTextField.getText());
                synapseData[3] = Integer.valueOf(lifetimeTextField.getText());
                return synapseData;
            }
            return null;
        });

        Optional<Integer[]> result = dialog.showAndWait();

        result.ifPresent(dataArray -> System.out.println("Get input"));

        return synapseData;
    }
}
