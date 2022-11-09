package ru.vsu.edu.shlyikov_d_g;

import java.awt.*;

public class DrawModule {
    private Graphics2D g;
    private double max;
    private double min;

    public DrawModule(Graphics2D g, double max, double min) {
        this.g = g;
        this.max = max;
        this.min = min;
    }

    public void drawLine(int x1, int y1, int x2, int y2){
        double dx,dy,steps,x,y,k;
        double xc,yc;
        boolean breakFlag = false;
        dx=x2-x1;
        dy=y2-y1;
        steps = Math.max(Math.abs(dx), Math.abs(dy));
        xc=(dx/steps);
        yc=(dy/steps);
        x=x1;
        y=y1;
        for(k=1;k<=steps;k++)
        {
            if (y > max+steps || y < min-steps) {
                if (!breakFlag) {
                    breakFlag = true;
                } else {
                    break;
                }
            }
            x+=xc;
            y+=yc;
            plot((int)x,(int)y);
        }
    }

    private void plot(int x, int y) {
            g.drawOval(x, y, 1, 1);
    }
}
