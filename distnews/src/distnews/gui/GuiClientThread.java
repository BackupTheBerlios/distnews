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


 * Created on Dec 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package distnews.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import distnews.message.XMLMessageParser;

/**
 * @author popel
 *
 */
public class GuiClientThread {
    public void connect(String ip, int port) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(ip,port), 1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream out = new PrintStream(s.getOutputStream());
            
            out.println("msglist");
            
            String a = "";
            String str = "";
            
            while (!(str = in.readLine()).trim().equals("###")) {
                if (!str.trim().equals("")) a = a + str.trim();
            }
            try {
                ml.addMsgList((new XMLMessageParser(a, conf)).returnMsgList());
            } catch (Exception e) {
                System.out.println("XML PARSER ERROR: " + e);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
