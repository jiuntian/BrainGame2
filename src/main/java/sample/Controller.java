package sample;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller{
    static Line[][] line;
    static Polygon[][] arrowHead;
    static Text[][] t;
    static ImageView[][] wrong;
    ArrayList<Circle> nodes = new ArrayList<>();

    Text descriptionText;
    Text informationText;

    TextField textField;
    TextField textField2;

    public Vertex[] init(Pane root, Map<Integer, Integer[][]> neuronToSynapses){
        Vertex[] a = inputGraph(root, neuronToSynapses);
        Label label1 = new Label("From:\t");
        textField = new TextField ();
        textField.setMaxWidth(36);
        HBox from = new HBox();
        from.getChildren().addAll(label1, textField);
        from.setSpacing(10);
        from.setLayoutX(20);
        from.setLayoutY(105);

        Label label2 = new Label("Dest:\t");
        textField2 = new TextField ();
        textField2.setMaxWidth(36);
        HBox to = new HBox();
        to.getChildren().addAll(label2, textField2);
        to.setSpacing(10);
        to.setLayoutX(20);
        to.setLayoutY(145);

        Button button = new Button("Make Search");
        button.setLayoutY(180.0);
        button.setLayoutX(20);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                inputQuery(a);
            }
        });
        descriptionText = new Text();
        descriptionText.setText("Click the button to start!");
        descriptionText.setFont(Font.font(18));
        descriptionText.setFill(Color.BLACK);
        descriptionText.setX(240);
        descriptionText.setY(40);
        informationText = new Text();
        informationText.setFont(Font.font(18));
        informationText.setFill(Color.BLACK);
        informationText.setX(20);
        informationText.setY(60);
        root.getChildren().addAll(button, from, to, descriptionText, informationText);
        return a;
    }

    private Vertex[] inputGraph(Pane root, Map<Integer, Integer[][]> neuronToSynapses){
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Program Start...\nEnter number of neurons");
//        int n = sc.nextInt();//number of neuron
//        while(n<2){
//            System.out.println("Neurons must more than or equal to 2");
//            n = sc.nextInt();
//        }
        int n = neuronToSynapses.size();

        Vertex[] neuron = new Vertex[n];
        for (int i = 0; i < n; i++) {
            neuron[i] = new Vertex(Integer.toString(i+1));//create neuron
            //gui
            Circle circle = new Circle(15);
            circle.setFill(Color.GRAY);
            Text text = new Text(""+(i+1));
            text.setFont(Font.font(17));
            text.setFill(Color.DARKORANGE);
            circle.setStroke(Color.DARKORANGE);
            text.layoutXProperty().bind(circle.centerXProperty().add(-text.getLayoutBounds().getWidth() / 2));
            text.layoutYProperty().bind(circle.centerYProperty().add(5));
            nodes.add(circle);
            root.getChildren().addAll(circle, text);
        }
        line = new Line[neuron.length][neuron.length];
        arrowHead = new Polygon[neuron.length][neuron.length];
        t = new Text[neuron.length][neuron.length];
        wrong = new ImageView[neuron.length][neuron.length];

        for(int i=0;i<nodes.size();i++){
            Circle circle = nodes.get(i);
            circle.setCenterX(400 + 250 * Math.cos(i * 2 * Math.PI / neuron.length - Math.PI * 2/3.0));
            circle.setCenterY(300 + 250 * (Math.sin(i * 2 * Math.PI / neuron.length - Math.PI * 2/3.0)));
        }

        for(int i=0;i<n;i++){
            //System.out.println("Configuring Neuron "+(i+1));
            Integer[][] currentNeuron = neuronToSynapses.get(i);
            //System.out.print("Number of synapse(s): ");
            //int m = sc.nextInt();//number of edge
            int m = currentNeuron.length;
            for(int j=0;j<m;j++){
//                System.out.println("Edge no "+(j+1));
//                System.out.print("Next neuron: ");
//                int nextNeuron = sc.nextInt()-1;
                int nextNeuron = currentNeuron[j][0]-1;
//                System.out.print("Distance: ");
//                int dist = sc.nextInt();
                int dist = currentNeuron[j][1];
//                System.out.print("Time needed: ");
//                int time = sc.nextInt();
                int time = currentNeuron[j][2];
//                int life = 100;//random number more than 10
//                while(life>10 || life<1) {
//                    System.out.println("Synapse Lifetime: ");
//                    life = sc.nextInt();
//                }
                int life = currentNeuron[j][3];
                neuron[i].addNeighbour(new Edge(dist, neuron[i],neuron[nextNeuron],time,life));
                addEdge(root, i, nextNeuron,"Time: "+time+"\nDist: "+dist + "\nLife: "+life);
            }
        }
        return neuron;
    }

    private void addEdge(Pane root, int u, int v, String weight){
        line[u][v] = new Line();
        Circle circleU, circleV;
        circleU = nodes.get(u);
        circleV = nodes.get(v);

        line[u][v].setStartX(circleU.getCenterX());
        line[u][v].setStartY(circleU.getCenterY());
        line[u][v].setEndX(circleV.getCenterX());
        line[u][v].setEndY(circleV.getCenterY());
        line[u][v].setStrokeWidth(3);

        //text information
        t[u][v] = new Text(weight);
        double arrowAngle = (Math.atan2(line[u][v].getEndY() - line[u][v].getStartY(), line[u][v].getEndX() - line[u][v].getStartX()));
        int onLeft = (v>u)? -90 : 0;
        t[u][v].setLayoutX((line[u][v].getEndX() - line[u][v].getStartX()) / 2 + line[u][v].getStartX()+onLeft);
        t[u][v].setLayoutY((line[u][v].getEndY() - line[u][v].getStartY()) / 2 + line[u][v].getStartY()-40);
        t[u][v].setFont(Font.font("verdana",FontWeight.EXTRA_BOLD,17));
        t[u][v].setFill(Color.LIGHTCORAL);

        //wrong icon
        Image image = new Image(this.getClass().getClassLoader().getResource("wrong.png").toString());
        ImageView imageView = new ImageView(image);
        wrong[u][v] = imageView;
        wrong[u][v].setX((line[u][v].getEndX() - line[u][v].getStartX()) / 2 + line[u][v].getStartX() -20);
        wrong[u][v].setY((line[u][v].getEndY() - line[u][v].getStartY()) / 2 + line[u][v].getStartY() -10);
        wrong[u][v].setVisible(false);

        //arrow
        double scale = 2.5;
        arrowHead[u][v] = new Polygon(-4.33 * scale, 2.5 * scale, 5.0 * scale, 0, -4.33 * scale, -2.5 * scale, -4.33 * scale, 2.5 * scale);
        arrowHead[u][v].setRotate(Math.toDegrees(arrowAngle));
        arrowHead[u][v].setLayoutX(line[u][v].getEndX() - 20 * Math.cos(arrowAngle));
        arrowHead[u][v].setLayoutY(line[u][v].getEndY() - 20 * Math.sin(arrowAngle));
        arrowHead[u][v].setFill(line[u][v].getStroke());
        root.getChildren().add(0, t[u][v]);
        root.getChildren().add(0, arrowHead[u][v]);
        root.getChildren().add(0, line[u][v]);
        if(!root.getChildren().contains(wrong[u][v]))
            root.getChildren().add(0, wrong[u][v]);
    }

    public void inputQuery(Vertex[] graph){
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter number of query");
//        int x = sc.nextInt();
//        for(int i=0;i<x;i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    singleQuery(graph, root);
//                }
//            }).start();
//        }
        singleQuery(graph);
    }
    public void singleQuery(Vertex[] graph) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter source and destination(s d):");
//        int src = sc.nextInt()-1;
//        int des = sc.nextInt()-1;
        //GUI input
        String text1 = textField.getText();
        String text2 = textField2.getText();
        if(!text1.equals("")&&!text2.equals("")) {
            int srcInput = new Integer(text1);
            int desInput = new Integer(text2);
            int src = srcInput - 1;
            int des = desInput - 1;
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
            resetPara(graph);
        }
    }

    private static void resetPara(Vertex... graph){
        for (Vertex vertex : graph) {
            vertex.setTime(Integer.MAX_VALUE);
            vertex.setDistance(Integer.MAX_VALUE);
            vertex.setVisited(false);
            vertex.setPredecessor(null);
        }
    }
}