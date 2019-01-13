package Quantum;

import com.sun.media.jfxmedia.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Grupo PGR
 */
public class ComplexVectorSpaces {

    //Drill 2.2.2 pag 42
    public static ComplexNumber[][] complexMatrixMultiplication(ComplexNumber[][] matrix1, ComplexNumber[][] matrix2) {
        if (matrix1[0].length == matrix2.length) {
            ComplexNumber[][] ans = new ComplexNumber[matrix1.length][matrix2[0].length];
            for (int i = 0; i < ans.length; i++) {
                for (int j = 0; j < ans[i].length; j++) {
                    ComplexNumber num = new ComplexNumber(0, 0);
                    for (int k = 0; k < matrix1[0].length; k++) {
                        num = ComplexCalculator.complexSum(num, ComplexCalculator.complexMultiplication(matrix1[i][k], matrix2[k][j]));
                    }
                    ans[i][j] = num;
                }
            }
            return ans;
        } else {
            Logger.logMsg(Logger.ERROR, "INFO: Error, No se pueden multiplicar las matrices AxB, las columnas de A deben ser iguales a las filas de la matriz B");
            throw new RuntimeException("INFO: Error, No se pueden multiplicar las matrices AxB, las columnas de A deben ser iguales a las filas de la matriz B");

        }
    }

    //Drill 2.2.3 pag 42
    public static ComplexNumber[] complexMatrixVectorMultiplication(ComplexNumber[][] matrix, ComplexNumber[] vect) {
        if (matrix[0].length == vect.length) {
            ComplexNumber[] ans = new ComplexNumber[vect.length];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = ComplexVectorSpaces.complexVectorInnerProduct(matrix[i], vect);
            }
            return ans;
        } else {
            Logger.logMsg(Logger.ERROR, "INFO: Error, Las columnas de la matriz no son iguales ");
            throw new RuntimeException("INFO: Error, Las columnas de la matriz no son iguales ");
        }
    }

    //Drill 2.4.1 pag 55
    public static ComplexNumber complexVectorInnerProduct(ComplexNumber[] vect1, ComplexNumber[] vect2) {
        if (vect1.length == vect2.length) {
            ComplexNumber ans = new ComplexNumber(0, 0);
            for (int i = 0; i < vect1.length; i++) {
                ans = ComplexCalculator.complexSum(ans, ComplexCalculator.complexMultiplication(vect1[i], vect2[i]));
            }
            return ans;
        } else {
            Logger.logMsg(Logger.ERROR, "INFO: Error, Los vectores no tienen la misma dimension");
            throw new RuntimeException("INFO: Error, Los vectores no tienen la misma dimension");
        }
    }

    public static ComplexNumber[][] tensorProduct(ComplexNumber[][] matrix1, ComplexNumber[][] matrix2) {
        ComplexNumber[][] answerMatrix = new ComplexNumber[matrix1.length * matrix2.length][matrix1[0].length * matrix2[0].length];
        for (int i = 0; i < answerMatrix.length; i++) {
            for (int j = 0; j < answerMatrix[0].length; j++) {
                answerMatrix[i][j] = ComplexCalculator.complexMultiplication(matrix1[i / matrix2.length][j / matrix2[0].length], matrix2[i % matrix2.length][j % matrix2[0].length]);
            }
        }
        return answerMatrix;
    }

    public static ComplexNumber[] ComplexVectorTensorProduct(ComplexNumber[] vector1, ComplexNumber[] vector2) {
        ComplexNumber[] answerVector = new ComplexNumber[vector1.length * vector2.length];
        for (int i = 0; i < answerVector.length; i++) {
            answerVector[i] = ComplexCalculator.complexMultiplication(vector1[i / vector2.length], vector2[i % vector2.length]);
        }
        return answerVector;
    }

    public static boolean isSquare(ComplexNumber[][] matrix) {
        boolean square = false;
        if (matrix.length == matrix[0].length) {
            square = true;
        }
        return square;
    }

    public static ComplexNumber[][] densityMatrix(ComplexNumber[] state) {
        ComplexNumber[] state_ad = new ComplexNumber[state.length];
        ComplexNumber[][] density_matrix = new ComplexNumber[state.length][state.length];

        for (int i = 0; i < state.length; ++i) {
            state_ad[i] = new ComplexNumber(state[i].getReal(), -state[i].getImaginaria());
        }

        for (int i = 0; i < state.length; ++i) {
            for (int j = 0; j < state.length; ++j) {
                density_matrix[i][j] = ComplexCalculator.complexMultiplication(state[i], state_ad[j]);
            }
        }

        return density_matrix;
    }

    public static ComplexNumber[][] partial_trace_4x4(ComplexNumber[][] p) {
        ComplexNumber[][] partial_trace = {
            {ComplexCalculator.complexSum(p[0][0], p[1][1]), ComplexCalculator.complexSum(p[0][2], p[1][3])},
            {ComplexCalculator.complexSum(p[2][0], p[3][1]), ComplexCalculator.complexSum(p[2][2], p[3][3])}};
        return partial_trace;
    }

    public static ComplexNumber matrix_determinant_2x2(ComplexNumber[][] matrix) {
        ComplexNumber ad = ComplexCalculator.complexMultiplication(matrix[0][0], matrix[1][1]);
        ComplexNumber bc = ComplexCalculator.complexMultiplication(matrix[0][1], matrix[1][0]);

        return ComplexCalculator.complexSubtraction(ad, bc);
    }

    public static ComplexNumber trace(ComplexNumber[][] matrix) {
        ComplexNumber trace = new ComplexNumber(0);
        for (int i = 0; i < matrix.length; ++i) {
            trace = ComplexCalculator.complexSum(trace, matrix[i][i]);
        }
        return trace;
    }
}
