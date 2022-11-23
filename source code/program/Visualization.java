/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program;

import common.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author tieulac305
 */
public class Visualization extends JFrame {
    Segment s;
    int times;
    int cap[][];

    public Visualization(Segment s, int c[][], int t) {
        this.s = s;
        this.s = s;
        times = t;
        cap = c;
        initUI();
    }
    
    private void initUI() {
        final Surface surface = new Surface(s, cap, times);
        add(surface);

        setTitle("Minh họa các cảm biến");
        setSize(s.getW() * times + 50, s.getH() * times + 50);
        setLocationRelativeTo(null);
    }
    
    class Surface extends JPanel {
        Segment s;
        int times;
        int cap[][];
        public Surface(Segment s, int c[][], int t) {
            this.s = s;
            times = t;
            cap = c;
        }

        private void doDrawing(Graphics g) {
            //vẽ các điểm
            Algorithm al = new Algorithm();
            Graphics2D g2d = (Graphics2D) g;

            g2d.setPaint(Color.blue);
            
            Vector<Sensor> v = s.getSensors();

            for(Sensor sensor: s.getSensors()){
                g2d.drawOval(sensor.getX() * times, sensor.getY() * times, times , times);
            }
            
            
            // nối đồ thị
            int n = cap.length;
            for(int i=0 ; i<n ; i++){
                for(int j=i+1 ; j<n ; j++){
                    if(cap[i][j]>0){
                        if(i==0){
                            // System.out.println("a");
                            g2d.setPaint(Color.RED);
                            g2d.drawLine(0,s.getH()/2*times,
                                    v.elementAt(j-1).getX()* times,v.elementAt(j-1).getY()* times);
                        }
                        else if(j==n-1){
                            // System.out.println("b");
                            g2d.setPaint(Color.RED);
                            g2d.drawLine(v.elementAt(i-1).getX()* times,v.elementAt(i-1).getY()* times,
                                    s.getW()*times + 50,s.getH()/2*times);
                        }
                        else{
                            //System.out.println( i + " " + j);
                            g2d.setPaint(Color.BLUE);
                            g2d.drawLine(v.elementAt(i-1).getX()* times,v.elementAt(i-1).getY()* times,
                                    v.elementAt(j-1).getX()* times,v.elementAt(j-1).getY()* times);
                        }
                    }
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            doDrawing(g);
        }
    }
}
