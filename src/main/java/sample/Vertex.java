package sample;

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

    public void decreaseSynapseLifetime(Vertex source, Vertex target){
        int i=0;
        for(Edge synapse: getNeighbour()){
            if(synapse.getTargetVertex()==target && synapse.getStartVertex()==source){
                System.out.println("Found it to decrease");
                synapse.decreaseLife();
                if(synapse.getLife()==0){
                    System.out.println("delete the node");
                    synapse.setTime(Integer.MIN_VALUE);
                }
            }
            i++;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edge> getAdjacenciesList() {
        return adjacenciesList;
    }

    public void setAdjacenciesList(List<Edge> adjacenciesList) {
        this.adjacenciesList = adjacenciesList;
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
