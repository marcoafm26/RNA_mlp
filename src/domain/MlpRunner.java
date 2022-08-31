package domain;

import app.MLP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MlpRunner {

    //base and
    private static double[][][] base = new double[][][]{
            {{0,0},{0}},
            {{0,1},{0}},
            {{1,0},{0}},
            {{1,1},{1}},
    };

    // base Xor
//    private static final double[][][] base = {
//            { {0,0}, {0} },
//            { {0,1}, {1} },
//            { {1,0}, {1} },
//            { {1,1}, {0} }
//    };

    // base Or
//    private static final double[][][] base = {
//            { { 0, 0}, {0} },
//            { { 0, 1}, { 1} },
//            { { 1, 0 }, { 1} },
//            { { 1, 1 }, { 1} }
//    };

    //Robo
//    private static final double[][][] base = {
//            { { 0, 0, 0 }, { 1, 1 } },
//            { { 0, 0, 1 }, { 0, 1 } },
//            { { 0, 1, 0 }, { 1, 0 } },
//            { { 0, 1, 1},  { 0, 1 } },
//            { { 1, 0, 0 }, { 1, 0 } },
//            { { 1, 0, 1 }, { 1, 0 } },
//            { { 1, 1, 0 }, { 1, 0 } },
//            { { 1, 1, 1 }, { 1, 0 } }
//    };

    public static void main(String[] args) throws FileNotFoundException {
        
        final int QTD_IN = 2;
        final int QTD_OUT= 1;
        final int QTD_H= 15;
        final double U = 0.3;
        final int EPOCA = 10000;

        MLP p = new MLP(QTD_IN,QTD_OUT,QTD_H,U);
        
        double erroEp = 0;
        double erroAm = 0;
        //double base[][][] = dataReader();
        for (int e = 0; e < EPOCA; e++) {
            erroEp = 0;

            for (int a = 0; a < base.length; a++) {
                double[] x = base[a][0];
                double[] y = base[a][1];
                double[] out = p.learn(x,y);
                erroAm = somador(y,out);
                erroEp += erroAm;
            }
                imprimir(erroEp,e);
        }
    }

    public static double somador(double[] y, double[] out){
        double soma=0;
        for (int i = 0; i < y.length; i++) {
            soma+=Math.abs(y[i]-out[i]);
        }
        return soma;
    }
    public static void imprimir(double erroEp, int epoca){
        System.out.println("Epoca "+(epoca+1)+"   erro: "+erroEp);
    }
    public static double[][][] dataReader() throws FileNotFoundException {
        double[][][] base = new double[1891][2][];
        double[] data = new double[8];
        File file = new File("abalone.data");
        Scanner ler = new Scanner(file);
        double out = 0;

        int cont = 0;
        for (int i = 0; i < 4177; i++) {
            String linha = ler.nextLine();

            data = inFill(linha);

            if (data[7] == 8 || data[7] == 9 || data[7] ==10){
                base[cont][0] = new double[7];
                for (int j = 0; j < 7; j++) {
                    base[cont][0][j] = data[j];
                }
                out = data[7];
                if(out == 8){
                    base[cont][1] = new double[]{0, 0, 0};
                } else if (out == 9) {
                    base[cont][1] = new double[]{0, 0, 1};
                } else if (out == 10) {
                    base[cont][1] = new double[]{0, 1, 0};
                }
                cont++;
            }

        }
    return base;
    }
    public static double[] inFill(String linha){
        double[] entradas;
        linha = linha.replace("F,","");
        linha = linha.replace("M,","");
        linha = linha.replace("I,","");
        entradas = Arrays.stream(linha.substring(1, linha.length()).split(",")).map(String::trim).mapToDouble(Double::parseDouble).toArray();
    return entradas;
    }
}
