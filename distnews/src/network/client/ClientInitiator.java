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

import xmlconfig.Configuration;
import distnews.message.MsgList;
import network.iplist.IpList;

/**
 * @author popel
 *
 */
public class ClientInitiator extends Thread {
    IpList ipl;
    MsgList ml;
    Configuration conf;
    
    public ClientInitiator (IpList i, MsgList m, Configuration c) {
        this.ipl	= i;
        this.ml		= m;
        this.conf	= c;
    }
    public void run() {
        while(true) {
            String	ip		= ipl.nextSocket().ip;
            int 	port	= ipl.nextSocket().port;
            if (!ip.equals("")) {
                //System.out.println("CLIENT connecting to " + ip);
                try {
                    new Client(ipl, ml).run(ip, port);            
                } catch(Exception e) {
	                System.out.println("ClientInitiator: " + e);
	            }
                //System.out.println("CLIENT connection finished: " + ip);
	            try {
	                sleep(this.conf.getIntValue("client_sleeptime"));
	            }
	            catch(InterruptedException e) {
	                System.out.println("ClientInitiatorsleep: " + e);
	            }
	            
            }
        }
    }
}
