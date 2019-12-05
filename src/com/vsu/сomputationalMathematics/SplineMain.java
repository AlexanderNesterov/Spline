package com.vsu.сomputationalMathematics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SplineMain {
    public static void main(String[] args) throws IOException {
        WorkWithFile file = new WorkWithFile();
        file.FileWorking();
        int N = file.getN();
        double A = file.getA();
        double B = file.getB();
        double XX = file.getXx();
        double[] x = parseArray(file.getX());
        double[] y = parseArray(file.getY());

        FileWriter writer = new FileWriter("RezFile.txt");

        if (checkOrder(x) && x.length > 2 && affiliation(x, XX)) {
            double[] h = new double[N];
            for (int i = 1; i < N; i++) {
                h[i] = x[i] - x[i - 1];
            }

            double[] CC = new double[N];
            double[] AA = new double[N];
            double[] BB = new double[N];
            double[] F = new double[N];

            CC[0] = 1;
            AA[0] = 0;
            BB[0] = 0.5;
            F[0] = 3 * h[1] * ((y[1] - y[0]) / h[1] - A);

            for (int i = 1; i < N - 1; i++) {
                CC[i] = 2 * (h[i] + h[i + 1]);
                AA[i] = h[i];
                BB[i] = h[i + 1];
                F[i] = 6 * ((y[i + 1] - y[i]) / h[i + 1] - (y[i] - y[i - 1]) / h[i]);
            }

            CC[N - 1] = 1;
            AA[N - 1] = 0.5;
            BB[N - 1] = 0;
            F[N - 1] = 3 / h[N - 1] * (B - (y[N - 1] - y[N - 2]) / h[N - 1]);

            double[] mu = new double[N + 1];
            double[] nu = new double[N + 1];

            mu[1] = -BB[0] / CC[0];
            nu[1] = F[0] / CC[0];

            for (int i = 1; i < N; i++) {
                mu[i + 1] = -BB[i] / (CC[i] + AA[i] * mu[i]);
                nu[i + 1] = (F[i] - AA[i] * nu[i]) / (CC[i] + AA[i] * mu[i]);
            }

            double[] C = new double[N];
            C[N - 1] = (F[N - 1] - AA[N - 1] * nu[N - 1]) / (CC[N - 1] + AA[N - 1] * mu[N - 1]);

            for (int i = N - 2; i >= 0; i--) {
                C[i] = mu[i + 1] * C[i + 1] + nu[i + 1];
            }

            double[] D = new double[N];
            double[] B_coef = new double[N];

            for (int i = N - 1; i > 0; i--) {
                D[i] = (C[i] - C[i - 1]) / h[i];
                B_coef[i] = C[i] * h[i] / 2 - D[i] / 6 * (h[i] * h[i]) + (y[i] - y[i - 1]) / h[i];
            }

            double spline = 0;

            for (int i = 1; i < N; i++) {
                double dx = XX - x[i];
                spline = B_coef[i] + C[i] * dx + D[i] * dx * dx / 2;
            }

            System.out.println("Spline = " + spline);
            writer.write("IER=0. Errors not found!\n");
            writer.write("YY = " + spline);
            writer.close();
        } else if (!checkOrder(x)) {
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

    private static boolean checkOrder(double[] checkArray) {
        for (int i = 0; i < checkArray.length - 1; i++) {
            if (checkArray[i] >= checkArray[i + 1]) {
                return false;
            }
        }

        return true;
    }

    private static boolean affiliation(double[] checkArray, double check) {
        for (int i = 0; i < checkArray.length - 1; i++) {
            if (check > checkArray[i] && check < checkArray[i + 1]) {
                return true;
            }
        }

        return false;
    }

    private static double[] parseArray(String[] array) {
        return Arrays.stream(array)
                .map(Double::parseDouble)
                .mapToDouble(xPoint -> xPoint)
                .toArray();
    }
}

