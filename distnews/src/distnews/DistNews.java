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
package distnews;

/*
 * TODO Security verbessern
 * TODO Message Bestandsabgleich vor ?bertragung (partialy done)
 * TODO Serverseitiges Select mittels XSLT
 * TODO Message Format Definition
 * 
 * TODO Funktion um distnews regul?r (mit speichern zu beenden)
 * TODO Funktion um messages aus letztem lauf wieder zu laden
 * 
 * XXX Configurations script
 * XXX xml configuration
 * 
 */ 

import distnews.message.MsgList;
import network.client.ClientInitiator;
import network.iplist.IpList;
import network.server.*;

/**
 * @author popel
 *
 */

public class DistNews {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to DistNews");
        System.out.println("(c) 2003, 2004 by Kai Roemer");
        System.out.println("Published under GPL");
        
        System.setProperty("file.encoding", "UTF-8");
        
        IpList ipl	= new IpList(10);
        MsgList ml	= new MsgList(1);
        
        new Server(ipl, ml).start();
        new GUIServer(ipl, ml).start();
        new ClientInitiator(ipl, ml).start();
    }
}
