/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Kevin Lin
 */
public class GameServer {
    
    ArrayList clientOutputStreams;
    
    public void go()
    {   
        int numPlayers=0;
        clientOutputStreams=new ArrayList();
        
        try{
            ServerSocket serverSocket=new ServerSocket(8080);
            
            //Only accept two players
            while(numPlayers<2)
            {
                Socket clientSocket=serverSocket.accept();
                
                PrintWriter writer=new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                
                if (numPlayers==0)
                {
                    writer.println("X");
                    writer.flush();
                }
                else if (numPlayers==1)
                {
                    writer.println("O");
                    writer.flush();
                }
                
                Thread t=new Thread(new ClientHandler(clientSocket));
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
        BufferedReader reader;
        Socket sock;
        
        ClientHandler(Socket clientSocket)
        {
            this.sock=clientSocket;
            try{
                InputStreamReader isReader=new InputStreamReader(sock.getInputStream());
                reader=new BufferedReader(isReader);
                
            } catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
        
        public void run()
        {   
            String move;
            try{
            while((move=reader.readLine())!=null)
            {
                System.out.println(move);
                distributeMove(move);
                
            }
            } catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    public void distributeMove(String move)
    {
        Iterator it=clientOutputStreams.iterator();
        
        while(it.hasNext())
        {
            try{
                PrintWriter writer=(PrintWriter) it.next();
                writer.println(move);
                writer.flush();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
}
