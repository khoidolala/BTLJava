/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author tieulac305
 */
public class Sensor {
    int x,y;
    double r;

    public Sensor(int x, int y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getR() {
        return r;
    }
        
}
