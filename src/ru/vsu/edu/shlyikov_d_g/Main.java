package ru.vsu.edu.shlyikov_d_g;

import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        double[][] list = {{0,-1,2},{2,1,1},{3,0,1},{3,7,1}};
        Matrix m = new Matrix(new ArrayList<>(), list, 4,3);
        double[][] list2 = {{3,1},{2,1},{1,0}};
        Matrix m1 = new Matrix(new ArrayList<>(), list2, 3,2);
        System.out.println(m);
        System.out.println(m1);

        System.out.println(m.multiply(m1));
        winMain();
    }

    public static void winMain(){
        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() { new FrameMain().setVisible(true); }
        });
    }
}