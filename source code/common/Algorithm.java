/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author tieulac305
 */
public class Algorithm {
//    public void GenerateData(double lambda, double l, double w, double r){
//        // gọi hàm generate poisson để tạo data
//    }
    
    public Segment GeneratePoints(int w, int h, double r, double lambda) {
        // sinh các điểm trên một đoạn
        //https://hpaulkeeler.com/poisson-point-process-simulation/
        Vector<Sensor> sensors = new Vector<>();
        Random rand = new Random();
        for (int i = 0; i < lambda * w * h; i++) {
            int x = rand.nextInt(w);
            int y = poisson(h/2);
            sensors.add(new Sensor(x, y, r));
        }
        
        return new Segment(w,h,sensors);
    }
    
    boolean AreConnected(Sensor s1, Sensor s2){
        double maxDis = s1.getR() + s2.getR();
        double sqrDis = (s1.getX()-s2.getX())*(s1.getX()-s2.getX()) + (s1.getY()-s2.getY()) * (s1.getY()-s2.getY());
        if(Math.sqrt(sqrDis) > maxDis){
            return false;
        }
        return true;
    }
    
    public int[][] GetGraph(Segment s){
        Vector<Sensor> v = s.getSensors();
        int n = v.size(); 
        int[][] g = new int[n+2][n+2]; // thêm 2 node s và t
        
        for(int i = 0 ; i < n ; i ++){
            if(v.elementAt(i).getX() < v.elementAt(i).getR()){
                g[0][i+1] = 1;
            }
        }
        for(int i = 0 ; i < n ; i ++){
            if(s.w - v.elementAt(i).getX() < v.elementAt(i).getR()){
                g[i+1][n+1] = 1;
            }
        }
        
        for(int i=0 ; i<n ; i++){
            for(int j=i+1 ; j<n ; j++){
                if(AreConnected(v.elementAt(i),v.elementAt(j))){
                    g[i+1][j+1] = 1;
                    //g[j+1][i+1] = 1;
                }
            }
        }
        
        return g;
    }
    
    public int ComputeBarriers(int cap[][]){
        // ComputeBarriers algorithm is used to find the number of disjoint barriers in the network.
        // tính số lượng rào rời rạc bằng luồng cực đại trên một đoạn
        int n = cap.length;
        return maxFlow(0,n,cap);
    }
    
    //public void ComputeAllBarriers(){
        // tính trên toàn dải, thuật toán chính
//        Divide-and-Conquer Algorithm to Construct Barrier Coverage
//    1. Divide the given (curly) strip into small segments interleaved by thin vertical strips. The length of each
//    vertical strip is w(n), the width of the original strip.
//    The width of each vertical strip is chosen to be of the
//    order log w(n) such that there exist Θ(log w(n)) disjoint barriers crossing the vertical strip according to
//    Theorem 1.
//    2. In each vertical strip, sensor nodes use ComputeBarriers to find all of the disjoint vertical barriers and
//    the horizontal barriers that connect the vertical barriers together. This computation is carried out in each
//    vertical strip independently.
//    3. For each strip segment, use ComputeBarriers to find
//    disjoint horizontal barriers intersecting the vertical barriers on both ends of the segment. This computation
//    is carried o
        
    //}
    
    private int poisson(double a) {
        Random rand = new Random();
        double limit = Math.exp(-a), prod = rand.nextDouble();
        int n;
        for (n = 0; prod >= limit; n++){
            prod *= rand.nextDouble();
        }
        return n;
    }
    
    
    private int maxFlow(int s, int t, int cap[][]) {
        int n = cap.length;

        int[] h = new int[n];
        h[s] = n - 1;

        int[] maxh = new int[n];

        int[][] f = new int[n][n];
        int[] e = new int[n];

        for (int i = 0; i < n; ++i) {
            f[s][i] = cap[s][i];
            f[i][s] = -f[s][i];
            e[i] = cap[s][i];
        }

        for (int sz = 0;;) {
            if (sz == 0) {
                for (int i = 0; i < n; ++i) {
                    if (i != s && i != t && e[i] > 0) {
                        if (sz != 0 && h[i] > h[maxh[0]]) {
                            sz = 0;
                        }
                        maxh[sz++] = i;
                    }
                }
            }
            if (sz == 0) {
                break;
            }
            while (sz != 0) {
                int i = maxh[sz - 1];
                boolean pushed = false;
                for (int j = 0; j < n && e[i] != 0; ++j) {
                    if (h[i] == h[j] + 1 && cap[i][j] - f[i][j] > 0) {
                        int df = Math.min(cap[i][j] - f[i][j], e[i]);
                        f[i][j] += df;
                        f[j][i] -= df;
                        e[i] -= df;
                        e[j] += df;
                        if (e[i] == 0) {
                            --sz;
                        }
                        pushed = true;
                    }
                }
                if (!pushed) {
                    h[i] = Integer.MAX_VALUE;
                    for (int j = 0; j < n; ++j) {
                        if (h[i] > h[j] + 1 && cap[i][j] - f[i][j] > 0) {
                            h[i] = h[j] + 1;
                        }
                    }
                    if (h[i] > h[maxh[0]]) {
                        sz = 0;
                        break;
                    }
                }
            }
        }

        int flow = 0;
        for (int i = 0; i < n; i++) {
            flow += f[s][i];
        }

        return flow;
    }
}
