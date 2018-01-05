/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;
import javax.swing.*;
/**
 *
 * @author kevinlin
 */

public class GameBoard {
    JFrame mainFrame;
    JButton boardButton;
    
    public void setUpBoard()
    {
        mainFrame=new JFrame("XD");
        
        mainFrame.setSize(500,500);
        mainFrame.setVisible(true);
    }
    
    public static void main(String args[])
    {
        GameBoard gB=new GameBoard();
        gB.setUpBoard();
    }
    
}
