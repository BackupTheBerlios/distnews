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
package network.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import xmlconfig.Configuration;

import distnews.message.MsgList;
import distnews.message.XMLMessageParser;

import network.iplist.IpList;
import network.iplist.MySocket;

/**
 * @author popel
 *
 */
public class GUIServerThread extends Thread {
    private Socket sverb;
    private Monitor monitor;
    private IpList ipl;
    private MsgList ml;
    private Configuration conf;
    
    GUIServerThread(Socket s, Monitor mo, IpList i, MsgList m, Configuration c) {
        this.sverb	= s;
        this.monitor	= mo;
        this.ipl		= i;
        this.ml		= m;
        this.conf	= c;
    }
    
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(sverb.getInputStream()));
            PrintStream out = new PrintStream(sverb.getOutputStream());
            String str;
            boolean go = true;
            
            monitor.monitorAdd();
            out.println("Distributed NewsServer");
            out.println("USERINTERFACE");
            out.println("(c) 2003, 2004 Kai R?mer");
            out.println("commands: ");
            out.println("\tiplist:\treturns all known ips");
            out.println("\tmsglist:\treturns the xml message list");
            out.println("\taddmsg\tadd a new message");
            out.println("\taddip\tadd a new ip");
            out.println("\taddxmlmsglist\tallows to put a xml messagelist");
            out.println("\tsave\tsaves messagelist to file");
            out.println("\texit\texits userinterface");
            out.println("");
            out.print("// ");
            
            while (((str = in.readLine()) != null) && go) {
                
                if (str.trim().equals("iplist")) {
                    out.println(ipl.toString());
                    out.println("###");
                    out.print("// ");
                }
                else if (str.trim().equals("msglist")) {
                    out.println(ml.toString());
                    out.print("// ");
                }
                else if (str.trim().equals("")) {
                    out.print("// ");
                }
                else if(str.trim().equals("addip")) {
                    String stra = "";
                    out.println("Enter IPs (end with ###) : ");
                    while (!(stra = in.readLine()).trim().equals("###")) {
                        if(!stra.trim().equals("")) ipl.addSocket(new MySocket(stra.trim(),7788));
                    }
                    out.println("END");
                    out.print("// ");
                }
                else if(str.trim().equals("addmsg")) {
                    String a = "";
                    out.println("Enter Message (end with ###) : ");
                    while (!(str = in.readLine()).trim().equals("###")) {
                        a = a.concat(str + "\n");
                    }
                    a = "<?xml version='1.0' encoding='" + System.getProperty("file.encoding") + "'?><messagelist date=\""+ System.currentTimeMillis() + "\"><message count=\"0\" hash=\"X\">" + a + "</message></messagelist>";
                    ml.addMsgList((new XMLMessageParser(a.trim(),conf)).returnMsgList());
                    out.println("END");
                    out.print("// ");
                }
                else if(str.trim().equals("addxmlmsglist")) {
                    String a = "";
                    out.println("Enter Message (end with ###) : ");
                    while (!(str = in.readLine()).trim().equals("###")) {
                        a = a.concat(str.trim() + "\n");
                    }
                    XMLMessageParser xmp = new XMLMessageParser(a.trim(), conf);
                    ml.addMsgList(xmp.returnMsgList());
                    out.println("END");
                    out.print("// ");
                }
                else if(str.trim().equals("savemsglist")) {
                    out.print("filename: ");
                    String filename = in.readLine();
                    ml.saveMsgList(filename);
                    out.println("xml message list saved to " + filename);
                    out.println("END");
                    out.print("// ");
                }
                else if (str.trim().equals("exit")) {
                    go = false;
                    out.println("Good By!");
                }
                else {
                    out.println("command unknown!\n###");
                    out.print("// ");
                }
                
            }
            sverb.close();
            monitor.monitorRem();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
