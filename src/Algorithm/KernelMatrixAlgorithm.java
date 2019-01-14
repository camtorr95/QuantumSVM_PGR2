/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import Data.TestingSet;
import Data.TrainingSet;
import Quantum.ComplexNumber;
import Quantum.ComplexVectorSpaces;
import Quantum.Gates;

/**
 *
 * @author Camilo Torres
 */
public class KernelMatrixAlgorithm {

    public static void main(String[] args) {
        ComplexNumber[][] K = getKernelMatrix();
        print_matrix(Gates.matrix_exponential_i_pi_F_2());
    }

    public static ComplexNumber[][] getKernelMatrix() {
        return kernelMatrix_1();
    }

    public static ComplexNumber[][] kernelMatrix_1() {
        ComplexNumber[] qubit_1 = {new ComplexNumber(1), new ComplexNumber(0)};
        ComplexNumber[] qubit_2 = {new ComplexNumber(1), new ComplexNumber(0)};

        ComplexNumber[][] rotation_y_theta1 = Gates.Ry_GATE(TrainingSet.THETA_1);
        ComplexNumber[][] rotation_y_theta2 = Gates.Ry_GATE(TrainingSet.THETA_2);

        ComplexNumber[][] ac_ryt1 = Gates.anti_controlled_gate(rotation_y_theta1);
        ComplexNumber[][] c_ryt2 = Gates.controlled_gate(0, rotation_y_theta2);

        ComplexNumber[][] H_I = ComplexVectorSpaces.tensorProduct(Gates.H_GATE(), Gates.I(2));
        ComplexNumber[] qubits = ComplexVectorSpaces.ComplexVectorTensorProduct(qubit_1, qubit_2);

        ComplexNumber[][] one = ComplexVectorSpaces.complexMatrixMultiplication(c_ryt2, ac_ryt1);
        ComplexNumber[][] two = ComplexVectorSpaces.complexMatrixMultiplication(one, H_I);
        ComplexNumber[] three = ComplexVectorSpaces.complexMatrixVectorMultiplication(two, qubits);

        ComplexNumber[][] density_matrix = ComplexVectorSpaces.densityMatrix(three);
        ComplexNumber[][] partial_trace = ComplexVectorSpaces.partial_trace_4x4(density_matrix);

        return partial_trace;
    }

    public static ComplexNumber[][] kernelMatrix_2() {
        ComplexNumber[][] K = {
            {new ComplexNumber(0.5065), new ComplexNumber(0.2425)},
            {new ComplexNumber(0.2425), new ComplexNumber(0.4935)}};
        return K;
    }

    private static void print_vector(ComplexNumber[] vector) {
        for (int i = 0; i < vector.length; ++i) {
            System.out.println(vector[i]);
        }
        System.out.println(vector.length);
    }

    private static void print_matrix(ComplexNumber[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
