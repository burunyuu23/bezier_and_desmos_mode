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
        drawLine(x1,y1,x2,y2,1, Color.BLACK, 2);
    }

    public void drawArrow(int x1, int y1, int x2, int y2, int step){
        drawLine(x1,y1,x2,y2,step, Color.BLUE, 10);
    }

    public void drawLine(int x1, int y1, int x2, int y2, int step, Color color, int pointSize){
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
        for(k=1;k<=steps;k++, x+=xc, y+=yc)
        {
            if (x == x1 && y == y1 || k == steps) {
                g.setColor(color);
                plot((int) Math.round(x), (int) Math.round(y), pointSize);
            }
            if ((step == 1 || (k % step < step/2))) {
                plot((int) x, (int) y, 2);
                if (y > max + steps || y < min - steps) {
                    if (!breakFlag) {
                        breakFlag = true;
                    } else {
                        break;
                    }
                }
                g.setColor(color != Color.BLACK ? Color.LIGHT_GRAY : Color.BLACK);
            }
        }
    }

    private void plot(int x, int y, int pointSize) {
        int shift = pointSize/2;
            g.fillOval(x - shift, y - shift, pointSize, pointSize);
    }
}
