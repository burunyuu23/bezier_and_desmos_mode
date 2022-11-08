package ru.vsu.edu.shlyikov_d_g;

import ru.vsu.edu.shlyikov_d_g.figures.Figure;

import java.util.ArrayList;
import java.util.List;

public class Transform {
    public static final double DEGREE_ROTATE_CLOCKWISE_90 = 90f;
    public static final double DEGREE_ROTATE_COUNTERCLOCKWISE_90 = -90f;
    public static final double DEGREE_ROTATE_CLOCKWISE_180 = 180f;
    public static final double DEGREE_ROTATE_COUNTERCLOCKWISE_180 = -180f;
    public static final double DEGREE_ROTATE_CLOCKWISE_45 = 45f;
    public static final double DEGREE_ROTATE_COUNTERCLOCKWISE_45 = -45f;
    public static final double X2_SCALE = 2;
    public static final double X_2_SCALE = 0.5;
    public static final double X3_SCALE = 3;
    public static final double X_3_SCALE = 0.3;

    private String nameTransformation;
    private List<Double> list = new ArrayList<>();
    private List<Point> pointList = new ArrayList<>();
    private Figure start;

    public Transform(Figure start){
        this.start = start;
    }

    public Transform(String nameTransformation, int x1, int y1, int x2, int y2){
        this.nameTransformation = nameTransformation;
        pointList.add(new Point(x1, y1));
        pointList.add(new Point(x2, y2));
    }

    public void setStart(Figure figure){
        this.start = figure;
    }

    public Figure getStart(){
        return start;
    }

    public String getName(){
        return nameTransformation;
    }

    public List<Point> getPointList(){
        return pointList;
    }

    @Override
    public String toString() {
        switch (nameTransformation){
            case "Translate transform pos" ->{
                return nameTransformation + ": " + pointList.get(0) + " -> " + pointList.get(1) + ".";
            }
            case "Scale" ->{
                return nameTransformation + " X*" + list.get(0) + ", Y*" + list.get(0);
            }
            case "Scale X", "Scale Y" ->{
                return nameTransformation + "*" + list.get(0);
            }
            case "Rotate radians", "Vertical shift radians", "Horizontal shift radians" ->{
                return nameTransformation + " " + list.get(0) + "π";
            }
            case "Rotate degrees", "Vertical shift degrees", "Horizontal shift degrees" ->{
                return nameTransformation + " " + list.get(0) + "°";
            }
            case "Translate X","Translate Y" ->{
                return nameTransformation + "+" + pointList.get(0).getNonZero();
            }
            case "Translate" ->{
                return nameTransformation + " X+" + pointList.get(0).getX() + " Y+" + pointList.get(0).getY();
            }
            case "Shift radians" ->{
                return nameTransformation + " " + list.get(0) + "π, " + list.get(1) + "π";
            }
            case "Shift degrees" ->{
                return nameTransformation + " " + list.get(0) + "°, " + list.get(1) + "°" ;
            }
        }
        return "null";
    }

    public Figure transformAgain(){
        switch (nameTransformation){
            case "Scale" ->{
                return scale(1/list.get(0));
            }
            case "Scale X" ->{
                return scaleX(1/list.get(0));
            }
            case "Scale Y" ->{
                return scaleY(1/list.get(0));
            }
            case "Rotate radians" ->{
                return rotateRadians(-list.get(0));
            }
            case "Vertical shift radians" ->{
                return vertShiftRadians(-list.get(0));
            }
            case "Horizontal shift radians" ->{
                return horShiftRadians(-list.get(0));
            }
            case "Rotate degrees" ->{
                return rotateDegrees(-list.get(0));
            }
            case "Vertical shift degrees" ->{
                return vertShiftDegrees(-list.get(0));
            }
            case "Horizontal shift degrees" ->{
                return horShiftDegrees(-list.get(0));
            }
            case "Translate X" ->{
                return translateX(-pointList.get(0).getNonZero());
            }
            case "Translate Y" ->{
                return translateY(-pointList.get(0).getNonZero());
            }
            case "Translate" ->{
                return translate(-pointList.get(0).getX(), -pointList.get(0).getY());
            }
        }
        return translate(0,0);
    }

