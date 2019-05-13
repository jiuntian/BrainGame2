package braingame;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    //Initiate all the global variable for the class
    //all in static because should be same for whole program
    static CubicCurve[][] line;
    static Polygon[][] arrowHead;
    static Text[][] t;
    static ImageView[][] wrong;

    ArrayList<Circle> nodes = new ArrayList<>();

    //initiate text label for javafx scene
    Text descriptionText;
    Text informationText;

    //text input field
    TextField textField;
    TextField textField2;

    /**
     * This method initialise the scene, being called by MainScene class to generate mainScene
     *
     * @param root
     * root is the Pane from MainScene
     *
     * @param neuronToSynapses
     * neuronToSynapse is a key pair data structure where
     * key is Integer type
     * value is Integer[][] array
     */
    public void init(Pane root, Map<Integer, Integer[][]> neuronToSynapses) {
        //trying to get the input from neuronnToSynapse to a Vertex[] array
        //check this function doc to know more on what it does
        Vertex[] a = inputGraph(root, neuronToSynapses);

        //this is to get the Source from user
        //consist of one label and one textField
        Label label1 = new Label("From:\t");
        textField = new TextField();
        textField.setMaxWidth(36);
        //put them in a Horizontal Box
        HBox from = new HBox();
        //add label and textField in Hbox
        from.getChildren().addAll(label1, textField);
        //set its location coordinate and spacing
        from.setSpacing(10);
        from.setLayoutX(20);
        from.setLayoutY(105);

        //this is to get the Destination from user
        //consist of one label and one textField
        Label label2 = new Label("Dest:\t");
        textField2 = new TextField();
        textField2.setMaxWidth(36);
        //put them in a Horizontal Box
        HBox to = new HBox();
        //add label and TextField in HBox
        to.getChildren().addAll(label2, textField2);
        //set its location coordinate and spacing
        to.setSpacing(10);
        to.setLayoutX(20);
        to.setLayoutY(145);

        //add a make search button
        Button button = new Button("Send Message");
        //set its location
        button.setLayoutY(180.0);
        button.setLayoutX(20);
        //when this button is pressed, it will call inputQuery method
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                inputQuery(a);
            }
        });

        //this is Text that tell user information, ie: shortest path, path not found
        descriptionText = new Text();
        descriptionText.setText("Click the button to start!");
        descriptionText.setFont(Font.font(18));
        descriptionText.setFill(Color.BLACK);
        descriptionText.setX(240);
        descriptionText.setY(40);

        //This is text that tell user about this path information, distance and time
        informationText = new Text();
        informationText.setFont(Font.font(18));
        informationText.setFill(Color.BLACK);
        informationText.setX(20);
        informationText.setY(60);

        //add all into root Pane, which is this method's input param
        root.getChildren().addAll(button, from, to, descriptionText, informationText);
    }

    /**
     * This method is to draw the graph in the JavaFX scene
     * @param root
     * This is the scene we are going to draw on
     * @param neuronToSynapses
     * neuronToSynapse is a key pair data structure where
     * key is Integer type
     * value is Integer[][] array
     *
     * @return a Vertex[] array that contain all neurons and synapse information we have
     * entered
     */
    private Vertex[] inputGraph(Pane root, Map<Integer, Integer[][]> neuronToSynapses) {
        int n = neuronToSynapses.size();
        //initialise a Vertex[] array to store Vertexes
        Vertex[] neuron = new Vertex[n];

        //add circle n times, where n is number of neurons
        for (int i = 0; i < n; i++) {
            neuron[i] = new Vertex(Integer.toString(i + 1));//create neuron
            //gui

            //create circle
            //circle parameters
            Circle circle = new Circle(15);
            circle.setFill(Color.GRAY);
            //create text in circle
            Text text = new Text("" + (i + 1));
            text.setFont(Font.font(17));
            text.setFill(Color.DARKORANGE);
            circle.setStroke(Color.DARKORANGE);

            //text is in circle center
            text.layoutXProperty().bind(circle.centerXProperty().add(-text.getLayoutBounds().getWidth() / 2));
            text.layoutYProperty().bind(circle.centerYProperty().add(5));
            //add to nodes, which is an ArrayList of Circle
            nodes.add(circle);
            //add the Circle and Text we have just create in root Pane
            root.getChildren().addAll(circle, text);
        }

        //initiate data to be feed in GUI
        //each of them is in [neuron.length][neuron.length] sized
        //first size is for source
        //second size is for destination
        //if one synapse connect 1 to 2
        //then it is line[1][2]
        line = new CubicCurve[neuron.length][neuron.length];
        arrowHead = new Polygon[neuron.length][neuron.length];
        t = new Text[neuron.length][neuron.length];
        wrong = new ImageView[neuron.length][neuron.length];

        //position every circle in its position
        //this function make the neurons arranged in a circular
        for (int i = 0; i < nodes.size(); i++) {
            Circle circle = nodes.get(i);
            circle.setCenterX(400 + 250 * Math.cos(i * 2 * Math.PI / neuron.length - Math.PI * 2 / 3.0));
            circle.setCenterY(300 + 250 * (Math.sin(i * 2 * Math.PI / neuron.length - Math.PI * 2 / 3.0)));
        }

        //for each neurons,
        // now will read its information we have get from neuronToSynapses
        for (int i = 0; i < n; i++) {
            //first and foremost, we read the Integer[][] array which contain neuron data
            //from neuronToSynapse with key i
            //and stored then in currentNeuron
            Integer[][] currentNeuron = neuronToSynapses.get(i);
            //we get its number of synapse
            int m = currentNeuron.length;
            //we loop through the synapses
            for (int j = 0; j < m; j++) {
                //determined all the synapse information
                int nextNeuron = currentNeuron[j][0] - 1;
                int dist = currentNeuron[j][1];
                int time = currentNeuron[j][2];
                int life = currentNeuron[j][3];
                //add the synapse to neuron, by calling addNeighbour method
                neuron[i].addNeighbour(new Edge(dist, neuron[i], neuron[nextNeuron], time, life));
                //call addEdge method to draw the synapse
                addEdge(root, i, nextNeuron, "Time: " + time + "\nDist: " + dist + "\nLife: " + life);
            }
        }
        //return neuron
        return neuron;
    }

    /**
     * This method draw edge on the GUI
     * it take parameter Pane, source ID, destination ID, and weight(String to show on line)
     *
     * @param root
     *
     * Source ID
     * @param u
     *
     * Destination ID
     * @param v
     *
     * Information to show on line
     * @param weight
     */
    private void addEdge(Pane root, int u, int v, String weight) {
        //get circle from nodes
        Circle circleU, circleV;
        circleU = nodes.get(u);
        circleV = nodes.get(v);

        //draw the synapse
        line[u][v] = new CubicCurve();
        //make sure to and from synapse wont overlapped by making it curved
        int left = (v > u) ? -15 : 15;
        //set synapse start and end point
        line[u][v].setStartX(circleU.getCenterX());
        line[u][v].setStartY(circleU.getCenterY());
        line[u][v].setEndX(circleV.getCenterX());
        line[u][v].setEndY(circleV.getCenterY());

        //set curve point
        //here i put center
        line[u][v].setControlX1((circleU.getCenterX() + circleV.getCenterX()) / 2 + left);
        line[u][v].setControlY1((circleU.getCenterY() + circleV.getCenterY()) / 2);
        line[u][v].setControlX2((circleU.getCenterX() + circleV.getCenterX()) / 2 + left);
        line[u][v].setControlY2((circleU.getCenterY() + circleV.getCenterY()) / 2);

        //set color
        line[u][v].setStrokeWidth(2.5);
        line[u][v].setFill(null);
        line[u][v].setStroke(Color.BLACK);


        //text information
        t[u][v] = new Text(weight);
        double arrowAngle = (Math.atan2(line[u][v].getEndY() - line[u][v].getStartY(), line[u][v].getEndX() - line[u][v].getStartX()));
        int onLeft = (v > u) ? -90 : 0;
        t[u][v].setLayoutX((line[u][v].getEndX() - line[u][v].getStartX()) / 2 + line[u][v].getStartX() + onLeft);
        t[u][v].setLayoutY((line[u][v].getEndY() - line[u][v].getStartY()) / 2 + line[u][v].getStartY() - 40);
        t[u][v].setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, 17));
        t[u][v].setFill(Color.LIGHTCORAL);
        t[u][v].setVisible(false);


        //wrong icon
        Image image = new Image(this.getClass().getClassLoader().getResource("wrong.png").toString());
        ImageView imageView = new ImageView(image);
        wrong[u][v] = imageView;
        wrong[u][v].setX((line[u][v].getEndX() - line[u][v].getStartX()) / 2 + line[u][v].getStartX());
        wrong[u][v].setY((line[u][v].getEndY() - line[u][v].getStartY()) / 2 + line[u][v].getStartY());
        wrong[u][v].setVisible(false);

        //arrow
        double scale = 2.5;
        arrowHead[u][v] = new Polygon(-4.33 * scale, 2.5 * scale, 5.0 * scale, 0, -4.33 * scale, -2.5 * scale, -4.33 * scale, 2.5 * scale);
        arrowHead[u][v].setRotate(Math.toDegrees(arrowAngle));
        arrowHead[u][v].setLayoutX(line[u][v].getEndX() - 20 * Math.cos(arrowAngle));
        arrowHead[u][v].setLayoutY(line[u][v].getEndY() - 20 * Math.sin(arrowAngle));
        arrowHead[u][v].setFill(line[u][v].getStroke());

        //then put mouse cursor on the line
        //this listener will set the text to be visible
        //and change line color
        line[u][v].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                t[u][v].setVisible(true);
                line[u][v].setStroke(Color.ORANGE);
                arrowHead[u][v].setFill(Color.ORANGE);
            }
        });

        //when the mouse cursor is not on the line
        //this listener will set text to be hidden
        //changed back the line color
        line[u][v].setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                t[u][v].setVisible(false);
                //if the line is not died, show black
                //if died, show red
                if (wrong[u][v].isVisible()) {
                    line[u][v].setStroke(Color.RED);
                    arrowHead[u][v].setFill(Color.RED);
                } else {
                    line[u][v].setStroke(Color.BLACK);
                    arrowHead[u][v].setFill(Color.BLACK);
                }

            }
        });

        //add all into root
        root.getChildren().add(0, t[u][v]);
        root.getChildren().add(0, arrowHead[u][v]);
        root.getChildren().add(0, line[u][v]);
        if (!root.getChildren().contains(wrong[u][v]))
            root.getChildren().add(0, wrong[u][v]);
    }

    //this method will be called when the user clicked the send message button
    public void inputQuery(Vertex[] graph) {
        //GUI input
        String text1 = textField.getText();
        String text2 = textField2.getText();

        if (!text1.equals("") && !text2.equals("")) {
            int srcInput = new Integer(text1);
            int desInput = new Integer(text2);
            int src = srcInput - 1;
            int des = desInput - 1;
            //call Solution class to find the path
            List<Vertex> path = new Solution().calc(graph[src], graph[des]);

            //animation
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            String text = "Path: ";
            String info = "";
            for (Vertex location : path) {
                int loc = Integer.valueOf(location.getName());
                text += " -> " + loc;
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(nodes.get(loc - 1).fillProperty(), Color.YELLOW)));
            }

            //If no path available
            if (path.size() == 0)
                text = "No Path Available";
            else
                info = "Time Needed: " + path.get(path.size() - 1).getTime() + "\nDistance Travelled: " + path.get(path.size() - 1).getDistance();

            //reset back to original color
            for (Circle circle : nodes) {
                circle.setFill(Color.GRAY);
            }

            descriptionText.setText(text);
            informationText.setText(info);
            timeline.play();
            //reset the path "from current source"
            //to ensure next query of shortest path worked
            resetPara(graph);
        }
    }

    //reset the path "from current source"
    private static void resetPara(Vertex... graph) {
        for (Vertex vertex : graph) {
            vertex.setTime(Integer.MAX_VALUE);
            vertex.setDistance(Integer.MAX_VALUE);
            vertex.setVisited(false);
            vertex.setPredecessor(null);
        }
    }
}