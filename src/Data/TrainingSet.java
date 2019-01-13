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
public class TrainingSet {

    public static final double THETA_1 = (Math.PI / 2) - Math.atan2(0.987, 0.159);
    public static final double THETA_2 = (Math.PI / 2) - Math.atan2(0.354, 0.935);

    private static final SVMCharacter SIX = new SVMCharacter(0.987, 0.159, 1);

    private static final SVMCharacter NINE = new SVMCharacter(0.354, 0.935, -1);

    public static final SVMCharacter[] TRAINING_SET = {SIX, NINE};
}
