package com.vsu.сomputationalMathematics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class WorkWithFile {
    private int n;
    private double a;
    private double b;
    private double xx;
    private String[] x;
    private String[] y;

    WorkWithFile() {
    }

    int getN() {
        return n;
    }

    double getA() {
        return a;
    }

    double getB() {
        return b;
    }

    double getXx() {
        return xx;
    }

    String[] getX() {
        return x;
    }

    String[] getY() {
        return y;
    }

    void FileWorking() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("File.txt"));
        n = Integer.parseInt(reader.readLine());
        x = reader.readLine().split(" ");
        y = reader.readLine().split(" ");
        a = Double.parseDouble(reader.readLine());
        b = Double.parseDouble(reader.readLine());
        xx = Double.parseDouble(reader.readLine());
        reader.close();
    }
}
