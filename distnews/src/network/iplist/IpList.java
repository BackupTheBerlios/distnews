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
package network.iplist;

import java.util.ArrayList;

import xmlconfig.Configuration;

/**
 * @author popel
 *
 */
public class IpList {
    private ArrayList ipl;
    public int pos = 0;
    private int size = 0;
    private int maxsockets;
    private Configuration conf;
    
    
    public IpList(int si, Configuration c) {
        this.conf	= c;
        this.ipl	= new ArrayList(si);
        
        ipl.add(new MySocket());
    }
    
/**
 * Returns next socket in list
 * @return next socket in list
 */
    public MySocket nextSocket() {
        return (MySocket) ipl.get(nextPos());
    }
    
/**
 * Adds a socket to the list
 * @param ms socket to add
 */
    public void addSocket(MySocket ms) {
        if	(ms.ip.matches("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}") &&
                (!ms.ip.matches("127.0.*")) && 
                (!contains(ms))) {
            if (this.conf.getValue("iplist_daemontype").equals("extern")) { 
                if (!ms.ip.matches("192.168.*")) {
                    ipl.add(ms);
                    this.controllSize();
                }
            }
            else {
                ipl.add(ms); 
                this.controllSize();
            }
        }
    }
    
    
    private void controllSize() {
        if(this.ipl.size() > this.conf.getIntValue("iplist_maxsize")) {
            for(int i = 1; i < 50; i++) {
                this.ipl.remove(i);
            }
        }
    }
/**
 * Returns next position
 * @return next entry to fetch
 */
    private int nextPos() {
        pos++;
        if (pos < ipl.size()) return pos;
        pos = 0;
        return 0;
    }
    
/**
 * Returns 6 randomly selected sockets from the whole list 
 * @return socket list
 */
    public String toStringRand() {
        String a = "";
        for(int i = 0; i < 6 ; i++) {
            int b = 0;
            while ((b = ((int) (Math.random() * this.maxsockets))) >= this.ipl.size()) {}
            a += "\n" + ((MySocket) ipl.get(b)).toString();
        }
        return a.trim();
    }

/**
 * Returns all sockets in list
 */
    public String toString() {
        String a = ((MySocket) ipl.get(0)).toString();
        for(int i = 1; i < this.ipl.size() ; i++) {
            a += "\n" + ((MySocket) ipl.get(i)).toString();
        }
        return a;
    }

/**
 * checks if a socket is still in list.
 * @param ms socket to check
 * @return ?(is socket in list)
 * 
 */
   private boolean contains(MySocket ms) {
       for (int i = 0; i < ipl.size(); i++) {
           if (((MySocket) ipl.get(i)).equals(ms)) return true;
       }
       return false;
   }
   
/**
 * finds positon of socket in list
 * @param ip to find
 * @return position of socket (-1 if not found)
 * 
 */
   private int find(String ip) {
       for (int i = 0; i < ipl.size(); i++) {
           if (((MySocket) ipl.get(i)).ip.equals(ip)) return i;
       }
       return -1;
   }
   
/**
 * Removes socket from List
 * @param ip socket to remove
 */
   public void removeSocket(String ip) {
       int a = find(ip);
       if (a != 0) ipl.remove(find(ip));
   }
}
