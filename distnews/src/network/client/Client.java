/*
 *	Copyright 2003, 2004 Kai Roemer
 *

	This file is part of distnews.

    distnews is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    any later version.

    distnews is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with distnews; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


 * Created on Dec 5, 2004
 *
 */
package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.InetSocketAddress;

import distnews.message.MsgList;
import distnews.message.XMLMessageParser;

import network.iplist.IpList;
import network.iplist.MySocket;

/**
 * @author popel
 *
 */
public class Client {
    private IpList ipl;
    private MsgList ml;

/**
 * Constructor 
 * @param i	reference on the used IpList
 * @param m reference on the used MsgList
 */
    public Client (IpList i, MsgList m) {
        this.ipl	= i;
        this.ml		= m;
    };
    
/**
 * Communicates with the server
 * @param	ip	ip address of the server
 * @param	port port of the server
 * @throws Exception
 */
	public void run(String ip, int port) {
        try {
            /*
             * Socket s = new Socket (ip, port);
             * s.setSoTimeout(1000);
             */
            
            Socket s = new Socket();
            //s.setSoTimeout(1000);
            s.connect(new InetSocketAddress(ip,port), 1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            String str;
            
            // Wait for Promt
            if (!(str = in.readLine()).equals("distnews:0.2")) {
                //System.out.println("WRONG VERSION");
            }
            else {
                while (!(str = in.readLine()).equals("###")) {}
                /**
                 * send message hashes, recieve message hashes of messages to send, send messagelist
                 */
                // send message hashes
                out.println("get");
                out.println(ml.hashList());
                out.println("###");
                // recieve message hashes
                String stra;
                String hashlist = "";
                while (!(stra = in.readLine()).trim().equals("###")) {
                    if (!stra.trim().equals("")) hashlist += stra.trim().replaceAll("\n","") + "\n";
                }
                // send messages
                out.println(ml.toString(hashlist));
                out.println("###");
                
                /**
                 * recieves message hashes, decides which ti fetch and fetches
                 */
                out.println("send");
                // recieve hashes
                hashlist = "";
                while (!(stra = in.readLine()).trim().equals("###")) {
                    if (!stra.trim().equals("")) hashlist += stra.trim().replaceAll("\n","") + "\n";
                }
                // decide which to fetch
                out.println(ml.needNewMsgList(hashlist));
                out.println("###");
                // fetch new one
                String a = "";
                while (!(str = in.readLine()).trim().equals("###")) {
                    if (!str.trim().equals("")) a = a + str.trim();
                }
                try {
                    ml.addMsgList(new XMLMessageParser(a).returnMsgList());
                } catch (Exception e) {
                    System.out.println("XML PARSER ERROR: " + e);
                }
                
                
                /**
                 * Exchange IP lists
                 */

                // Send iplist
                out.println("get iplist");
                out.println(ipl.toString());
                out.println("###");
                
                // Retrive iplist
                out.println("send iplist");
                while (!(str = in.readLine()).equals("###")) {
                    if (!str.equals("")) ipl.addSocket(new MySocket(str));
                }
                out.println("exit");
            }
            s.close();
        }
        catch (IOException e) {
            System.out.println("Client: " + e);
            ipl.removeSocket(ip);
        }
    }
}
