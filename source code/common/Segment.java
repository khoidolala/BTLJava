/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Vector;

/**
 *
 * @author tieulac305
 */
public class Segment {
    int w, h;
    Vector<Sensor> sensors;

    public Segment(int w, int h, Vector<Sensor> sensors) {
        this.w = w;
        this.h = h;
        this.sensors = sensors;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Vector<Sensor> getSensors() {
        return sensors;
    }

    @Override
    public String toString() {
        double r = sensors.elementAt(0).getR();
        String str = w + " " + h + " " + r + " " + sensors.size()
                + "\nTọa độ các cảm biến:";
        for(Sensor s: sensors){
            str += "\n" + s.getX() + " " + s.getY();
        }
        return str;
    }
    
    private void CalculateFlow(){ // that ra return ra 1 cai flow
        Algorithm al = new Algorithm();
        
        
    }
}
