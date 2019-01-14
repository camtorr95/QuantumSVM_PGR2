/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import Data.SVMCharacter;
import Data.TestingSet;
import Data.TrainingSet;
import Quantum.ComplexNumber;
import Quantum.ComplexVectorSpaces;
import Quantum.Gates;

/**
 * A simulation of a Quantum Support Vector Machine (SVM) For handwritten
 * character classification, specifically 6 and 9.
 *
 * @author Camilo Torres
 */
public class QuantumSVM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        classify();
    }

    public static void classify() {
        for (SVMCharacter c : TestingSet.TESTING_SET) {
            System.out.println(String.format("El caracter es un: %d - (%.4f,%.4f)", (c.getLabel() < 0 ? 9 : 6), c.getHorizontal_ratio(), c.getVertical_ratio()));
            ComplexNumber[] final_state = run_circuit(c.get_arccot());
            measure(final_state);
            System.out.println("------");
        }
    }

    public static ComplexNumber[] run_circuit(double theta_x0) {
        //Defining all quantum registers
        ComplexNumber[] qubit_1 = {new ComplexNumber(1), new ComplexNumber(0)};
        ComplexNumber[] qubit_2 = {new ComplexNumber(1), new ComplexNumber(0)};
        ComplexNumber[] qubit_3 = {new ComplexNumber(1), new ComplexNumber(0)};
        ComplexNumber[] qubit_4 = {new ComplexNumber(1), new ComplexNumber(0)};
        //Assembling the quantum state
        ComplexNumber[] one = ComplexVectorSpaces.ComplexVectorTensorProduct(qubit_1, qubit_2);
        ComplexNumber[] two = ComplexVectorSpaces.ComplexVectorTensorProduct(one, qubit_3);
        ComplexNumber[] qubits = ComplexVectorSpaces.ComplexVectorTensorProduct(two, qubit_4);

        //Defining all tensor products
        //Swap
        ComplexNumber[][] big_swap = Gates.big_swap_16x16();

        //Ux0
        //H gate
        ComplexNumber[][] controlled_h_gate = Gates.controlled_reversed_gate(0, Gates.H_GATE());
        ComplexNumber[][] ux_c_hgate = ComplexVectorSpaces.tensorProduct(Gates.I(4), controlled_h_gate);
        //Inverse Ry Theta_0
        ComplexNumber[][] controlled_rotation_theta = Gates.controlled_reversed_gate(1, Gates.Ry_INVERSE_GATE(theta_x0));
        ComplexNumber[][] ux_rotation = ComplexVectorSpaces.tensorProduct(Gates.I(2), controlled_rotation_theta);

        //Training Data Oracle
        //Ry Theta2
        ComplexNumber[][] double_controlled_rotation_theta2 = Gates.double_controlled_reversed_gate(Gates.Ry_GATE(TrainingSet.THETA_2));
        ComplexNumber[][] tdo_ryt2 = ComplexVectorSpaces.tensorProduct(Gates.I(2), double_controlled_rotation_theta2);
        //Ry Theta1
        ComplexNumber[][] anti_controlled_rotation_theta1 = Gates.anti_controlled_reversed_gate(Gates.Ry_GATE(TrainingSet.THETA_1));
        ComplexNumber[][] tdo_ryt1 = ComplexVectorSpaces.tensorProduct(Gates.I(2), anti_controlled_rotation_theta1);

        //MatrixInversion
        //Double Hadamard 1
        ComplexNumber[][] tensor_hadamard = ComplexVectorSpaces.tensorProduct(Gates.H_GATE(), Gates.H_GATE());
        ComplexNumber[][] mi_double_hadamard = ComplexVectorSpaces.tensorProduct(tensor_hadamard, Gates.I(4));
        //Controlled Inverse exponential -i*pi*F
        ComplexNumber[][] controlled_inverse_exp_ipiF = Gates.controlled_gate(1, Gates.get_inverse_for_exponentials(Gates.matrix_exponential_i_pi_F()));
        ComplexNumber[][] mi_inv_exp_ipiF = ComplexVectorSpaces.tensorProduct(controlled_inverse_exp_ipiF, Gates.I(2));
        //Controlled Inverse exponential -i*pi*F/2
        ComplexNumber[][] controlled_inverse_exp_ipiF2 = Gates.controlled_gate(0, Gates.get_inverse_for_exponentials(Gates.matrix_exponential_i_pi_F_2()));
        ComplexNumber[][] id_inv_exp_F2 = ComplexVectorSpaces.tensorProduct(Gates.I(2), controlled_inverse_exp_ipiF2);
        ComplexNumber[][] mi_inv_exp_ipiF2 = ComplexVectorSpaces.tensorProduct(id_inv_exp_F2, Gates.I(2));
        //Swap
        ComplexNumber[][] mi_swap = ComplexVectorSpaces.tensorProduct(Gates.swap_gate(), Gates.I(4));
        //Single Hadamard qbit 2 - 1
        ComplexNumber[][] id_h_2 = ComplexVectorSpaces.tensorProduct(Gates.I(2), Gates.H_GATE());
        ComplexNumber[][] mi_h_2 = ComplexVectorSpaces.tensorProduct(id_h_2, Gates.I(4));
        //Reverse Controlled Phase Gate
        ComplexNumber[][] reverse_controlled_phase_gate = Gates.controlled_reversed_gate(0, Gates.S_GATE());
        ComplexNumber[][] mi_rcpg = ComplexVectorSpaces.tensorProduct(reverse_controlled_phase_gate, Gates.I(4));
        //Single Hadamard qubit 1 - 1
        ComplexNumber[][] mi_h_1 = ComplexVectorSpaces.tensorProduct(Gates.H_GATE(), Gates.I(8));
        //Rotation Ry pi/8
        ComplexNumber[][] rotation_gate_pi_8 = Gates.Ry_GATE(Math.PI / 8);
        ComplexNumber[][] mi_rypi8 = Gates.controlled_gate(2, rotation_gate_pi_8);
        //Rotation Ry pi/4
        ComplexNumber[][] rotation_gate_pi_4 = Gates.Ry_GATE(Math.PI / 4);
        ComplexNumber[][] controlled_rotation_gate_pi_4 = Gates.controlled_gate(1, rotation_gate_pi_4);
        ComplexNumber[][] mi_rypi4 = ComplexVectorSpaces.tensorProduct(Gates.I(2), controlled_rotation_gate_pi_4);
        //Single Hadamard qubit 1 - 2 (again)
        //Reverse Controlled Inverse Phase Gate
        ComplexNumber[][] reverse_controlled_inverse_phase_gate = Gates.controlled_reversed_gate(0, Gates.S_INVERSE_GATE());
        ComplexNumber[][] mi_rcipg = ComplexVectorSpaces.tensorProduct(reverse_controlled_inverse_phase_gate, Gates.I(4));
        //Single Hadamard qubit 2 - 2 (again)
        //Swap (again)
        //Controlled Matrix Exponential i*pi*F/2
        ComplexNumber[][] controlled_exp_ipiF2 = Gates.controlled_gate(0, Gates.matrix_exponential_i_pi_F_2());
        ComplexNumber[][] id_exp_F2 = ComplexVectorSpaces.tensorProduct(Gates.I(2), controlled_exp_ipiF2);
        ComplexNumber[][] mi_exp_ipiF2 = ComplexVectorSpaces.tensorProduct(id_exp_F2, Gates.I(2));
        //Controlled Matrix Exponential i*pi*F
        ComplexNumber[][] controlled_exp_ipiF = Gates.controlled_gate(1, Gates.matrix_exponential_i_pi_F());
        ComplexNumber[][] mi_exp_ipiF = ComplexVectorSpaces.tensorProduct(controlled_exp_ipiF, Gates.I(2));
        //Double Hadamard 2 (again)

        //Evaluation
        ComplexNumber[][] step_1 = ComplexVectorSpaces.complexMatrixMultiplication(big_swap, ux_c_hgate);
        ComplexNumber[][] step_2 = ComplexVectorSpaces.complexMatrixMultiplication(step_1, ux_rotation);
        ComplexNumber[][] step_3 = ComplexVectorSpaces.complexMatrixMultiplication(step_2, tdo_ryt2);
        ComplexNumber[][] step_4 = ComplexVectorSpaces.complexMatrixMultiplication(step_3, tdo_ryt1);
        ComplexNumber[][] step_5 = ComplexVectorSpaces.complexMatrixMultiplication(step_4, mi_double_hadamard);
        ComplexNumber[][] step_6 = ComplexVectorSpaces.complexMatrixMultiplication(step_5, mi_inv_exp_ipiF);
        ComplexNumber[][] step_7 = ComplexVectorSpaces.complexMatrixMultiplication(step_6, mi_inv_exp_ipiF2);
        ComplexNumber[][] step_8 = ComplexVectorSpaces.complexMatrixMultiplication(step_7, mi_swap);
        ComplexNumber[][] step_9 = ComplexVectorSpaces.complexMatrixMultiplication(step_8, mi_h_2);
        ComplexNumber[][] step_10 = ComplexVectorSpaces.complexMatrixMultiplication(step_9, mi_rcpg);
        ComplexNumber[][] step_11 = ComplexVectorSpaces.complexMatrixMultiplication(step_10, mi_h_1);
        ComplexNumber[][] step_12 = ComplexVectorSpaces.complexMatrixMultiplication(step_11, mi_rypi8);
        ComplexNumber[][] step_13 = ComplexVectorSpaces.complexMatrixMultiplication(step_12, mi_rypi4);
        ComplexNumber[][] step_14 = ComplexVectorSpaces.complexMatrixMultiplication(step_13, mi_h_1);
        ComplexNumber[][] step_15 = ComplexVectorSpaces.complexMatrixMultiplication(step_14, mi_rcipg);
        ComplexNumber[][] step_16 = ComplexVectorSpaces.complexMatrixMultiplication(step_15, mi_h_2);
        ComplexNumber[][] step_17 = ComplexVectorSpaces.complexMatrixMultiplication(step_16, mi_swap);
        ComplexNumber[][] step_18 = ComplexVectorSpaces.complexMatrixMultiplication(step_17, mi_exp_ipiF2);
        ComplexNumber[][] step_19 = ComplexVectorSpaces.complexMatrixMultiplication(step_18, mi_exp_ipiF);
        ComplexNumber[][] step_20 = ComplexVectorSpaces.complexMatrixMultiplication(step_19, mi_double_hadamard);

        ComplexNumber[] final_state = ComplexVectorSpaces.complexMatrixVectorMultiplication(step_20, qubits);
        return final_state;
    }

    private static void print_matrix(ComplexNumber[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(matrix.length);
    }

    private static void print_vector(ComplexNumber[] vector) {
        for (int i = 0; i < vector.length; ++i) {
            System.out.println(vector[i]);
        }
        System.out.println(vector.length);
    }

    private static void measure(ComplexNumber[] vector) {
        int p, n;
        p = n = 0;
        for (ComplexNumber c : vector) {
            if (c.getReal() > 0) {
                ++p;
            } else {
                ++n;
            }
            if (c.getImaginaria() > 0) {
                ++p;
            } else {
                ++n;
            }
        }
        System.out.println("El resultado de la clasificación es: " + (p > n ? 6 : 9));
    }
}
