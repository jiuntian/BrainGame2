package sample;

public class Edge {

    private int weight;
    private int time;
    private Vertex startVertex;
    private Vertex targetVertex;
    private int life;

    public int getLife() {
        return life;
    }

    public void decreaseLife(){
        this.life--;
    }

    public Edge(int weight, Vertex startVertex, Vertex targetVertex, int time, int life) {
        this.weight = weight;
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
        this.time = time;
        this.life = life;
    }

    public int getWeight() {
        return weight;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    @Override
    public String toString() {
        return startVertex.getName() + " to "+targetVertex.getName();
    }
}