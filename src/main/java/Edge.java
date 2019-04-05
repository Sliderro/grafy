import java.util.ArrayList;

class Edge {
    private Vertex src;
    private Vertex dest;
    private int intensity;
    private int c;
    private double weight;
    private ArrayList<Vertex> sources;
    private ArrayList<Vertex> destinations;

    Edge(Vertex src, Vertex dest,int c,double weight){
        this.src = src;
        this.dest = dest;
        sources = new ArrayList<Vertex>();
        destinations = new ArrayList<Vertex>();
        intensity = 0;
        this.weight=weight;
        this.c = c;
    }

    Vertex getSrc() {
        return src;
    }

    Vertex getDest() {
        return dest;
    }

    boolean exists(Vertex src, Vertex dest){
        if(src == this.src && dest == this.dest){
            return true;
        }
        return false;
    }

    void addPath(Vertex src, Vertex dest){
        boolean add = true;
        if (sources.isEmpty()){
            sources.add(src);
            destinations.add(dest);
        }
        for (int i = 0; i < sources.size(); i++) {
            if(sources.get(i) == src && destinations.get(i) == dest){
                add = false;
            }
        }
        if(add){
            sources.add(src);
            destinations.add(dest);
        }
    }

    void getPath(){
        System.out.println("Edge " + this.src.getIndex() + "-" + this.dest.getIndex() + " contain paths:");
        for (int i=0; i<sources.size();i++){
            System.out.println("from " + sources.get(i).getIndex() + " to " + destinations.get(i).getIndex());
        }
    }

    void addIntensity(Vertex v1, Vertex v2, int intensity){
        for(int i=0;i<sources.size();i++){
            if(v1 == sources.get(i) && v2 == destinations.get(i)){
                this.intensity += intensity;
            }
        }
    }

    void setIntensity(int intensity){
        this.intensity = intensity;
    }

    int getA() throws EdgeOverloadedException {
        if(intensity>c){
            throw new EdgeOverloadedException("Edge got overloaded!");
        } else {
            return intensity;
        }
    }

    public int getC() {
        return c;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    int pathsAmount(){
        return sources.size();
    }
}
