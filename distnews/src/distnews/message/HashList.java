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


 * Created on Dec 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package distnews.message;
import java.util.ArrayList;

/**
 * Class for efficient hash list ahandling
 * @author popel
 *
 */
public class HashList {
    ArrayList hashlist;
    
    public HashList(String hl) {
        hl = hl.replaceAll(" ", "").replaceAll("\n\n","\n");
        String [] b = hl.split("\n");
        for (int i = 0; i < b.length; i++) this.hashlist.add(b[i]);
    }
    
    public HashList(String [] hl) {
        for (int i = 0; i < hl.length; i++) this.hashlist.add(hl[i]);
    }
    
    public HashList() {
        hashlist = new ArrayList(1);
    }

/**
 * Checks if hash is in Hashtable
 * 
 * @param h	hash to look for
 * @return	?(hash in hashtable)
 */
    public boolean inHashs(String h) {
        return this.hashlist.contains(h);
    }
    
/**
 * List of hashes not in both lists
 * @param hl	reference list
 * @return		list of diffs seperated by ;
 */
    public String diffHash(String [] hl) {
        String a = "";
        for(int i = 0; i < hl.length; i++) {
            if (!this.hashlist.contains(hl[i])) a += hl[i] + "\n";
        }
        return a;
    }
    
    public String hashTable() {
        String a = "";
        for(int i = 0; i < this.hashlist.size(); i++) {
            a += this.hashlist.get(i) + "\n";
        }
        return a;
    }
/**
 * Add Hash to hashtable
 * @param h	hash to add
 * 
 */
    public void addHash(String h) {
        this.hashlist.add(h);
    }
    
/**
 * Remove Hash from List
 * @param h hash to remove
 * 
 */
    public void remHash(String h) {
        this.hashlist.remove(h);
    }
}