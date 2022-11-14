package ru.vsu.edu.shlyikov_d_g.figures;

import ru.vsu.edu.shlyikov_d_g.DrawModule;
import ru.vsu.edu.shlyikov_d_g.utils.matrix.Matrix;
import ru.vsu.edu.shlyikov_d_g.utils.Point;
import ru.vsu.edu.shlyikov_d_g.utils.ExpressionCommander;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Figure extends Path2D.Float{
    protected int startX = 0;
    protected int startY = 0;
    protected Matrix coords;

    public void draw(DrawModule drawModule) {
        for (int i = 0; i < getCoords().getMatrix().size() - 1; i++) {
            List<java.lang.Double> list = getCoords().getMatrix().get(i);
            List<java.lang.Double> list2 = getCoords().getMatrix().get(i + 1);
            int x0 = list.get(0).intValue();
            int y0 = list.get(1).intValue();
            int x1 = list2.get(0).intValue();
            int y1 = list2.get(1).intValue();
//            if (Math.abs(x1-x0) > 1){
//                return;
//            }
//            System.out.println(new Point(x0, y0) + " + " + new Point(x1, y1));
//                    gr.drawOval(x0, y0, 1, 1);
            drawModule.drawLine(x0, y0, x1, y1);
        }
    }

    public Figure(){}

    public Figure(Matrix coords, int startX, int startY){
        this.startX = startX;
        this.startY = startY;
        this.coords = coords;
    }

    public Figure(List<Point> coords, int startX, int startY){
        this.startX = startX;
        this.startY = startY;
        this.coords = new Matrix();
        addVectors(coords);
    }

    public Figure(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
        this.coords = new Matrix();
    }

    public Figure(ExpressionCommander expressionCommander, int startX2, int startY2, double size, double numberSize, double max){
        this(startX2, startY2);
        List<Point> list = new ArrayList<>();

        double multiply = size/numberSize;
        boolean isSqrt = expressionCommander.isSqrt();

        for (int i = (int) -max ; i < max; i++) {
            Point p = new Point(i*(-1), (int)(expressionCommander.solve((i+0.00000001)/multiply)*multiply));
//            Point p = new Point(i, (int)(multiply*Math.pow(ax*multiply*Math.pow(i*1.0/multiply, bx)/multiply+ac,
//                    1.0/by)/ay));
            if (Math.abs(p.getY()) < 200000000) {
                if (isSqrt){
                    if (p.getY()!=0) {
                        if ((expressionCommander.solve((i - 1) / multiply) * multiply) == 0 ^ expressionCommander.solve((i + 1) / multiply) * multiply == 0) {
                            if ((expressionCommander.solve((i - 1) / multiply) * multiply) == 0) {
                                list.add(new Point((i - 1)*(-1), (int) (expressionCommander.solve((i - 1) / multiply) * multiply)));
                            }
                            list.add(p);
                            if ((expressionCommander.solve((i + 1) / multiply) * multiply) == 0) {
                                list.add(new Point((i + 1)*(-1), (int) (expressionCommander.solve((i + 1) / multiply) * multiply)));
                            }
                        } else {
                            list.add(p);
                        }
                    }
                }
                else {
                    list.add(p);
                }
            }
        }
//        System.out.println(list);
        addVectors(list);
    }

    public void startTransform(){
        for (int i = 0; i < getCoords().getSizeX(); i++) {
            getCoords().getMatrix().get(i).set(0, getCoords().getMatrix().get(i).get(0) - this.startX);
            getCoords().getMatrix().get(i).set(1, getCoords().getMatrix().get(i).get(1) - this.startY);
        }
    }

    public void endTransform(){
        for (int i = 0; i < getCoords().getSizeX(); i++) {
            getCoords().getMatrix().get(i).set(0, getCoords().getMatrix().get(i).get(0) + this.startX);
            getCoords().getMatrix().get(i).set(1, getCoords().getMatrix().get(i).get(1) + this.startY);
        }
    }

    public void drawLine(DrawModule drawModule) {
        for (int i = 0; i < getCoords().getMatrix().size() - 1; i++) {
            List<java.lang.Double> list = getCoords().getMatrix().get(i);
            List<java.lang.Double> list2 = getCoords().getMatrix().get(i + 1);
            int x0 = list.get(0).intValue();
            int y0 = list.get(1).intValue();
            int x1 = list2.get(0).intValue();
            int y1 = list2.get(1).intValue();
//            System.out.println(new Point(x0, y0) + " + " + new Point(x1, y1));
//                    gr.drawOval(x0, y0, 1, 1);
            drawModule.drawLine(x0, y0, x1, y1, 10, Color.LIGHT_GRAY, 3);
        }
    }

    private void addVectors(int[] x, int[] y){
        for (int i = 0; i < x.length; i++) {
            coords.setSizeX(coords.getSizeX() + 1);
            double[] vector = new double[]{startX+x[i], startY+y[i], 1};
            coords.addVector(vector);
        }
    }

    protected void addVectors(List<Point> list){
        for (int i = 0; i < list.size(); i++) {
            coords.setSizeX(coords.getSizeX() + 1);
            double[] vector = new double[]{startX+list.get(i).getX(), startY+list.get(i).getY(), 1};
            coords.addVector(vector);
        }
    }

    public Matrix getCoords(){
        return coords;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public Path2D getPath(){
        reset();

        for (int i = 0; i < getCoords().getSizeX(); i++) {
            try {
                if (i == 0) {
                    moveTo(getCoords().getMatrix().get(i).get(0), getCoords().getMatrix().get(i).get(1));
                } else {
                    lineTo(getCoords().getMatrix().get(i).get(0), getCoords().getMatrix().get(i).get(1));
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        closePath();
        return this;
    }

    public static Figure getFigure(String str, int startX, int startY, int[] x, int[] y){
        if (x.length != y.length){
            System.out.println("It isn't a " + str.toLowerCase(Locale.ROOT));
            return null;
        }
        Figure figure = new Figure(startX, startY);
        figure.addVectors(x, y);
        return figure;
}
}
