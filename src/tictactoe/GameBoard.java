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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.util.*;
/**
 *
 * @author kevinlin
 */

public class GameBoard implements ActionListener, Runnable{
    JFrame mainFrame;
    JPanel boardPanel;
    ArrayList<JButton> buttonList;
    Socket sock;
    String player;
    BufferedReader reader;
    PrintWriter writer;
    
    
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
        setUpConnection();
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
        tempButton.setText(player);
        tempButton.setUI(new MetalButtonUI(){
            protected Color getDisabledTextColor()
            {
                return Color.RED;
            }
            
        });
        Integer buttonNumber=buttonList.indexOf((JButton) ev.getSource());
        writer.println(player+"-"+buttonNumber.toString());
        writer.flush();
        tempButton.setEnabled(false);
    }
    
    public boolean checkWinCondition()
    {   
        
        if (!buttonList.get(0).getText().equals("") && buttonList.get(0).getText().equals(buttonList.get(1).getText()) && buttonList.get(0).getText().equals(buttonList.get(2).getText()))
        {
            displayWinner(buttonList.get(0).getText());
            return true;
        }
        else if (!buttonList.get(3).getText().equals("") && buttonList.get(3).getText().equals(buttonList.get(4).getText()) && buttonList.get(3).getText().equals(buttonList.get(5).getText()))
        {
            displayWinner(buttonList.get(3).getText());
            return true;
        }
        else if(!buttonList.get(6).getText().equals("") && buttonList.get(6).getText().equals(buttonList.get(7).getText()) && buttonList.get(6).getText().equals(buttonList.get(8).getText()))
        {
            displayWinner(buttonList.get(6).getText());
            return true;
        }
        else if(!buttonList.get(0).getText().equals("") && buttonList.get(0).getText().equals(buttonList.get(3).getText()) && buttonList.get(0).getText().equals(buttonList.get(6).getText()))
        {
            displayWinner(buttonList.get(0).getText());
            return true;
        }
        else if(!buttonList.get(1).getText().equals("") && buttonList.get(1).getText().equals(buttonList.get(4).getText()) && buttonList.get(1).getText().equals(buttonList.get(7).getText()))
        {
            displayWinner(buttonList.get(1).getText());
            return true;
        }
        else if(!buttonList.get(2).getText().equals("") && buttonList.get(2).getText().equals(buttonList.get(5).getText()) && buttonList.get(2).getText().equals(buttonList.get(8).getText()))
        {
            displayWinner(buttonList.get(2).getText());
            return true;
        }
        else if(!buttonList.get(0).getText().equals("") && buttonList.get(0).getText().equals(buttonList.get(4).getText()) && buttonList.get(0).getText().equals(buttonList.get(8).getText()))
        {
            displayWinner(buttonList.get(0).getText());
            return true;
        }
        else if(!buttonList.get(2).getText().equals("") && buttonList.get(2).getText().equals(buttonList.get(4).getText()) && buttonList.get(2).getText().equals(buttonList.get(6).getText()))
        {
            displayWinner(buttonList.get(2).getText());
            return true;
        }
        
        for (int i=0;i<buttonList.size();++i)
        {
            if (buttonList.get(i).isEnabled()==true)
            {
                return false;
            }
        }
        
        displayWinner("Draw");
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
    
    public void displayWinner(String winner)
    {
        JFrame endFrame=new JFrame("Game Over");
        endFrame.setSize(150,200);
        endFrame.setLocationRelativeTo(null);
            
        JLabel jLabel=new JLabel("Player "+winner+" wins!");
        endFrame.getContentPane().add(BorderLayout.CENTER,jLabel);
        JButton okButton=new JButton("Ok");
        okButton.addActionListener(new EndGameListener(endFrame));
        endFrame.getContentPane().add(BorderLayout.SOUTH,okButton);
        endFrame.setVisible(true);
    }
    
    public void run()
    {
        
    }
    
    public void setUpConnection()
    {
        try{
            sock=new Socket("69.141.219.134",8080);
            writer=new PrintWriter(sock.getOutputStream());
            InputStreamReader isReader=new InputStreamReader(sock.getInputStream());
            reader=new BufferedReader(isReader);
            System.out.println("Connection Established");
            player=reader.readLine();
            
            Thread t=new Thread(new incommingReader());
            t.start();
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public class incommingReader implements Runnable
    {   
        public void run()
        {
            String move;
            
            try{
            while((move=reader.readLine())!=null)
            {   
                String decode[]=move.split("-");
                JButton tempButton=buttonList.get(Integer.parseInt(decode[1]));
                tempButton.setFont(new Font("Courier New", Font.PLAIN,32));
                tempButton.setForeground(Color.RED);
                tempButton.setBackground(Color.lightGray);
                tempButton.setOpaque(true);
                tempButton.setText(decode[0]);
                tempButton.setUI(new MetalButtonUI(){
                protected Color getDisabledTextColor()
                {
                    return Color.RED;
                }
                });
                
                tempButton.setEnabled(false);
                System.out.println(move);
                checkWinCondition();
            }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String args[])
    {
        GameBoard gB=new GameBoard();
        gB.setUpBoard();
    }
    
}
