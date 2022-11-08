package ru.vsu.edu.shlyikov_d_g.parser;

import java.util.HashMap;
import java.util.Map;

public class Element {
    // a*x^b
    double a = 0;
    double b = 0;

    public Element(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "a*" + getA() + "x^" + getB();
    }
}
