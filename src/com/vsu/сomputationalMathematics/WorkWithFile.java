package com.vsu.сomputationalMathematics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private int n;
    private double a;
    private double b;
    private double xx;
    private String[] x;
    private String[] y;

    public WorkWithFile() {
    }

    public int getN() {
        return n;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getXx() {
        return xx;
    }

    public String[] getX() {
        return x;
    }

    public String[] getY() {
        return y;
    }

    public void FileWorking() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("File.txt"));
        String c;
        c = reader.readLine();
        n = Integer.parseInt(c);
        c = reader.readLine();
        x = c.split(" ");
        c = reader.readLine();
        y = c.split(" ");
        c = reader.readLine();
        a = Double.parseDouble(c);
        c = reader.readLine();
        b = Double.parseDouble(c);
        c = reader.readLine();
        xx = Double.parseDouble(c);
        reader.close();
    }

}
