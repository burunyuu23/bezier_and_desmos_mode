package ru.vsu.edu.shlyikov_d_g.figures;

import ru.vsu.edu.shlyikov_d_g.Combinations;
import ru.vsu.edu.shlyikov_d_g.DrawModule;
import ru.vsu.edu.shlyikov_d_g.Matrix;
import ru.vsu.edu.shlyikov_d_g.Point;

import java.util.ArrayList;
import java.util.List;

public class BezieCurve extends Figure {

    public BezieCurve(List<Point> coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    @Override
    public void draw(DrawModule drawModule) {
        List<Point> listPoint = new ArrayList<>();
        int size = getCoords().getMatrix().size();

        for (double j = 0; j <= 1; j+=1.0/10) {
            List<Point> listPointo = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                List<java.lang.Double> list = getCoords().getMatrix().get(i);
                Combinations combinations = new Combinations(size - 1, i);
                long comb = combinations.solve();
                double multiply = comb * Math.pow(1 - j, size - 1 - i) * Math.pow(j, i);

                double x0 = list.get(0) * multiply;
                double y0 = list.get(1) * multiply;
                listPointo.add(new Point(x0, y0));
            }
            Point ans = sum(listPointo);
            listPoint.add(new Point(ans.getX() - startX, ans.getY() - startY));
            System.out.println(listPoint);
//            System.out.println("I'M HERE!!!\nREACHING FAR ACROSS THESE NEW FRONTIERS\nWITH MY LIFE I FIGHT THIS FEAR");
        }
        Figure figure = new Figure(listPoint, startX, startY);
        figure.draw(drawModule);
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
