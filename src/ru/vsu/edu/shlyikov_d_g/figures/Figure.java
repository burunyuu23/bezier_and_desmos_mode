package ru.vsu.edu.shlyikov_d_g.figures;

import ru.vsu.edu.shlyikov_d_g.Matrix;
import ru.vsu.edu.shlyikov_d_g.Point;
import ru.vsu.edu.shlyikov_d_g.parser.Element;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Figure extends Path2D.Float{
    int startX = 0;
    int startY = 0;
    private Matrix coords;

    public Figure(Matrix coords, int startX, int startY){
        this.startX = startX;
        this.startY = startY;
        this.coords = coords;
    }

    public Figure(List<ru.vsu.edu.shlyikov_d_g.Point> coords, int startX, int startY){
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

    public Figure(Element[] elements, int startX2, int startY2, double size, double numberSize, double max){
        this(startX2, startY2);
        List<Point> list = new ArrayList<>();

        double multiply = size/numberSize;

        double ac = elements[2].getA();
        double bc = elements[2].getB();
        double ax = elements[1].getA();
        double bx = elements[1].getB();
        double ay = elements[0].getA();
        double by = elements[0].getB();

        for (int i = -startX2; i < startX2; i++) {
            Point p = new Point(i, (int)(multiply*Math.pow(ax*multiply*Math.pow(i*1.0/multiply, bx)/multiply+ac,
                    1.0/by)/ay));
                list.add(p);
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
