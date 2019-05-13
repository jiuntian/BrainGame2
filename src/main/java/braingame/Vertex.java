package braingame;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {

    private String name;
    private List<Edge> adjacenciesList;
    private boolean visited;
    private Vertex predecessor;
    private int distance = Integer.MAX_VALUE;
    private int time = Integer.MAX_VALUE;

    public Vertex(String name) {
        this.name = name;
        this.adjacenciesList = new ArrayList<>();
    }

    public void addNeighbour(Edge edge) {
        this.adjacenciesList.add(edge);
    }

    public List<Edge> getNeighbour(){
        return this.adjacenciesList;
    }

    public void decreaseSynapseLifetime(Vertex source, Vertex target, int u, int v){
        for(Edge synapse: getNeighbour()){
            if(synapse.getTargetVertex()==target && synapse.getStartVertex()==source){
                //decrement of life
                synapse.decreaseLife();
                String[] oldText = Controller.t[u][v].getText().split("\n");
                String newText = oldText[0]+"\n"+oldText[1]+"\n"+"Life: "+synapse.getLife();
                Controller.t[u][v].setText(newText);
                if(synapse.getLife()<=0){
                    //debug in console
                    System.out.println("Node died");
                    //set the gui when synapse died
                    Controller.wrong[u][v].setVisible(true);
                    Controller.line[u][v].setStroke(Color.RED);
                    Controller.arrowHead[u][v].setFill(Color.RED);
                    //let the time to be minimum when synapse died
                    synapse.setTime(Integer.MIN_VALUE);//disabled the synapse

                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<Edge> getAdjacenciesList() {
        return adjacenciesList;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Vertex getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Vertex otherVertex) {
        //prioritize shortest time
        //if time same, compare distance
        if(this.time==otherVertex.time) {
            return Integer.compare(this.distance, otherVertex.getDistance());//dist
        }
        //if time not same, choose shortest time
        else {
            return Integer.compare(this.time, otherVertex.getTime());
        }
    }
}
