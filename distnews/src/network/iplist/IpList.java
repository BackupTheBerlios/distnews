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

import java.util.*;

/**
 * @author popel
 *
 */
public class IpList {
    private ArrayList ipl;
    public int pos = 0;
    private int size = 0;
    
    public IpList(int si) {
        this.ipl = new ArrayList(si);
        ipl.add(new MySocket());
    }
    
    public MySocket nextSocket() {
        return (MySocket) ipl.get(nextPos());
    }
    
    public void addSocket(MySocket ms) {
        if	(ms.ip.matches("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}") &&
            (!ms.ip.equals("127.0.0.1")) && 
            (!contains(ms))) 
            	ipl.add(ms);
    }
    
    private int nextPos() {
        pos++;
        if (pos < ipl.size()) return pos;
        pos = 0;
        return 0;
    }
    
   public String toString() {
        String a = ((MySocket) ipl.get(0)).toString();
        for(int i = 1; i < ipl.size(); i++) {
            a = a.concat("\n" + ((MySocket) ipl.get(i)).toString());
        }
        return a;
    }
   
   private boolean contains(MySocket ms) {
       for (int i = 0; i < ipl.size(); i++) {
           if (((MySocket) ipl.get(i)).equals(ms)) return true;
       }
       return false;
   }
   
   private int find(String ip) {
       for (int i = 0; i < ipl.size(); i++) {
           if (((MySocket) ipl.get(i)).ip.equals(ip)) return i;
       }
       return -1;
   }
   
   public void removeSocket(String ip) {
       int a = find(ip);
       if (a != 0) ipl.remove(find(ip));
   }
}
