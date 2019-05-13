package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

//Dijkstra algorithm
//https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

//Interesting story on his invention

//What is the shortest way to travel from Rotterdam to Groningen,
//in general: from given city to given city. It is the algorithm for the shortest path,
//which I designed in about twenty minutes. One morning I was shopping in Amsterdam
//with my young fiancée, and tired, we sat down on the café terrace to drink a cup of
//coffee and I was just thinking about whether I could do this, and I then designed
//the algorithm for the shortest path. As I said, it was a twenty-minute invention.
//In fact, it was published in ’59, three years later. The publication is still
//readable, it is, in fact, quite nice. One of the reasons that it is so nice was
//that I designed it without pencil and paper. I learned later that one of the
//advantages of designing without pencil and paper is that you are almost forced to
//avoid all avoidable complexities. Eventually that algorithm became, to my great
//amazement, one of the cornerstones of my fame.
//— Edsger Dijkstra

//https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-using-priority_queue-stl/

public class DijkstraShortestPath {

    /**
     * This method will compute the graph and find the path to all other neurons
     * @param source The source neuron
     */
    public void computePath(Vertex source){
        source.setDistance(0);
        source.setTime(0);

        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(source);
        source.setVisited(true);

        while(!pq.isEmpty()){
            // Getting the minimum distance vertex from priority queue
            Vertex currentMin = pq.poll();
            //loop through all the synapses
            for(Edge edge : currentMin.getAdjacenciesList()){
                Vertex v = edge.getTargetVertex();
                //if not visited and not died
                if(!v.isVisited()&&edge.getTime()>-1)
                {
                    int newDistance = currentMin.getDistance() + edge.getWeight();
                    int newTime = currentMin.getTime() + edge.getTime();
                    //prioritise the time
                    if(newTime < v.getTime()) {
                        pq.remove(v);
                        v.setDistance(newDistance);
                        v.setTime(newTime);
                        v.setPredecessor(currentMin);
                        pq.add(v);
                    }
                    else if(newTime == v.getTime()){
                        if(newDistance < v.getDistance()){
                            pq.remove(v);
                            v.setDistance(newDistance);
                            v.setTime(newTime);
                            v.setPredecessor(currentMin);
                            pq.add(v);
                        }
                    }
                }
            }
            currentMin.setVisited(true);
        }
    }

    /**
     * Get the shortest path
     * @param target target neurons
     * @return shortest path
     */
    public List<Vertex> getPath(Vertex target){
        List<Vertex> path = new ArrayList<>();

        for(Vertex v=target; v!=null; v=v.getPredecessor()){
            path.add(v);
        }

        Collections.reverse(path);
        return path;
    }

}
