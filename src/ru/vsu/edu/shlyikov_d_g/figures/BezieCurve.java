package ru.vsu.edu.shlyikov_d_g.figures;

import ru.vsu.edu.shlyikov_d_g.utils.math.Combinations;
import ru.vsu.edu.shlyikov_d_g.DrawModule;
import ru.vsu.edu.shlyikov_d_g.utils.Point;
import ru.vsu.edu.shlyikov_d_g.utils.ExpressionCommander;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BezieCurve extends Figure {

    public BezieCurve(List<Point> coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public BezieCurve(ExpressionCommander expressionCommander, int startX2, int startY2, double size, double numberSize, double max){
        super(expressionCommander, startX2, startY2, size, numberSize, max);
    }

    @Override
    public void draw(DrawModule drawModule) {
        List<Point> listPoint = new ArrayList<>();
        int size = getCoords().getMatrix().size();

        for (double t = 0; t < 1; t+=1.0/100) {
            List<Point> listPointo = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                List<java.lang.Double> list = getCoords().getMatrix().get(i);
                Combinations combinations = new Combinations(size - 1, i);
                long comb = combinations.solve();
                double multiply = comb * Math.pow(1 - t, size - 1 - i) * Math.pow(t, i);

                double x0 = list.get(0) * multiply;
                double y0 = list.get(1) * multiply;
                listPointo.add(new Point(x0, y0));
            }
            Point ans = sum(listPointo);
            listPoint.add(new Point(ans.getX() - startX, ans.getY() - startY));
//            System.out.println(listPoint);
//            System.out.println("I'M HERE!!!\nREACHING FAR ACROSS THESE NEW FRONTIERS\nWITH MY LIFE I FIGHT THIS FEAR");
        }
        Figure figure = new Figure(listPoint, startX, startY);
        figure.draw(drawModule);
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

    private Point sum(List<Point> list){
        Point mainPoint = new Point(0, 0);
        for (Point p:list) {
            mainPoint.setX(mainPoint.getX() + p.getX());
            mainPoint.setY(mainPoint.getY() + p.getY());
        }
        return mainPoint;
    }
}
