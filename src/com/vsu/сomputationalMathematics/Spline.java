package com.vsu.сomputationalMathematics;

public class Spline {
    private double a;
    private double b;
    private double c;
    private double d;
    private double xi;

    public Spline() {
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

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getXi() {
        return xi;
    }

    public void setXi(double xi) {
        this.xi = xi;
    }
    public void printSpline(){
        System.out.println("A = "+a+" B = "+b+" C = "+c+" D = "+d+" x[i] = "+xi+";");
    }
}
