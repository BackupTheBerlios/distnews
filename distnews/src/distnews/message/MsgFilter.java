/*
 *	Copyright 2003, 2004 Kai Roemer

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
package distnews.message;

import distnews.message.filter.WordFilter;

/**
 * @author popel
 * 
 * Class to use message filters.
 *  
 */

public class MsgFilter {
	private String [] filters;
	private MessageContainer mc;
	private int limes;
	
/**
 * 
 * @param	a	name of the message filter to be used
 * @param	m	message that should be examined
 * @param	l	maximum filter count for the message to be accepted
 * 
 */
	public MsgFilter (String a, MessageContainer m, int l) {
		this.filters = a.split(",");
		this.mc		= m;
		this.limes	= l;
	}
	
/**
 * call all mentioned filters
 * @return		?(accept the message)
 */
	public boolean filter () {
		int ret = 0;
		if (this.filters.length == 0) return false;
		for (int i = 0; ((i< filters.length) && (ret < this.limes)); i++) {
			if (filters[i].equalsIgnoreCase("wordfilter")) {
				ret += (new WordFilter()).filter(mc);
			}
			else {
			    System.out.println("FILTER: \tfilter not found");
			}
		}
		/*
		 * TODO	Build MessageFilter caller that looks for message filters 
		 * 		in a directory and uses all found ???
		 */
		return (ret > this.limes);
	}
}