/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wpso;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author epredator
 */
public class WPSO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         JFrame main = new MainFrame();
        main.setMinimumSize(new Dimension(1000,700));
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.pack();
        main.setVisible(true);
    }
    
}
