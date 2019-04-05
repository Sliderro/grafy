import java.util.Random;

public class Main {


    private static int V = 10;
    private static int[][] N = new int[10][10];
    private static int attempts = 10000;

    public static void main(String[] args) {
        System.out.format("%3s%4s%5s%7s%3s%7s%7s%7s%7s%7s%7s%7s%7s\n","min","max","c(e)","T_Max","m","G","OL%","Con%","Time%","G2","OL%","Con%","Time%");
        calculate(10,20,3200, 0.02, 32);
        calculate(10,15,3200,0.02,32);
        calculate(15,20,3200,0.02,32);
        calculate(10,20,4800, 0.02, 32);
        calculate(10,15,4800,0.02,32);
        calculate(15,20,4800,0.02,32);
        calculate(5,10,1600,0.02,32);
        calculate(5,7,1600,0.02,32);
        calculate(8,10,1600,0.02,32);
        calculate(10,20,3200, 0.03, 32);
        calculate(10,15,3200,0.03,32);
        calculate(15,20,3200,0.03,32);
        calculate(10,20,4800, 0.03, 32);
        calculate(10,15,4800,0.03,32);
        calculate(15,20,4800,0.03,32);
        calculate(5,10,1600,0.03,32);
        calculate(5,7,1600,0.03,32);
        calculate(8,10,1600,0.03,32);;
        calculate(10,20,3200, 0.01, 32);
        calculate(10,15,3200,0.01,32);
        calculate(15,20,3200,0.01,32);
        calculate(10,20,4800, 0.01, 32);
        calculate(10,15,4800,0.01,32);
        calculate(15,20,4800,0.01,32);
        calculate(5,10,1600,0.01,32);
        calculate(5,7,1600,0.01,32);
        calculate(8,10,1600,0.01,32);
        calculate(40,40,8960,0.02,32);
        calculate(20,20,4480,0.02,32);
        calculate(10,10,2240,0.02,32);
        calculate(5,5,1120,0.02,32);
        //calculate();
    }

    private static void calculate(int minPackage, int maxPackage, int c_e, double T_Max, int m){
        int failures1 = 0;
        int overload1 = 0;
        int connection1 = 0;
        int timeout1 = 0;
        int failures2 = 0;
        int overload2 = 0;
        int connection2 = 0;
        int timeout2 = 0;
        int g = 0;
        double T;
        double T2;
        double max1=0;
        double max2=0;
        Random r = new Random();
        for (int i = 0; i <attempts ; i++) {
            for(int k=0; k<V; k++){
                for (int j=0 ; j<V; j++){
                    N[k][j] = r.nextInt(maxPackage - minPackage + 1) + minPackage;
                    g += N[k][j];
                }
            }
            Graph G = new Graph(V);
            G.addEdge(1,2,c_e/m,0.95);
            G.addEdge(2,3,c_e/m,0.95);
            G.addEdge(3,4,c_e/m,0.95);
            G.addEdge(4,5,c_e/m,0.95);
            G.addEdge(5,1,c_e/m,0.95);
            G.addEdge(1,6,c_e/m,0.95);
            G.addEdge(2,7,c_e/m,0.95);
            G.addEdge(3,8,c_e/m,0.95);
            G.addEdge(4,9,c_e/m,0.95);
            G.addEdge(5,10,c_e/m,0.95);
            G.addEdge(6,8,c_e/m,0.95);
            G.addEdge(8,10,c_e/m,0.95);
            G.addEdge(10,7,c_e/m,0.95);
            G.addEdge(7,9,c_e/m,0.95);
            G.addEdge(9,6,c_e/m,0.95);
            G.testReliability();
            G.createPaths();
            G.setIntensity(N);
            T = 1.0/(double) g * G.calculateSum(m);
                if(G.getIntensity() == -1){
                    failures1++;
                    overload1++;
                } else if (!G.checkConnection()){
                    failures1++;
                    connection1++;
                } else if (T > T_Max){
                    failures1++;
                    timeout1++;
                }
            Graph G2 = new Graph(V);
            G2.addEdge(1,2,c_e/m,0.95);
            G2.addEdge(2,3,c_e/m,0.95);
            G2.addEdge(3,4,c_e/m,0.95);
            G2.addEdge(4,5,c_e/m,0.95);
            //G2.addEdge(5,1,0.95);
            G2.addEdge(5,6,c_e/m,0.95);
            G2.addEdge(6,7,c_e/m,0.95);
            G2.addEdge(7,8,c_e/m,0.95);
            G2.addEdge(8,9,c_e/m,0.95);
            G2.addEdge(9,1,c_e/m,0.95);
            for (int j = 1; j <=9; j++){
                G2.addEdge(j,10,c_e/m,0.95);
            }
            G2.testReliability();
            G2.createPaths();
            G2.setIntensity(N);
            T2 = 1.0/(double) g * G2.calculateSum(m);
                if(G2.getIntensity() == -1){
                    overload2++;
                    failures2++;
                } else if (!G2.checkConnection()){
                    connection2++;
                    failures2++;
                } else if (T2 > T_Max) {
                    timeout2++;
                    failures2++;
                }

            g=0;
        }
        double o1 = (double)overload1/failures1 * 100;
        double c1 = (double)connection1/failures1 * 100;
        double t1 = (double)timeout1/failures1 * 100;
        double o2 = (double)overload2/failures2 * 100;
        double c2 = (double)connection2/failures2 * 100;
        double t2 = (double)timeout2/failures2 * 100;
        System.out.format("%3d%4d%5d%7.4f%3d%6.2f%%%6.2f%%%6.2f%%%6.2f%%%6.2f%%%6.2f%%%6.2f%%%6.2f%%\n",minPackage,maxPackage,c_e,T_Max,m,
                (1-(double)failures1/attempts)*100,o1,c1,t1,
                (1-(double)failures2/attempts)*100,o2,c2,t2);
    }
}
