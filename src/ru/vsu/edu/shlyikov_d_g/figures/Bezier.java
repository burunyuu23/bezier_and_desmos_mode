package ru.vsu.edu.shlyikov_d_g.figures;

import ru.vsu.edu.shlyikov_d_g.DrawModule;
import ru.vsu.edu.shlyikov_d_g.utils.ExpressionCommander;
import ru.vsu.edu.shlyikov_d_g.utils.Point;
import ru.vsu.edu.shlyikov_d_g.utils.matrix.Matrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bezier extends Figure{
    List<Integer> Mb1 = new ArrayList<>();
    List<Integer> Mb2 = new ArrayList<>();
    List<Integer> Mb3 = new ArrayList<>();
    List<Integer> Mb4 = new ArrayList<>();

    {
        Mb1.add(-1);
        Mb1.add(3);
        Mb1.add(-3);
        Mb1.add(1);

        Mb2.add(3);
        Mb2.add(-6);
        Mb2.add(3);
        Mb2.add(0);

        Mb3.add(-3);
        Mb3.add(3);
        Mb3.add(0);
        Mb3.add(0);

        Mb4.add(1);
        Mb4.add(0);
        Mb4.add(0);
        Mb4.add(0);
    }

    public Bezier(Matrix coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public Bezier(List<Point> coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public Bezier(int startX, int startY) {
        super(startX, startY);
    }

    public Bezier(ExpressionCommander expressionCommander, int startX2, int startY2, double size, double numberSize, double max) {
        super(expressionCommander, startX2, startY2, size, numberSize, max);
    }

//    public Bezier(List<Point> coords, int startX, int startY) {
//        this.startX = startX;
//        this.startY = startY;
//        this.coords = new Matrix();
//
//        double[] temp = new double[0];
//        int k = 0;
//
//        for (int i = 0; i < coords.size(); i++) {
//            this.coords.setSizeX(this.coords.getSizeX() + 1);
//            double[] vector = new double[]{startX+coords.get(i).getX(), startY+coords.get(i).getY(), 1};
//            if ((i + k - 1) % 4 == 0){
//                temp = new double[]{startX+coords.get(i).getX(), startY+coords.get(i).getY(), 1};
//            }
//            this.coords.addVector(vector);
//            if ((i + k + 1) % 4 == 0){
//                k++;
//                this.coords.addVector(temp);
//            }
//        }
//    }


    @Override
    public void draw(DrawModule drawModule) {
        List<Point> listPoint = new ArrayList<>();
        int size = getCoords().getMatrix().size();

        for (int i = 0; i < size - 3; i+=3) {
            for (double t = 0; t < 1; t+=1.0/100) {
                List<java.lang.Double> list1 = getCoords().getMatrix().get(i);
                List<java.lang.Double> list2 = getCoords().getMatrix().get(i+1);
                List<java.lang.Double> list3 = getCoords().getMatrix().get(i+2);
                List<java.lang.Double> list4 = getCoords().getMatrix().get(i+3);

//                double multiply = coxDeBoor(i == 0 ? 0 : degree/(i*1.0), t*(degree/(i+size*1.0)), t, degree);
                double multiply1 = matrixMult(t, Mb1);
                double multiply2 = matrixMult(t, Mb2);
                double multiply3 = matrixMult(t, Mb3);
                double multiply4 = matrixMult(t, Mb4);
//                System.out.println(multiply);

                double x0 = list1.get(0) * multiply1 + list2.get(0) * multiply2
                        + list3.get(0) * multiply3 + list4.get(0) * multiply4;
                double y0 = list1.get(1) * multiply1 + list2.get(1) * multiply2
                        + list3.get(1) * multiply3 + list4.get(1) * multiply4;
                listPoint.add(new Point(x0 - startX, y0 - startY));
            }
//            System.out.println(listPoint);
//            System.out.println("I'M HERE!!!\nREACHING FAR ACROSS THESE NEW FRONTIERS\nWITH MY LIFE I FIGHT THIS FEAR");
        }
        System.out.println(listPoint);
        Figure figure = new Figure(listPoint, startX, startY);
        figure.draw(drawModule);
    }

    private double matrixMult(double t, List<Integer> matrix){
        return Math.pow(t, 3) * matrix.get(0) + Math.pow(t, 2) * matrix.get(1) + t * matrix.get(2) + matrix.get(3);
    }

    public void drawBezier(DrawModule drawModule) {
        for (int i = 0; i < getCoords().getMatrix().size() - 1; i++) {
            List<java.lang.Double> list = getCoords().getMatrix().get(i);
            List<java.lang.Double> list2 = getCoords().getMatrix().get(i + 1);
            int x0 = list.get(0).intValue();
            int y0 = list.get(1).intValue();
            int x1 = list2.get(0).intValue();
            int y1 = list2.get(1).intValue();
//            System.out.println(new Point(x0, y0) + " + " + new Point(x1, y1));
//                    gr.drawOval(x0, y0, 1, 1);
            drawModule.drawLine(x0, y0, x1, y1, 10, Color.RED, 10);
        }
    }

    private ru.vsu.edu.shlyikov_d_g.utils.Point sum(List<ru.vsu.edu.shlyikov_d_g.utils.Point> list){
        ru.vsu.edu.shlyikov_d_g.utils.Point mainPoint = new ru.vsu.edu.shlyikov_d_g.utils.Point(0, 0);
        for (Point p:list) {
            mainPoint.setX(mainPoint.getX() + p.getX());
            mainPoint.setY(mainPoint.getY() + p.getY());
        }
        return mainPoint;
    }
}
