package com.vsu.сomputationalMathematics;

import java.io.FileWriter;
import java.io.IOException;


public class SplineMain {
    public static void main(String[] args) throws IOException {
        WorkWithFile file = new WorkWithFile();
        file.FileWorking();
        int N = file.getN();
        double A = file.getA();
        double B = file.getB();
        double XX = file.getXx();
        double[] x = new double[file.getX().length];
        for (int i = 0; i < file.getX().length; i++) {
            x[i] = Double.parseDouble(file.getX()[i]);
        }

        double[] y = new double[file.getY().length];
        for (int i = 0; i < file.getY().length; i++) {
            y[i] = Double.parseDouble(file.getY()[i]);
        }
//        for (int i = 0; i < N; i++) {
//            System.out.println("X = " + x[i] + " Y = " + y[i]);
//        }
        FileWriter writer = new FileWriter("RezFile.txt");
        if (ascendingOrder(x) && x.length > 2 && affiliation(x, XX)) {
            double[] h = new double[N];
            for (int i = 1; i < N; i++) {
                h[i] = x[i] - x[i - 1];
            }
//            for (int i = 1; i < N; i++) {
//                System.out.println("h = " + h[i]);
//            }
            double[] T = new double[N];
            double[] K = new double[N];
            double[] G = new double[N];
            double[] F = new double[N];
            T[0] = 1;
            K[0] = 0;
            G[0] = 0.5;
            F[0] = 3 * h[1] * ((y[1] - y[0]) / h[1] - A);
            for (int i = 1; i < N - 1; i++) {
                T[i] = 2 * (h[i] + h[i + 1]);
                K[i] = h[i];
                G[i] = h[i + 1];
                F[i] = 6 * ((y[i + 1] - y[i]) / h[i + 1] - (y[i] - y[i - 1]) / h[i]);
            }
            T[N - 1] = 1;
            K[N - 1] = 0.5;
            G[N - 1] = 0;
            F[N - 1] = 3 / h[N - 1] * (B - (y[N - 1] - y[N - 2]) / h[N - 1]);
//            System.out.println("_________________________________________");
//            for (int i = 0; i < N; i++) {
//                System.out.println(" K = " + K[i] + " T = " + T[i] + " G = " + G[i] + " F = " + F[i]);
//            }
            double[] m = new double[N + 1];
            double[] n = new double[N + 1];
            m[1] = -G[0] / T[0];
            n[1] = F[0] / T[0];
            for (int i = 1; i < N; i++) {
                m[i + 1] = -G[i] / (T[i] + K[i] * m[i]);
//                System.out.println("*************************************");
//                System.out.println("G[" + i + "] = " + G[i] + " T[" + i + "] = " + T[i] + " K[" + i + "] = " + K[i] + " m[" + i + "] = " + m[i]);
//                System.out.println("*************************************");
                n[i + 1] = (F[i] - K[i] * n[i]) / (T[i] + K[i] * m[i]);
            }
//            System.out.println("_________________________________________");
//            for (int i = 1; i < N; i++) {
//                System.out.println(" m[" + i + "] = " + m[i] + " n = " + n[i]);
//            }
            double[] C = new double[N];
            // System.out.println("Check = "+(F[N-2]-K[N-2]*n[N-1])+" Check1 = "+(T[N-2]+K[N-2]*m[N-2]));
            C[N - 1] = (F[N - 1] - K[N - 1] * n[N - 1]) / (T[N - 1] + K[N - 1] * m[N - 1]);
            for (int i = N - 2; i >= 0; i--) {
                C[i] = m[i + 1] * C[i + 1] + n[i + 1];
            }
//            System.out.println("_________________________________________");
//            for (int i = 0; i < N; i++) {
//                System.out.println(" C[" + i + "] = " + C[i]);
//            }
            double[] D = new double[N];
            double[] B_coef = new double[N];
            for (int i = N - 1; i > 0; i--) {

                D[i] = (C[i] - C[i - 1]) / h[i];
//                System.out.println("*************************************");
//                System.out.println("D[" + i + "] = " + D[i] + " C[" + i + "] = " + C[i]);
//                System.out.println("*************************************");
                B_coef[i] = C[i] * h[i] / 2 - D[i] / 6 * (h[i] * h[i]) + (y[i] - y[i - 1]) / h[i];
            }
//            System.out.println("_________________________________________");
//            for (int i = 1; i < N; i++) {
//                System.out.println(" B = " + B_coef[i] + " D = " + D[i]);
//            }
            double spline = 0;
            for (int i = 1; i < N; i++) {
                double dx = XX - x[i];
//                System.out.println(" Y = " + y[i]);
                spline = B_coef[i] + C[i] * dx + D[i] * dx * dx / 2;
//                System.out.println(" Spline = " + spline);
            }
//            System.out.println("_________________________________________");
            System.out.println(" Spline = " + spline);
            writer.write("IER=0. Errors not found!\n");
            writer.write("YY = " + spline);
            writer.close();
        } else if (ascendingOrder(x) == false) {
            writer.write("IER=2. Violated the order of increasing elements.");
            writer.close();

        } else if (x.length < 3) {
            writer.write("IER=1. A polynomial cannot be built");
            writer.close();
        } else {
            writer.write("IER=3. The point does not belong to the given segment");
            writer.close();
        }

    }

    private static boolean ascendingOrder(double[] checkArray) {
        boolean flag = true;
        int i = 0;
        while (i < checkArray.length - 1 && flag) {
            if (checkArray[i] > checkArray[i + 1] || checkArray[i] == checkArray[i + 1])
                flag = false;
            i++;
        }
        return flag;
    }

    private static boolean affiliation(double[] checkArray, double check) {
        boolean flag = false;
        int i = 0;
        while (!flag && i < checkArray.length - 1) {
            if (checkArray[i] < check && checkArray[i + 1] > check)
                flag = true;
            i++;
        }
        return flag;
    }
}

