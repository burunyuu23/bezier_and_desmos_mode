package ru.vsu.edu.shlyikov_d_g.figures;

import ru.vsu.edu.shlyikov_d_g.Matrix;
import ru.vsu.edu.shlyikov_d_g.Point;

import java.util.ArrayList;
import java.util.List;

public class Tan extends Figure {
    public Tan(Matrix coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public Tan(List<Point> coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public Tan(int startX, int startY) {
        super(startX, startY);
    }

//    public Tan(List<Point> list, int startX, int startY, String current, int width) {
//        super(list, startX, startY, current, width);
//    }

    public Tan(int startX, int startY, int multiply) {
        this(startX, startY);
        List<Point> list = new ArrayList<>();
        for (int i = -startX-multiply*10; i < startX+multiply*10; i++) {
            list.add(new Point(i, (int)(Math.tan((double)i/(multiply))*multiply)));
        }
        for (int i = startX+multiply*10; i > -startX-multiply*10; i--) {
            list.add(new Point(i, (int)(Math.tan((double)i/(multiply))*multiply)));
        }
        addVectors(list);
    }
}
