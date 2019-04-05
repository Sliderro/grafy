import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

class Graph {
    private Vertex[] vertices;
    private ArrayList<Edge> edges;
    private ArrayList<Vertex> checkedVertices;

    Graph(int V){
        vertices = new Vertex[V];
        edges = new ArrayList<Edge>();
        checkedVertices = new ArrayList<Vertex>();
        for(int i=0; i<V; i++){
            vertices[i] = new Vertex(i+1);
        }
    }

    void addEdge(int v1, int v2, int c, double weight) {
        if (v1 == v2){
            //System.out.println("You cannot connect vertex with itself!");
            return;
        }
        addEdge(vertices[v1-1], vertices[v2-1],c,weight);
    }

    boolean checkConnection(){
        checkedVertices.add(vertices[0]);
        vertices[0].setChecked(true);
        int counter=0;
        while(counter<checkedVertices.size()){
            Vertex[] out = checkedVertices.get(counter).linkedVertices();
            for(Vertex v: out){
                if(!v.isChecked()){
                    checkedVertices.add(v);
                    v.setChecked(true);
                }
            }
            counter++;
        }
        for (Vertex v:vertices
             ) {
            v.setChecked(false);
        }
        if(vertices.length==checkedVertices.size()){
            //System.out.println("This graph is connected");
            return true;
        } else {
            //System.out.println("This graph is not connected");
            return false;
        }
    }

    private void addEdge(Vertex v1, Vertex v2, int c, double weight){
        for(Edge e: edges){
            if(e.exists(v1,v2)||e.exists(v2,v1)){
                //throw new EdgeAlreadyExistsException("Edge between these two vertices already exist");
            }
        }
        v1.addEdge(v2/*,weight*/);
        v2.addEdge(v1/*,weight*/);
        edges.add(new Edge(v1,v2,c,weight));
    }

    void removeEdge(int v1, int v2){
        removeEdge(vertices[v1-1], vertices[v2-1]);
    }

    private void removeEdge(Vertex v1, Vertex v2){
        if(v1.removeEdge(v2) && v2.removeEdge(v1)){
            for(int i=0; i<edges.size(); i++){
                if(edges.get(i).exists(v1,v2)||edges.get(i).exists(v2,v1)){
                    edges.remove(i);
                    break;
                }
            }
            //System.out.println("Removed edge");
        } else {
            //System.out.println("Edge between these two vertices did not exist.");
        }
    }


    void createPaths(){
        for(Vertex v: vertices){
            BFS(v);
        }
    }

    void BFS(Vertex vertex)
    {
        checkedVertices.add(vertex);
        vertex.setChecked(true);
        int counter=0;
        while(counter<checkedVertices.size()){
            Vertex[] out = checkedVertices.get(counter).linkedVertices();
            for(Vertex v: out){
                if(!v.isChecked()){
                    v.setPathParent(checkedVertices.get(counter));
                    checkedVertices.add(v);
                    v.setChecked(true);
                }
            }
            counter++;
        }
        edgePaths();
        checkedVertices.clear();
        for (Vertex v:vertices){
            v.setPathParent(null);
            v.setChecked(false);
        }
    }

    void edgePaths(){
        Vertex tmp;
        for(int i=1; i<checkedVertices.size();i++){
            tmp = checkedVertices.get(i);
            while(tmp.getPathParent()!=null){
                for (Edge e:edges) {
                    if(e.exists(tmp,tmp.getPathParent())){
                        e.addPath(checkedVertices.get(0),tmp);
                    }
                }
                tmp = tmp.getPathParent();
            }
        }
    }

    void printEdgePath(){
        for (Edge e:edges) {
            e.getPath();
        }
    }

    void setIntensity(int[][] intensity){
        int k = intensity.length;
        int l = intensity[0].length;
        for (Edge e:edges) {
            for (int i = 0; i < k ; i++) {
                for (int j = 0; j < l ; j++) {
                    e.addIntensity(vertices[i],vertices[j],intensity[i][j]);
                }
            }
        }
    }

    int getIntensity(){
        int max = 0;
        for (Edge e: edges) {
            try {
                if (e.getA()>max){
                    max = e.getA();
                }
            } catch (EdgeOverloadedException ex) {
                return -1;
            }
        }
        return max;
    }

    void test(){
        vertices[0].printVertices();
    }

    double calculateSum(double m){
        double sum = 0;
        for (Edge e: edges) {
            try {
                sum += (double)e.getA() / ( ((double)e.getC()) - (double)e.getA());
            } catch (EdgeOverloadedException ex) {
                return -1;
            }
        }
        return sum;
    }

    public void testReliability(){
        Random r = new Random();
        int counter = 0;
        while (counter<edges.size()){
            if(r.nextDouble()>edges.get(counter).getWeight()){
                //System.out.println("Connection between " + edges.get(counter).getSrc().getIndex() + " and " + edges.get(counter).getDest().getIndex() + " lost!");
                removeEdge(edges.get(counter).getSrc(),edges.get(counter).getDest());
            } else {
                counter++;
            }
        }
    }

    int pathAmountMax(){
        int max = 0;
        for (Edge e: edges){
            if(e.pathsAmount()>max){
                max = e.pathsAmount();
            }
        }
        return max;
    }

}