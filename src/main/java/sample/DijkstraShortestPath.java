package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraShortestPath {

    public void computeShortestPaths(Vertex sourceVertex){
        sourceVertex.setDistance(0);
        sourceVertex.setTime(0);
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceVertex);
        sourceVertex.setVisited(true);

        while(!priorityQueue.isEmpty()){
            // Getting the minimum distance vertex from priority queue
            Vertex actualVertex = priorityQueue.poll();
            for(Edge edge : actualVertex.getAdjacenciesList()){
                Vertex v = edge.getTargetVertex();
                if(!v.isVisited()&&edge.getTime()>-1)
                {
                    int newDistance = actualVertex.getDistance() + edge.getWeight();
                    int newTime = actualVertex.getTime() + edge.getTime();
                    //prioritise the time
                    if(newTime < v.getTime()) {
                        priorityQueue.remove(v);
                        v.setDistance(newDistance);
                        v.setTime(newTime);
                        v.setPredecessor(actualVertex);
                        priorityQueue.add(v);
                    }
                    else if(newTime == v.getTime()){
                        if(newDistance < v.getDistance()){
                            priorityQueue.remove(v);
                            v.setDistance(newDistance);
                            v.setTime(newTime);
                            v.setPredecessor(actualVertex);
                            priorityQueue.add(v);
                        }
                    }
                }
            }
            actualVertex.setVisited(true);
        }
    }

    public List<Vertex> getShortestPathTo(Vertex targetVertex){
        List<Vertex> path = new ArrayList<>();

        for(Vertex vertex=targetVertex; vertex!=null; vertex=vertex.getPredecessor()){
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }

}
