/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Kevin Lin
 */
public class GameServer {
    
    public void go()
    {   
        int numPlayers=0;
        
        try{
            ServerSocket serverSocket=new ServerSocket(8080);
            
            //Only accept two players
            while(numPlayers<2)
            {
                Socket clientSocket=serverSocket.accept();
                
                Thread t=new Thread(new ClientHandler());
                t.start();
                System.out.println("Got a connection");
                ++numPlayers;
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public class ClientHandler implements Runnable
    {
        public void run()
        {
            
        }
    }
    
}
