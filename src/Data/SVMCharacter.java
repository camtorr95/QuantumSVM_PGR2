/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Camilo Torres
 */
public class SVMCharacter {

    private double horizontal_ratio;
    private double vertical_ratio;
    private int label;

    public double get_arccot() {
        return (Math.PI / 2) - Math.atan2(horizontal_ratio, vertical_ratio);
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public double getHorizontal_ratio() {
        return horizontal_ratio;
    }

    public void setHorizontal_ratio(double horizontal_ratio) {
        this.horizontal_ratio = horizontal_ratio;
    }

    public double getVertical_ratio() {
        return vertical_ratio;
    }

    public void setVertical_ratio(double vertical_ratio) {
        this.vertical_ratio = vertical_ratio;
    }

    public SVMCharacter(double horizontal_ratio, double vertical_ratio) {
        this.horizontal_ratio = horizontal_ratio;
        this.vertical_ratio = vertical_ratio;
        this.label = 0;
    }

    public SVMCharacter(double horizontal_ratio, double vertical_ratio, int label) {
        this.horizontal_ratio = horizontal_ratio;
        this.vertical_ratio = vertical_ratio;
        this.label = label;
    }

    @Override
    public String toString() {
        int class_ = label == 1 ? 6 : label == -1 ? 9 : 0;
        return String.format("y(x) = %d ; Es un %d%n", label, class_);
    }
}
