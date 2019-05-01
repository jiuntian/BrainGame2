package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Solution {
    public List<Vertex> calc(Vertex source, Vertex destination){
        DijkstraShortestPath shortestPath = new DijkstraShortestPath();
        shortestPath.computeShortestPaths(source);
        if(destination.getDistance()!= Integer.MAX_VALUE || destination.getTime()!=Integer.MAX_VALUE) {
            System.out.println("Minimum distance from "
                    + source.getName()
                    + " to "
                    + destination.getName()
                    + ": " + destination.getDistance());
            System.out.println("Minimum time from "
                    + source.getName()
                    + " to "
                    + destination.getName()
                    + ": " + destination.getTime());
            List<Vertex> path = shortestPath.getShortestPathTo(destination);
            System.out.println("Shortest Path: " + path);

            for(int i=1; i<path.size();i++){
                path.get(i-1).decreaseSynapseLifetime(path.get(i-1),path.get(i),i-1,i);
            }

            return shortestPath.getShortestPathTo(destination);
        }
        else{
            System.out.println("No Path Available");
            return new ArrayList<>();//force the result to be empty list
        }
    }
}
