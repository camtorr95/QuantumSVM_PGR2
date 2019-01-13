/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quantum;

/**
 *
 * @author Camilo Torres
 */
public class Gates {

    /**
     * Identity Matrix
     * @param size size of the matrix
     * @return Identity matrix size * size
     */
    public static ComplexNumber[][] I(int size) {
        ComplexNumber[][] I = new ComplexNumber[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i == j) {
                    I[i][j] = new ComplexNumber(1);
                } else {
                    I[i][j] = new ComplexNumber(0);
                }
            }
        }
        return I;
    }

    /**
     * The Hadamard Matrix
     * @return Hadamard Matrix 2x2
     */
    public static ComplexNumber[][] H_GATE() {
        double SQRT_2 = Math.sqrt(2);

        ComplexNumber[][] H_GATE = {
            {new ComplexNumber(1 / SQRT_2), new ComplexNumber(1 / SQRT_2)},
            {new ComplexNumber(1 / SQRT_2), new ComplexNumber(-1 / SQRT_2)}};

        return H_GATE;
    }

    /**
     * Matriz de phase gate (S), phase shift para phi = pi/2
     *
     * @return Phase Gate S 2x2
     */
    public static ComplexNumber[][] S_GATE() {
        ComplexNumber E = new ComplexNumber(Math.E);
        ComplexNumber shift = new ComplexNumber(0, Math.PI / 2);
        ComplexNumber[][] S_GATE = {
            {new ComplexNumber(1), new ComplexNumber(0)},
            {new ComplexNumber(0), ComplexCalculator.complexPowerComplex(E, shift)}};

        return S_GATE;
    }

    /**
     * Inversa de S
     *
     * @return S -1
     */
    public static ComplexNumber[][] S_INVERSE_GATE() {
        ComplexNumber[][] S_TEMP_GATE = S_GATE();
        S_TEMP_GATE[1][1] = ComplexCalculator.complexDivision(new ComplexNumber(1), S_TEMP_GATE[1][1]);
        return S_TEMP_GATE;
    }

    /**
     * Matriz de rotación sobre el eje y en la esfera de bloch
     *
     * @param shift_angle Angulo de rotación
     * @return Matriz de rotación sobre y
     */
    public static ComplexNumber[][] Ry_GATE(double shift_angle) {
        double cosine = Math.cos(shift_angle / 2);
        double sine = Math.sin(shift_angle / 2);

        ComplexNumber[][] Ry_GATE = {
            {new ComplexNumber(cosine), new ComplexNumber(-sine)},
            {new ComplexNumber(sine), new ComplexNumber(cosine)}};

        return Ry_GATE;
    }

    /**
     * Matriz inversa de rotación sobre el ejece y en la esfera de bloch
     *
     * @param shift_angle Angulo de rotación
     * @return Matriz inversa de rotación sobre y
     */
    public static ComplexNumber[][] Ry_INVERSE_GATE(double shift_angle) {
        double cosine = Math.cos(shift_angle / 2);
        double sine = Math.sin(shift_angle / 2);

        ComplexNumber[][] Ry_GATE = {
            {new ComplexNumber(cosine), new ComplexNumber(sine)},
            {new ComplexNumber(-sine), new ComplexNumber(cosine)}};

        return Ry_GATE;
    }

    /**
     * Version controlada de la compuerta teniendo en cuenta el espacio entre
     * qubits
     *
     * @param spacing Cuantos registros (qubits) hay entre el bit de control y
     * la compuerta. Si es 0 significa que los registros son i(control),
     * i+1(gate). Para este demo solo se espera un input entre 0 y 2
     * @param gate Compuerta que actua sobre un solo registro (matriz 2x2)
     * @return
     */
    public static ComplexNumber[][] controlled_gate(int spacing, ComplexNumber[][] gate) {
        int n_length = (int) Math.pow(2, 2 + spacing);
        ComplexNumber[][] c_gate = new ComplexNumber[n_length][n_length];
        for (int i = 0; i < n_length / 2; ++i) {
            for (int j = 0; j < n_length; ++j) {
                if (i == j) {
                    c_gate[i][j] = new ComplexNumber(1);
                } else {
                    c_gate[i][j] = new ComplexNumber(0);
                }
            }
        }
        for (int i = n_length / 2; i < n_length; ++i) {
            for (int j = 0; j < n_length / 2; ++j) {
                c_gate[i][j] = new ComplexNumber(0);
            }
        }
        for (int i = n_length / 2; i < n_length; i += 2) {
            for (int j = n_length / 2; j < n_length; ++j) {
                if (i == j) {
                    c_gate[i][j] = new ComplexNumber(gate[0][0].getReal(), gate[0][0].getImaginaria());
                    c_gate[i][++j] = new ComplexNumber(gate[0][1].getReal(), gate[0][1].getImaginaria());
                } else {
                    c_gate[i][j] = new ComplexNumber(0);
                }
            }
        }
        for (int i = n_length / 2 + 1; i < n_length; i += 2) {
            for (int j = n_length / 2; j < n_length; ++j) {
                if (j == i - 1) {
                    c_gate[i][j] = new ComplexNumber(gate[1][0].getReal(), gate[1][0].getImaginaria());
                    c_gate[i][++j] = new ComplexNumber(gate[1][1].getReal(), gate[1][1].getImaginaria());
                } else {
                    c_gate[i][j] = new ComplexNumber(0);
                }
            }
        }
        return c_gate;
    }

    public static ComplexNumber[][] anti_controlled_gate(ComplexNumber[][] gate) {
        int n_length = gate.length * 2;
        ComplexNumber[][] anti_controlled_gate = new ComplexNumber[n_length][n_length];

        for (int i = 0; i < n_length; ++i) {
            for (int j = 0; j < n_length; ++j) {
                if (i < gate.length) {
                    if ((i == 0 && j == 1) || (i == 1 && j == 0)) {
                        anti_controlled_gate[i][j] = new ComplexNumber(1);
                    } else {
                        anti_controlled_gate[i][j] = new ComplexNumber(0);
                    }
                } else {
                    if (j < gate.length) {
                        anti_controlled_gate[i][j] = new ComplexNumber(0);
                    } else {
                        anti_controlled_gate[i][j] = new ComplexNumber(gate[i - gate.length][j - gate.length].getReal(), gate[i - gate.length][j - gate.length].getImaginaria());
                    }
                }
            }
        }

        return anti_controlled_gate;
    }

    public static ComplexNumber[][] swap_gate() {
        ComplexNumber[][] swap = {
            {new ComplexNumber(1), new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(0)},
            {new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(0)},
            {new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(0), new ComplexNumber(0)},
            {new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(0), new ComplexNumber(1)}};
        return swap;
    }

    public static ComplexNumber[][] big_swap_16x16() {
        ComplexNumber[][] swap = new ComplexNumber[16][16];

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (i == j && i % 2 == 0) {
                    swap[i][j] = new ComplexNumber(1);
                } else {
                    swap[i][j] = new ComplexNumber(0);
                }
            }
        }

        for (int i = 8; i < 16; ++i) {
            for (int j = 8; j < 16; ++j) {
                if (i == j && i % 2 == 1) {
                    swap[i][j] = new ComplexNumber(1);
                } else {
                    swap[i][j] = new ComplexNumber(0);
                }
            }
        }

        int counter = 0;

        for (int i = 0; i < 8; ++i) {
            if (i > 0 && i % 2 == 0) {
                counter += 2;
            }
            for (int j = 8; j < 16; ++j) {
                if ((i - counter == 1) && (j - counter == 8)) {
                    swap[i][j] = new ComplexNumber(1);
                    swap[j][i] = new ComplexNumber(1);
                } else {
                    swap[i][j] = new ComplexNumber(0);
                    swap[j][i] = new ComplexNumber(0);
                }
            }
        }

        return swap;
    }

    /**
     * Devuelve la compuerta controlada, con el bit de control abajo teniendo en
     * cuenta el espacio entre los qubits
     *
     * @param spacing Cuantos registros (qubits) hay entre el bit de control y
     * la compuerta. Si es 0 significa que los registros son i(operacion),
     * i+1(control). Para este demo solo se espera un input de 0 y 1
     * @param gate Compuerta que actua sobre un solo registro (matriz 2x2)
     * @return
     */
    public static ComplexNumber[][] controlled_reversed_gate(int spacing, ComplexNumber[][] gate) {
        int n_length = (int) Math.pow(2, 2 + spacing);
        ComplexNumber[][] cr_gate = new ComplexNumber[n_length][n_length];
        for (int i = 0; i < cr_gate.length; i += 2) {
            for (int j = 0; j < cr_gate.length; ++j) {
                if (i == j) {
                    cr_gate[i][j] = new ComplexNumber(1);
                } else {
                    cr_gate[i][j] = new ComplexNumber(0);
                }
            }
        }
        int counter = 1 + spacing;
        int space = spacing * 2 + 2;
        for (int i = 1; i < cr_gate.length; i += 2) {
            for (int j = 0; j < cr_gate.length; ++j) {
                if (counter > 0) {
                    if (i == j) {
                        cr_gate[i][j] = new ComplexNumber(gate[0][0].getReal(), gate[0][0].getImaginaria());
                    } else if (j == i + space) {
                        cr_gate[i][j] = new ComplexNumber(gate[0][1].getReal(), gate[0][1].getImaginaria());
                    } else {
                        cr_gate[i][j] = new ComplexNumber(0);
                    }
                } else {
                    if (j == i - space) {
                        cr_gate[i][j] = new ComplexNumber(gate[1][0].getReal(), gate[1][0].getImaginaria());
                    } else if (i == j) {
                        cr_gate[i][j] = new ComplexNumber(gate[1][1].getReal(), gate[1][1].getImaginaria());
                    } else {
                        cr_gate[i][j] = new ComplexNumber(0);
                    }
                }
            }
            --counter;
        }
        return cr_gate;
    }

    public static ComplexNumber[][] double_controlled_reversed_gate(ComplexNumber[][] gate) {
        ComplexNumber[][] dc_gate = new ComplexNumber[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                switch (i) {
                    case 3:
                        if (i == j) {
                            dc_gate[i][j] = new ComplexNumber(gate[0][0].getReal(), gate[0][0].getImaginaria());
                        } else if (j == i + 4) {
                            dc_gate[i][j] = new ComplexNumber(gate[0][1].getReal(), gate[0][1].getImaginaria());
                        } else {
                            dc_gate[i][j] = new ComplexNumber(0);
                        }
                        break;
                    case 7:
                        if (j == i - 4) {
                            dc_gate[i][j] = new ComplexNumber(gate[1][0].getReal(), gate[1][0].getImaginaria());
                        } else if (i == j) {
                            dc_gate[i][j] = new ComplexNumber(gate[1][1].getReal(), gate[1][1].getImaginaria());
                        } else {
                            dc_gate[i][j] = new ComplexNumber(0);
                        }
                        break;
                    default:
                        if (i == j) {
                            dc_gate[i][j] = new ComplexNumber(1);
                        } else {
                            dc_gate[i][j] = new ComplexNumber(0);
                        }
                        break;
                }
            }
        }
        return dc_gate;
    }

    public static ComplexNumber[][] anti_controlled_reversed_gate(ComplexNumber[][] gate) {
        ComplexNumber[][] ac_gate = new ComplexNumber[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                switch (i) {
                    case 1:
                        if (i == j) {
                            ac_gate[i][j] = new ComplexNumber(gate[0][0].getReal(), gate[0][0].getImaginaria());
                        } else if (j == i + 4) {
                            ac_gate[i][j] = new ComplexNumber(gate[0][1].getReal(), gate[0][1].getImaginaria());
                        } else {
                            ac_gate[i][j] = new ComplexNumber(0);
                        }
                        break;
                    case 5:
                        if (j == i - 4) {
                            ac_gate[i][j] = new ComplexNumber(gate[1][0].getReal(), gate[1][0].getImaginaria());
                        } else if (i == j) {
                            ac_gate[i][j] = new ComplexNumber(gate[1][1].getReal(), gate[1][1].getImaginaria());
                        } else {
                            ac_gate[i][j] = new ComplexNumber(0);
                        }
                        break;
                    default:
                        if (i == j) {
                            ac_gate[i][j] = new ComplexNumber(1);
                        } else {
                            ac_gate[i][j] = new ComplexNumber(0);
                        }
                        break;
                }
            }
        }
        return ac_gate;
    }

    public static ComplexNumber[][] matrix_exponential_i_pi_F() {
        ComplexNumber[][] exp_ipF = {
            {new ComplexNumber(-0.7234, -0.0185), new ComplexNumber(0, -0.6902)},
            {new ComplexNumber(0, -0.6902), new ComplexNumber(-0.7234, 0.0185)}};
        return exp_ipF;
    }

    public static ComplexNumber[][] matrix_exponential_i_pi_F_2() {
        ComplexNumber[][] exp_ipF2 = {
            {new ComplexNumber(-0.0100, 0.9283), new ComplexNumber(-0.3718)},
            {new ComplexNumber(-0.3718), new ComplexNumber(0.0100, 0.9283)}};
        return exp_ipF2;
    }

    public static ComplexNumber[][] get_inverse_for_exponentials(ComplexNumber[][] matrix) {
        ComplexNumber det = ComplexVectorSpaces.matrix_determinant_2x2(matrix);
        ComplexNumber neg_b = new ComplexNumber(-matrix[0][1].getReal(), -matrix[0][1].getImaginaria());
        ComplexNumber neg_c = new ComplexNumber(-matrix[1][0].getReal(), -matrix[1][0].getImaginaria());
        ComplexNumber[][] inverse = {
            {ComplexCalculator.complexDivision(matrix[1][1], det), ComplexCalculator.complexDivision(neg_b, det)},
            {ComplexCalculator.complexDivision(neg_c, det), ComplexCalculator.complexDivision(matrix[0][0], det)}};
        return inverse;
    }
}
