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

import java.net.ServerSocket;
import java.net.Socket;

import distnews.message.MsgList;

import network.iplist.IpList;

/**
 * @author popel
 *
 */
public class GUIServer extends Thread {
    IpList ipl;
    MsgList ml;
    
    public GUIServer (IpList i, MsgList m) {
        this.ipl	= i;
        this.ml	= m;
    }
    
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(7799);
            Monitor monitor = new Monitor(4);
            while (true) {
                Socket sverb = ss.accept();
                if (monitor.isFree()) {
                    //System.out.println("New GUI connection: " + sverb.getInetAddress().toString().substring(1));
                    new GUIServerThread(sverb, monitor, ipl, ml).start();
                } else {
                    //System.out.println("New GUI connection refused: " + sverb.getInetAddress().toString().substring(1));
                    sverb.close();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
