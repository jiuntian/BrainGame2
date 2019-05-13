package sample;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    /**
     * this calculate the shortest path
     * @param source
     * Source ID
     * @param destination
     * Destination ID
     * @return
     * Path
     */
    public List<Vertex> calc(Vertex source, Vertex destination){
        DijkstraShortestPath shortestPath = new DijkstraShortestPath();
        shortestPath.computePath(source);
        //if not infinity (path not found), print shortest distance
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

            List<Vertex> path = shortestPath.getPath(destination);
            System.out.println("Shortest Path: " + path);

            for(int i=1; i<path.size();i++){
                path.get(i-1).decreaseSynapseLifetime(
                        path.get(i-1)
                        ,path.get(i)
                        ,new Integer(path.get(i-1).getName())-1
                        ,new Integer(path.get(i).getName())-1);
            }

            return shortestPath.getPath(destination);
        }
        else{
            System.out.println("No Path Available");
            return new ArrayList<>();//force the result to be empty list
        }
    }
}
