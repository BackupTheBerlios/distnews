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

/**
 * @author popel
 *
 */
public class MySocket {
    public String ip = "";
    public int port = 7788;
    
    public MySocket(String i, int p) {
        ip = i;
        port = 7788;
    }
    
    public MySocket(String s) {
        String [] a = s.split(":");
        ip = a[0];
        port = 7788;
        //port = Integer.valueOf(a[1]).intValue();
    }

    public MySocket() {
        ip = "192.168.77.20";
        port = 7788;
    }
    
    public boolean equals (MySocket ms) {
        return (ip.equals(ms.ip));
    }
    
    public String toString() {
        return (ip + ":" + Integer.toString(port));
    }
}
