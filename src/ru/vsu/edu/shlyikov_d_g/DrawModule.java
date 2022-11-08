package ru.vsu.edu.shlyikov_d_g;

import java.awt.*;

public class DrawModule {
    private Graphics2D g;

    public DrawModule(Graphics2D g){
        this.g = g;
    }


    public void drawLine(int x1, int y1, int x2, int y2){
        double dx,dy,steps,x,y,k;
        double xc,yc;
        dx=x2-x1;
        dy=y2-y1;
        steps = Math.max(Math.abs(dx), Math.abs(dy));
        xc=(dx/steps);
        yc=(dy/steps);
        x=x1;
        y=y1;
        for(k=1;k<=steps;k++)
        {
            x=x+xc;
            y=y+yc;
            plot((int)x,(int)y);
        }
    }

    private void plot(int x, int y) {
            g.drawOval(x, y, 2, 2);
    }
}
