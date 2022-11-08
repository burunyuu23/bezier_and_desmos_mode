package ru.vsu.edu.shlyikov_d_g.figures;

import ru.vsu.edu.shlyikov_d_g.Matrix;
import ru.vsu.edu.shlyikov_d_g.Point;

import java.util.ArrayList;
import java.util.List;

public class Cot extends Figure {
    public Cot(Matrix coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public Cot(List<Point> coords, int startX, int startY) {
        super(coords, startX, startY);
    }

    public Cot(int startX, int startY) {
        super(startX, startY);
    }

//    public Cot(List<Point> list, int startX, int startY, String current) {
//        super(list, startX, startY, current);
//    }

    public Cot(int startX, int startY, int multiply) {
        this(startX, startY);
        List<Point> list = new ArrayList<>();
        for (int i = -startX-multiply*10; i < startX+multiply*10; i++) {
            if ((int)(Math.sin((double)i/(multiply))*multiply) != 0) list.add(new Point(i, (int)(Math.cos((double)i/(multiply))*multiply)/(int)(Math.sin((double)i/(multiply))*multiply)));
        }
        for (int i = startX+multiply*10; i > -startX-multiply*10; i--) {
            if ((int)(Math.sin((double)i/(multiply))*multiply) != 0) list.add(new Point(i, (int)(Math.cos((double)i/(multiply))*multiply)/(int)(Math.sin((double)i/(multiply))*multiply)));
        }
        addVectors(list);
    }
}
