/*
 *  Copyright 2003, 2004 Kai Roemer

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

 * Created on 08.12.2004
 *
 */
package distnews.message.filter;

import java.io.FileReader;
import java.io.BufferedReader;

import distnews.message.MessageContainer;

/**
 * @author popel
 * Filter EXAMPLE
 * Shows how to build a message filter.
 * 
 */
public class WordFilter {
	String [] wordlist;
	
/**
 * Reads wordlist from file and initializes all necessary things
 *
 */
	public WordFilter(){
		try {
		    BufferedReader br = new BufferedReader(new FileReader("wordlist.txt"));
		    String line;
		    String b = "";
		    while((line = br.readLine()) != null) {
		        if (!line.trim().startsWith("#")) b += line.replaceAll("\n", "").replaceAll("\t","");
		    }
		    this.wordlist = b.split(";");
		}
		catch (Exception e) {
		    System.out.println("FILTER -> Wordfilter ERROR: " + e);
		}
	}
	
/**
 * Checks against the filter
 * @param	mc	message to check
 * @return		?(message ok)
 */
	public int filter(MessageContainer mc) {
		int ret = 0;
		for(int i = 0; i < wordlist.length; i++) {
			if(mc.getMessage().matches(".*" + wordlist[i].trim() + ".*")) ret += 10;
		}
		return ret;
	}
}