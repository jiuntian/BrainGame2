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

    public void setLife(int life) {
        this.life = life;
    }

    public void decreaseLife(){
        this.life--;
    }

    public boolean isAlive(){
        return this.life>0;
    }

    public void delete(){
        startVertex=null;
        targetVertex=null;
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

    public void setWeight(int weight) {
        this.weight = weight;
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

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    public void setTargetVertex(Vertex targetVertex) {
        this.targetVertex = targetVertex;
    }

    @Override
    public String toString() {
        return startVertex.getName() + " to "+targetVertex.getName();
    }
}