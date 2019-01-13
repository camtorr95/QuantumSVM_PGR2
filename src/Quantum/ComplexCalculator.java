package Quantum;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Camilo
 */
public class ComplexCalculator {

    /**
     * Sum between 2 complex numbers
     * @param c1 complex number
     * @param c2 complex number
     * @return c1 + c2
     */
    public static ComplexNumber complexSum(ComplexNumber c1, ComplexNumber c2) {
        double a = c1.getReal() + c2.getReal();
        double b = c1.getImaginaria() + c2.getImaginaria();
        return new ComplexNumber(a, b);
    }

    /**
     * Subtraction between 2 complex numbers
     * @param c1 complex number
     * @param c2 complex number (substractor)
     * @return difference between c1 and c2 (c1 - c2)
     */
    public static ComplexNumber complexSubtraction(ComplexNumber c1, ComplexNumber c2) {
        double a = c1.getReal() - c2.getReal();
        double b = c1.getImaginaria() - c2.getImaginaria();
        return new ComplexNumber(a, b);
    }

    /**
     * Multiplication between complex numbers
     * @param c1 complex number
     * @param c2 complex number
     * @return c1 * c2
     */
    public static ComplexNumber complexMultiplication(ComplexNumber c1, ComplexNumber c2) {
        double a = c1.getReal() * c2.getReal() - c1.getImaginaria() * c2.getImaginaria();
        double b = c1.getReal() * c2.getImaginaria() + c1.getImaginaria() * c2.getReal();
        return new ComplexNumber(a, b);
    }

    /**
     * Division between 2 complex numbers
     * @param c1 complex number dividend
     * @param c2 complex number divisor
     * @return c1 / c2
     */
    public static ComplexNumber complexDivision(ComplexNumber c1, ComplexNumber c2) {
        double div = c2.getReal() * c2.getReal() + c2.getImaginaria() * c2.getImaginaria();
        double a = (c1.getReal() * c2.getReal() + c1.getImaginaria() + c2.getImaginaria()) / div;
        double b = (c2.getReal() * c1.getImaginaria() - c1.getReal() * c2.getImaginaria()) / div;
        return new ComplexNumber(a, b);
    }

    /**
     *
     * @param ro Modulo del numero
     * @param theta Angulo en radianes
     * @return
     */
    public static ComplexNumber polarToComplex(double ro, double theta) {
        double a = ro * Math.cos(theta);
        double b = ro * Math.sin(theta);
        return new ComplexNumber(a, b);
    }

    public static ComplexNumber getConjugate(ComplexNumber c) {
        double a = c.getReal();
        double b = (-1) * c.getImaginaria();
        return new ComplexNumber(a, b);
    }

    /**
     *
     * @param c Numero complejo al cual se quiere elevar
     * @param power Exponente entero mayor o igual a 0
     * @return Resultado c elevado a power
     */
    public static ComplexNumber complexPower(ComplexNumber c, int power) {
        double ro = Math.pow(c.getModulus(), power);
        double theta = power * c.getThetaRadians();
        return polarToComplex(ro, theta);
    }

    /**
     *
     * @param c Numero complejo al cual se quiere elevar
     * @param power Exponente complejo
     * @return Resultado c elevado a power
     */
    public static ComplexNumber complexPowerComplex(ComplexNumber c, ComplexNumber power) {
        double ro = Math.pow(c.getModulus(), power.getModulus());
        double theta = power.getThetaRadians() * c.getThetaRadians();
        return polarToComplex(ro, theta);
    }

    /**
     *
     * @param c Numero complejo al que se le quieren hayar las raices
     * @param root Raiz n (root)
     * @return Raices de c. Arreglo de tamaño root por el teorema fundamental
     * del algebra.
     */
    public static ComplexNumber[] complexRoot(ComplexNumber c, int root) {
        double ro = Math.pow(c.getModulus(), 1 / root);
        double theta = c.getThetaRadians();
        ComplexNumber[] roots = new ComplexNumber[root];
        for (int k = 0; k < root; ++k) {
            double theta_root = (theta + k * 2 * Math.PI) / root;
            roots[k] = polarToComplex(ro, theta_root);
        }
        return roots;
    }
}
