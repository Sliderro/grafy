import java.util.ArrayList;

class Vertex {
    private int index;
    private ArrayList<Vertex> vertices;
    private boolean checked;
    private Vertex pathParent;
    //private ArrayList<Double> weights;

    Vertex(int index){
        checked = false;
        this.index = index;
        vertices = new ArrayList<Vertex>();
        //weights = new ArrayList<Double>();
    }

    void addEdge(Vertex vertex/*, Double weight*/){
        vertices.add(vertex);
        //weights.add(weight);
    }

    boolean removeEdge(Vertex vertex){
        for (int i=0; i<vertices.size(); i++){
            if(vertices.get(i).getIndex() == vertex.getIndex()){
                vertices.remove(i);
                //weights.remove(i);
                return true;
            }
        }
        return false;
    }

    void printVertices(){
        for (Vertex vertex : vertices) {
            System.out.println(this.index + " is connected with " + vertex.getIndex()/* + " with weight: " + weights.get(i)*/);
        }
    }

    Vertex[] linkedVertices(){
        return vertices.toArray(new Vertex[0]);
    }

    public int getIndex() {
        return index;
    }

    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean bool){
        this.checked = bool;
    }

    public Vertex getPathParent() {
        return pathParent;
    }

    public void setPathParent(Vertex pathParent) {
        this.pathParent = pathParent;
    }
}
