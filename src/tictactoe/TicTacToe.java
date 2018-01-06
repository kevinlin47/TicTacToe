/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.TimerTask;
import java.util.Timer;

/**
 *
 * @author kevinlin
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Timer t=new Timer();
        t.schedule(new TimerTask(){
            @Override
            public void run()
            {   
                GameBoard gameBoard=new GameBoard();
                gameBoard.setUpBoard();
            }
        }, 1000);
        
        GameServer server=new GameServer();
        server.go();

    }
    
}
