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

 *
 * Created on Dec 4, 2004
 *
 */
package network.server;

import java.net.*;

import distnews.message.MsgList;

import network.iplist.IpList;
import network.iplist.MySocket;

/**
 * @author popel
 *
 */
public class Server extends Thread {
    IpList ipl;
    MsgList ml;
    
    public Server (IpList i, MsgList m) {
        this.ipl	= i;
        this.ml	= m;
    }
    
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(7788);
            Monitor monitor = new Monitor(10);
            while (true) {
                Socket sverb = ss.accept();
                if (monitor.isFree()) {
                    //System.out.println("SERVER  connection from : " + sverb.getInetAddress().toString().substring(1));
                    ipl.addSocket(new MySocket(sverb.getInetAddress().toString().substring(1), 7788));
                    new ServerThread(sverb, monitor, ipl, ml).start();
                } else {
                    ipl.addSocket(new MySocket(sverb.getInetAddress().toString().substring(1), 7788));
                    //System.out.println("SERVER connection refused: " + sverb.getInetAddress().toString().substring(1));
                    sverb.close();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Server: " + e);
        }
    }
}