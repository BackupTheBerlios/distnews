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


 * Created on Dec 4, 2004
 *
 */
package network.server;

import java.io.*;
import java.net.*;

import xmlconfig.Configuration;

import distnews.message.MsgList;
import distnews.message.XMLMessageParser;

import network.iplist.*;

/**
 * @author popel
 *
 */

public class ServerThread extends Thread {
    private Socket sverb;
    private Monitor monitor;
    private IpList ipl;
    private MsgList ml;
    private Configuration conf;
    
    ServerThread(Socket s, Monitor mo, IpList i, MsgList m, Configuration c) {
        this.sverb	= s;
        this.monitor	= mo;
        this.ipl	= i;
        this.ml		= m;
        this.conf	= c;
    }
    
/**
 * Manages the server session
 */
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(sverb.getInputStream()));
            PrintStream out = new PrintStream(sverb.getOutputStream());
            
            boolean go = true;
            String str;
            monitor.monitorAdd();
            
            out.println("distnews:0.2");
            out.println("###");

            while (((str = in.readLine()) != null) && go) {
                if (str.trim().equals("exit")) go = false;
                else if (str.trim().equals("send iplist")) {
                    out.println(ipl.toStringRand());
                    out.println("###");
                }
                else if (str.trim().equals("send msglist")) {
                    out.println(ml.toString());
                    out.println("###");
                }
                
                /**
                 * send message hashes, recieve message hashes of messages to send, send messagelist
                 */
                else if (str.trim().equals("send")) {
                    // send message hashes
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
                }
                
                /**
                 * recieves message hashes, decides which ti fetch and fetches
                 */
                else if (str.trim().equals("get")) {
                    // recieve hashes
                    String stra;
                    String hashlist = "";
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
                    ml.addMsgList((new XMLMessageParser(a, conf)).returnMsgList());
                }
                else if (str.trim().equals("get iplist")) {
                    String stra;
                    while (!(stra = in.readLine()).trim().equals("###")) {
                        if (!stra.trim().equals("")) ipl.addSocket(new MySocket(stra.trim()));
                    }
                }
                else if (str.trim().equals("get msglist")) {
                    String stra;
                    String a = "";
                    while (!(str = in.readLine()).trim().equals("###")) {
                        if (!str.trim().equals("")) a = a + str.trim();
                    }
                    ml.addMsgList((new XMLMessageParser(a, conf)).returnMsgList());

                }
                else if (str.trim().equals("info")) {
                    out.println("DistNews");
                    out.println("(c) 2003, 2004 Kai R?mer");
                    out.println("mailto:kai.roemer@gmx.de");
                    out.println("This programm is distributed under GPL");
                }
            }
            
            out.println("OK and Good by!");
            sverb.close();
            monitor.monitorRem();
        }
        catch (Exception e) {
            System.out.println("ServerThread: " + e);
        }
    }
}
