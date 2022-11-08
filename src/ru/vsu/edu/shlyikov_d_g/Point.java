package ru.vsu.edu.shlyikov_d_g;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Point {
    private int x;
    private int y;
    private boolean xClosed = false;
    private boolean yClosed = false;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point(){
        this.x = 0;
        this.y = 0;
    }

    public boolean isxClosed() {
        return xClosed;
    }

    public void setxClosed(boolean xClosed) {
        this.xClosed = xClosed;
    }

    public boolean isyClosed() {
        return yClosed;
    }

    public void setyClosed(boolean yClosed) {
        this.yClosed = yClosed;
    }

    @Override
    public String toString() {
        return (String.format("(%d,%d)", x, y));
    }

    public int getNonZero(){
        return x == 0 ? y : x;
    }

    public static Point parsePoint(String str){
        List<Integer> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("-*\\d+")
                .matcher(str);
        while (m.find()) {
            allMatches.add(Integer.valueOf(m.group()));
        }
        if (allMatches.size() < 2){
            return new Point();
        }
        return new Point(allMatches.get(0), allMatches.get(1));
    }

    public static List<Point> parsePoints(String str){
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("(-*\\d+,-*\\d+)")
                .matcher(str);
        while (m.find()) {
            allMatches.add(m.group());
        }
        List<Point> list = new ArrayList<>();

        for (String s:allMatches) {
            list.add(Point.parsePoint(s));
        }
        return list;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
