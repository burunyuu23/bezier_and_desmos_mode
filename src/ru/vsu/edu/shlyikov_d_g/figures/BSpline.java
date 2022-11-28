package ru.vsu.edu.shlyikov_d_g.figures;

import ru.vsu.edu.shlyikov_d_g.DrawModule;
import ru.vsu.edu.shlyikov_d_g.utils.ExpressionCommander;
import ru.vsu.edu.shlyikov_d_g.utils.Point;
import ru.vsu.edu.shlyikov_d_g.utils.math.Combinations;
import ru.vsu.edu.shlyikov_d_g.utils.matrix.Matrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BSpline extends Figure{
    List<Integer> Ms1 = new ArrayList<>();
    List<Integer> Ms2 = new ArrayList<>();
    List<Integer> Ms3 = new ArrayList<>();
    List<Integer> Ms4 = new ArrayList<>();

    {
        Ms1.add(-1);
        Ms1.add(3);
        Ms1.add(-3);
        Ms1.add(1);

        Ms2.add(3);
        Ms2.add(-6);
        Ms2.add(0);
        Ms2.add(4);

        Ms3.add(-3);
        Ms3.add(3);
        Ms3.add(3);
        Ms3.add(1);

        Ms4.add(1);
        Ms4.add(0);
        Ms4.add(0);
        Ms4.add(0);
    }

    public BSpline(Matrix coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public BSpline(List<Point> coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public BSpline(int startX, int startY) {
        super(startX, startY);
    }

    public BSpline(ExpressionCommander expressionCommander, int startX2, int startY2, double size, double numberSize, double max) {
        super(expressionCommander, startX2, startY2, size, numberSize, max);
    }

    @Override
    public void draw(DrawModule drawModule) {
        List<Point> listPoint = new ArrayList<>();
        int size = getCoords().getMatrix().size();
        double k = 1.0/100;

            for (int i = 0; i < size - 3; i++) {
                for (double t = 0; t < 1; t+=k) {
                    List<java.lang.Double> list1 = getCoords().getMatrix().get(i);
                    List<java.lang.Double> list2 = getCoords().getMatrix().get(i + 1);
                    List<java.lang.Double> list3 = getCoords().getMatrix().get(i + 2);
                    List<java.lang.Double> list4 = getCoords().getMatrix().get(i + 3);

                    if (Math.abs(list1.get(0) - list2.get(0)) == 1 || Math.abs(list2.get(0) - list3.get(0)) == 1
                    || Math.abs(list3.get(0) - list4.get(0)) == 1) k = 1;
                    else k = 1.0/100;

//                double multiply = coxDeBoor(i == 0 ? 0 : degree/(i*1.0), t*(degree/(i+size*1.0)), t, degree);
                    double multiply1 = matrixMult(t, Ms1);
                    double multiply2 = matrixMult(t, Ms2);
                    double multiply3 = matrixMult(t, Ms3);
                    double multiply4 = matrixMult(t, Ms4);
//                System.out.println(multiply);

                    double x0 = list1.get(0) * multiply1 + list2.get(0) * multiply2
                            + list3.get(0) * multiply3 + list4.get(0) * multiply4;
                    x0 = 1 / 6.0 * x0 - startX;
                    double y0 = list1.get(1) * multiply1 + list2.get(1) * multiply2
                            + list3.get(1) * multiply3 + list4.get(1) * multiply4;
                    y0 = 1 / 6.0 * y0 - startY;
                    listPoint.add(new Point(x0, y0));
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

    private Point sum(List<Point> list){
        Point mainPoint = new Point(0, 0);
        for (Point p:list) {
            mainPoint.setX(mainPoint.getX() + p.getX());
            mainPoint.setY(mainPoint.getY() + p.getY());
        }
        return mainPoint;
    }

    private double coxDeBoor(double t_i, double t_i_k, double t, int degree){
        if (degree == 0){
            return t >= t_i && t < t_i + 1 ? 1 : 0;
        }
        return ((t_i_k - t_i) == 0 ? 0 : ((t - t_i)/(t_i_k - t_i))*coxDeBoor(t_i, t_i_k, t, degree - 1)) + ((t_i_k - t_i) == 0 ? 0 :((t_i_k + 1 - t)/(t_i_k - t_i))*
                coxDeBoor(t_i + 1, t_i_k + 1, t, degree - 1));
    }
}
