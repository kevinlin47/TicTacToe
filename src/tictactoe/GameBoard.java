/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.util.*;
/**
 *
 * @author kevinlin
 */

public class GameBoard implements ActionListener{
    JFrame mainFrame;
    JPanel boardPanel;
    ArrayList<JButton> buttonList;
    
    
    public void setUpBoard()
    {   
        buttonList=new ArrayList();
        
        mainFrame=new JFrame("XD");
        mainFrame.setSize(500,500);
        mainFrame.setLocationRelativeTo(null);
        
        boardPanel=new JPanel();
        GridLayout gridLayout=new GridLayout(3,3,2,2);
        boardPanel.setLayout(gridLayout);
        
        for (int i=0;i<9;++i)
        {   
            JButton boardButton=new JButton("");
            boardButton.addActionListener(this);
            boardPanel.add(boardButton);
            buttonList.add(boardButton);
        }
        
        mainFrame.getContentPane().add(BorderLayout.CENTER,boardPanel);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent ev)
    {
        JButton tempButton=(JButton)ev.getSource();
        tempButton.setFont(new Font("Courier New", Font.PLAIN,32));
        tempButton.setForeground(Color.RED);
        tempButton.setBackground(Color.lightGray);
        tempButton.setOpaque(true);
        tempButton.setText("X");
        tempButton.setUI(new MetalButtonUI(){
            protected Color getDisabledTextColor()
            {
                return Color.RED;
            }
            
        });
        tempButton.setEnabled(false);
        checkWinCondition();
    }
    
    public boolean checkWinCondition()
    {
        if (buttonList.get(0).getText().equals(buttonList.get(1).getText()) && buttonList.get(0).getText().equals(buttonList.get(2).getText()) )
        {
            JFrame endFrame=new JFrame("Game Over");
            endFrame.setSize(150,200);
            endFrame.setLocationRelativeTo(null);
            endFrame.setVisible(true);
            
            JLabel jLabel=new JLabel("Player X Wins");
            endFrame.getContentPane().add(BorderLayout.CENTER,jLabel);
            JButton okButton=new JButton("Ok");
            okButton.addActionListener(new EndGameListener(endFrame));
            endFrame.getContentPane().add(BorderLayout.SOUTH,okButton);
            return true;
        }
        return false;
    }
    
    public class EndGameListener implements ActionListener
    {   
        JFrame endGameFrame;
        
        EndGameListener(JFrame endGameFrame)
        {
            this.endGameFrame=endGameFrame;
        }
        
        public void actionPerformed(ActionEvent ev)
        {   
            endGameFrame.dispose();
            mainFrame.dispose();
        }
    }
    
    public static void main(String args[])
    {
        GameBoard gB=new GameBoard();
        gB.setUpBoard();
    }
    
}