    public Figure transform(Matrix matrix){
        return new Figure(this.start.getCoords().multiply(matrix), this.start.getStartX(), this.start.getStartY());
    }

    public Figure translateX(int x){
        this.nameTransformation = "Translate X";
        pointList.add(new Point(x, 0));
        return translation(x,0);
    }

    public Figure translateY(int y){
        this.nameTransformation = "Translate Y";
        pointList.add(new Point(0, y));
        return translation(0,y);
    }

    public Figure translate(int x, int y){
        this.nameTransformation = "Translate";
        pointList.add(new Point(x, y));
        return translation(x,y);
    }

    private Figure translation(int x, int y){
        Matrix shiftMatrix = new Matrix(new ArrayList<>(), new double[][]
                {{1,0,0},
                 {0,1,0},
                 {x,y,1}}, 3);
        this.start.startTransform();
        this.start = transform(shiftMatrix);
        this.start.endTransform();
        return this.start;
    }

    public Figure horShiftRadians(double angle){
        this.nameTransformation = "Horizontal shift radians";
        list.add(angle);
        return shift(0, angle);
    }

    public Figure vertShiftRadians(double angle){
        this.nameTransformation = "Vertical shift radians";
        list.add(angle);
        return shift(angle, 0);
    }

    public Figure horShiftDegrees(double angle){
        this.nameTransformation = "Horizontal shift degrees";
        list.add(angle);
        return shift(0, Math.toRadians(angle));
    }

    public Figure vertShiftDegrees(double angle){
        this.nameTransformation = "Vertical shift degrees";
        list.add(angle);
        return shift(Math.toRadians(angle), 0);
    }

    private Figure shift(double alpha, double beta){
        Matrix shiftMatrix = new Matrix(new ArrayList<>(), new double[][]
                {{       1,       Math.tan(alpha),0},
                 {Math.tan(beta),       1,        0},
                 {       0,             0,        1}}, 3);
        this.start.startTransform();
        this.start = transform(shiftMatrix);
        this.start.endTransform();
        return this.start;
    }

    public Figure rotateDegrees(double angle){
        this.nameTransformation = "Rotate degrees";
        list.add(angle);
        return rotate(Math.toRadians(angle));
    }

    public Figure rotateRadians(double angle){
        this.nameTransformation = "Rotate radians";
        list.add(angle);
        return rotate(angle);
    }

    private Figure rotate(double angle){
        Matrix rotateMatrix = new Matrix(new ArrayList<>(), new double[][]
                {{ Math.cos(angle), Math.sin(angle), 0},
                 {-Math.sin(angle), Math.cos(angle), 0},
                 {       0,               0,         1}}, 3);
        this.start.startTransform();
        this.start = transform(rotateMatrix);
        this.start.endTransform();
        return this.start;
    }

    public Figure scaleX(double multiplyX){
        this.nameTransformation = "Scale X";
        list.add(multiplyX);
        return scale(multiplyX,1);
    }

    public Figure scaleY(double multiplyY){
        this.nameTransformation = "Scale Y";
        list.add(multiplyY);
        return scale(1,multiplyY);
    }

    public Figure scale(double multiply){
        this.nameTransformation = "Scale";
        list.add(multiply);
        return scale(multiply, multiply);
    }

    private Figure scale( double multiplyX, double multiplyY){
        Matrix mutilplyMatrix = new Matrix(new ArrayList<>(), new double[][]
                {{ multiplyX,    0,     0},
                 {     0  ,  multiplyY, 0},
                 {     0,        0,     1}}, 3);
        this.start.startTransform();
        this.start = transform(mutilplyMatrix);
        this.start.endTransform();
        return this.start;
    }
}
