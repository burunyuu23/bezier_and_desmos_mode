package ru.vsu.edu.shlyikov_d_g.utils.matrix;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    int sizeX;
    int sizeY;
    List<List<Double>> matrix;

    public Matrix(){
        this.matrix = new ArrayList<>();
        this.sizeX = 0;
        this.sizeY = 3;
    }

    public Matrix(int size){
        this.matrix = new ArrayList<>();
        this.sizeX = size;
        this.sizeY = size;
        for (int i = 0; i < this.sizeX; i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < this.sizeY; j++) {
                this.matrix.get(i).add(0.0);
            }
        }
    }

    public Matrix(int sizeX, int sizeY){
        this.matrix = new ArrayList<>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for (int i = 0; i < this.sizeX; i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < this.sizeY; j++) {
                this.matrix.get(i).add(0.0);
            }
        }
    }

    public Matrix(List<List<Double>> matrix, List<Double> list, int size){
        this.matrix = matrix;
        this.sizeX = size;
        this.sizeY = size;
        for (int i = 0; i < this.sizeX; i++) {
            this.matrix.add(list);
            for (int j = 0; j < this.sizeY; j++) {
                this.matrix.get(i).add(0.0);
            }
        }
    }

    public Matrix(List<List<Double>> matrix, List<Double> list, int sizeX, int sizeY){
        this.matrix = matrix;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for (int i = 0; i < this.sizeX; i++) {
            this.matrix.add(list);
            for (int j = 0; j < this.sizeY; j++) {
                this.matrix.get(i).add(0.0);
            }
        }
    }

    public Matrix(List<List<Double>> matrix, double[][] array, int size){
        this.matrix = matrix;
        this.sizeX = size;
        this.sizeY = size;
        for (int i = 0; i < array.length; i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < array.length; j++) {
                this.matrix.get(i).add(array[i][j]);
            }
        }
    }

    public Matrix(List<List<Double>> matrix, double[][] array, int sizeX, int sizeY){
        this.matrix = matrix;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for (int i = 0; i < array.length; i++) {
            this.matrix.add(new ArrayList<>());
            for (int j = 0; j < array[0].length; j++) {
                this.matrix.get(i).add(array[i][j]);
            }
        }
    }

    public Matrix(List<List<Double>> matrix, int size){
        this.matrix = matrix;
        this.sizeX = size;
        this.sizeY = size;
    }

    public Matrix(List<List<Double>> matrix, int sizeX, int sizeY){
        this.matrix = matrix;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public List<List<Double>> getMatrix(){
        return matrix;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "size=" + sizeX +
                ", matrix=" + matrix +
                '}';
    }

    public void addVector(double[] vector){
        this.matrix.add(new ArrayList<>());
        for (int j = 0; j < vector.length; j++) {
            this.matrix.get(matrix.size() - 1).add(vector[j]);
        }
    }

    private void setElement(int x, int y, double element){
        this.matrix.get(x).set(y, element);
    }

    private double getElement(int x, int y){
        return this.matrix.get(x).get(y);
    }

    public double determinant3x3(){
        if (this.sizeX != 3){
            System.out.println("size not equals 3!");
            return 0;
        }
        double ans = 0;
        
        double tempPlus;
        double tempMinus;

        for (int i = 0; i < this.sizeX; i++) {
            tempPlus = 1;
            tempMinus = -1;
            for (int j = this.sizeX - 1, k = 0; j >= 0 && k <= this.sizeX; j--, k++) {
                int a = i + k;

                if (a >= this.sizeX) a -= this.sizeX;

//                System.out.printf("plus= a{%d,%d} = %.2f\n",a,k, this.matrix.get(a).get(k));
//                System.out.printf("minus= a{%d,%d} = %.2f\n",a,j, this.matrix.get(a).get(j));

                tempPlus *= this.matrix.get(a).get(k);
                tempMinus *= this.matrix.get(a).get(j);
            }
            ans += tempPlus + tempMinus;
        }

        return ans;
    }

    public Matrix multiply(Matrix matrix){
        Matrix ans = new Matrix(this.getSizeX(), matrix.getSizeY());
        if (this.getSizeY() != matrix.getSizeX()){
            return new Matrix(3);
        }

        double temp;

        for (int i = 0; i < ans.getSizeX(); i++) {
            for (int j = 0; j < ans.getSizeY(); j++) {
                temp = 0;
                for (int k = 0; k < matrix.getSizeX(); k++) {
                    temp += this.getElement(i, k)*matrix.getElement(k, j);
                }
                ans.setElement(i, j, temp);
            }
        }
        return ans;
    }
}
